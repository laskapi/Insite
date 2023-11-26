package in2horizon.insite

import android.util.Log
import android.util.Patterns
import android.util.SizeF
import android.webkit.URLUtil
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.in2horizon.insite.db.Translation
import com.gmail.in2horizon.insite.db.TranslationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Collections
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.schedule

@HiltViewModel
class TransViewModel @Inject constructor(private val translationRepository: TranslationRepository) :
    ViewModel() {

    private val TAG = javaClass.name
    private var lastTranslations = Collections.EMPTY_LIST
    private var lastTranslationsIterator = 0
    private var lastTranslationsViewUpdateTimer: Timer = Timer()

    private val LAST_TRANSLATIONS_COUNT = 5
    private val LAST_TRANSLATIONS_VIEW_UPDATE_PERIOD = 5000L

    private val _translationToShow = MutableStateFlow(Translation())
    val translationToShow = _translationToShow.asStateFlow()

    private val _address = MutableStateFlow(TextFieldValue(""))
    val address = _address.asStateFlow()
    var mUrl = "https://www.tagesschau.de/"

    private val _activeSession = MutableStateFlow(Integer.valueOf(0))
    val activeSession = _activeSession.asStateFlow()

    var selectionCoords: SizeF? = null

    private var src = ""


    init {
        viewModelScope.launch(Dispatchers.IO) {
            updateLastTranslations()
        }
        lastTranslationsViewUpdateTimer.schedule(LAST_TRANSLATIONS_VIEW_UPDATE_PERIOD, LAST_TRANSLATIONS_VIEW_UPDATE_PERIOD) {
            updateTranslationToShow()
        }

    }


    fun setActiveSession(index: Int) {
        _activeSession.value = index
    }

    fun setAddress(addr: TextFieldValue) {
        _address.value = addr
    }

    fun setUrl(urlString: String) {

        if (Patterns.WEB_URL.matcher(
                /*address.value.text*/
                urlString
            ).matches()
        ) {
            val guessedUrl = URLUtil.guessUrl(
                /*address.value.text*/
                urlString
            )
            mUrl = guessedUrl
            //_url.value = guessedUrl
            // _url.value=StringBuilder(guessedUrl).insert(4,"s").toString()
            Log.d(
                TAG, "valid " +
                        /*address.value*/
                        urlString + " :: " + mUrl//_url.value
            )
        } else {
            mUrl = "https://www.google.pl/search?q=" +

//            _url.value = "https://www.google.pl/search?q=" +
                    /*address.value.text*/
                    urlString
            Log.d(
                TAG, "invalid " +
                        /*_url.value*/
                        urlString
            )
        }
    }

    fun setSourceText(src: String) {
        if (src.length > 0) {
            this.src = src
        }
    }

    fun addTranslation(dst: CharSequence?) {
        if (src.isNotBlank() && !dst.isNullOrBlank()) {
            viewModelScope.launch(Dispatchers.IO) {
                translationRepository.insertTranslation(src, dst.toString())
                updateLastTranslations()
            }
        } else {
            src = ""
        }
    }

    fun deleteTranslation(translation: Translation) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "delete " + translation)
            translationRepository.deleteTranslation(translation)
            updateLastTranslations()
        }

    }


    fun setSelectionCenterCoords(coords: SizeF?) {
        this.selectionCoords = coords
    }

    private fun updateTranslationToShow() {
        lastTranslations.let {
            if (it.isNotEmpty()) {
                _translationToShow.value = it.get(
                    (lastTranslationsIterator++)
                            % it.size
                ) as
                        Translation
                Log.d(TAG,"update translation to show: "+_translationToShow.value)

            }
        }
    }

    private fun updateLastTranslations() {
        lastTranslations = translationRepository.getTranslations(LAST_TRANSLATIONS_COUNT)
        if (lastTranslations.isEmpty()){
            lastTranslations.add(Translation())
        }
        lastTranslationsIterator = 0
        updateTranslationToShow()
    }


}
