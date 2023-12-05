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
import dagger.hilt.android.qualifiers.ApplicationContext
import in2horizon.insite.gecko.SessionObserver
import in2horizon.insite.gecko.SessionsManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.mozilla.geckoview.GeckoSession
import java.util.Collections
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.schedule

@HiltViewModel
class TransViewModel @Inject constructor(private val translationRepository: TranslationRepository) :
    ViewModel() {

    private val TAG = javaClass.name

    @ApplicationContext
    private lateinit var appContext: ApplicationContext

    @Inject
    lateinit var sessionsManager: SessionsManager

    private val LAST_TRANSLATIONS_COUNT = 5
    private val LAST_TRANSLATIONS_VIEW_UPDATE_PERIOD = 5000L

    private var lastTranslations = Collections.EMPTY_LIST
    private var lastTranslationsIterator = 0
    private var lastTranslationsViewUpdateTimer= Timer()


    private val _translationToShow = MutableStateFlow(Translation())
    val translationToShow = _translationToShow.asStateFlow()

    private val _address = MutableStateFlow(TextFieldValue(""))
    val address = _address.asStateFlow()

    private val _mUrl = MutableStateFlow("https://www.tagesschau.de/")
    var mUrl = _mUrl.asStateFlow()

    private var activeSession = 0
    var selectionCoords: SizeF? = null

    private val _showPreferences = MutableStateFlow(false)
    var showPreferences = _showPreferences.asStateFlow()

    private var srcText = ""

    private var _engineUrl = MutableStateFlow("")
    var engineUrl = _engineUrl.asStateFlow()



    init {
        viewModelScope.launch(Dispatchers.IO) {
            updateLastTranslations()
        }
        lastTranslationsViewUpdateTimer.schedule(
            LAST_TRANSLATIONS_VIEW_UPDATE_PERIOD,
            LAST_TRANSLATIONS_VIEW_UPDATE_PERIOD
        ) {
            updateTranslationToShow()
        }

    }


    fun setActiveSession(index: Int) {
       activeSession = index
    }

    fun setAddress(addr: TextFieldValue) {
        _address.value = addr
    }

    fun setUrl(urlString: String) {

        if (Patterns.WEB_URL.matcher(
                urlString
            ).matches()
        ) {
            val guessedUrl = URLUtil.guessUrl(
                urlString
            )
            _mUrl.value = guessedUrl
            Log.d(
                TAG, "valid " +
                        urlString + " :: " + mUrl.value//
            )
        } else {
            _mUrl.value = engineUrl.value + address.value.text
         Log.d(
                TAG, "invalid " +
                        urlString
            )
        }
    }

    fun setSourceText(src: String) {
        if (src.length > 0) {
            this.srcText = src
        }
    }

    fun addTranslation(dst: CharSequence?) {
        if (srcText.isNotBlank() && !dst.isNullOrBlank()) {
            viewModelScope.launch(Dispatchers.IO) {
                translationRepository.insertTranslation(srcText, dst.toString())
                updateLastTranslations()
            }
        } else {
            srcText = ""
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
                Log.d(TAG, "update translation to show: " + _translationToShow.value)

            }
        }
    }

    private fun updateLastTranslations() {
        lastTranslations = translationRepository.getTranslations(LAST_TRANSLATIONS_COUNT)
        if (lastTranslations.isEmpty()) {
            lastTranslations.add(Translation())
        }
        lastTranslationsIterator = 0
        updateTranslationToShow()
    }

    fun showPreferences(show: Boolean) {
        _showPreferences.value = show
    }

    fun setEngine(engine: String) {
        _engineUrl.value = engine
        setUrl(address.value.text)
    }

    fun setSessionsManagerObserver(sessionObserver: SessionObserver) {
        sessionsManager.setObserver(sessionObserver)
    }

    fun getActiveSession(): GeckoSession {
        return sessionsManager.get(activeSession)
    }
}
