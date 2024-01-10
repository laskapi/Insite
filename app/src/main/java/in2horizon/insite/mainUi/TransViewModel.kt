package in2horizon.insite

import android.content.Context
import android.util.Log
import android.util.Patterns
import android.util.SizeF
import android.webkit.URLUtil
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gmail.in2horizon.insite.db.Translation
import com.gmail.in2horizon.insite.db.TranslationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import in2horizon.insite.gecko.SessionObserver
import in2horizon.insite.gecko.SessionsManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.mozilla.geckoview.GeckoSession
import java.util.Collections
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.schedule

@HiltViewModel
class TransViewModel @Inject constructor(
    private val translationRepository:
    TranslationRepository, @ApplicationContext
    appContext: Context
) :
    ViewModel() {


    private val START_PAGE = "start_page"
    private val USE_START_PAGE = "use_start_page"
    private val TAG = javaClass.name

    private val prefs = appContext.getSharedPreferences(
        appContext.getString(R.string.prefs),
        Context.MODE_PRIVATE
    )

    @Inject
    lateinit var sessionsManager: SessionsManager

    private val LAST_TRANSLATIONS_COUNT = 5
    private val LAST_TRANSLATIONS_VIEW_UPDATE_PERIOD = 5000L

    private var lastTranslations = Collections.EMPTY_LIST
    private var lastTranslationsIterator = 0
    private var lastTranslationsViewUpdateTimer = Timer()


    private val _translationToShow = MutableStateFlow(Translation())
    val translationToShow = _translationToShow.asStateFlow()

    private val _searchText = MutableStateFlow(TextFieldValue(""/*"https://www.tagesschau.de/"*/))
    val searchText = _searchText.asStateFlow()

    private val _url = MutableStateFlow("")
    var url = _url.asStateFlow()

    private var activeSession = 0
    var selectionCoords: SizeF? = null

    private val _showPreferences = MutableStateFlow(false)
    var showPreferences = _showPreferences.asStateFlow()

    private var srcText = ""

    private var _engineUrl = MutableStateFlow("https://duckduckgo.com/")
    var engineUrl = _engineUrl.asStateFlow()


    private var rawSearchText = ""

    var useStartPage: Boolean
        get() = prefs.getBoolean(USE_START_PAGE, true)
        set(value) {
            Log.d(TAG, "Use start page: " + value)
            prefs.edit().putBoolean(USE_START_PAGE, value).apply()
        }

    val allTranslationsPagedFlow: Flow<PagingData<Translation>> =translationRepository
        .getAllTranslationsPaged().cachedIn(viewModelScope)


    init {
        Log.d(TAG, "ViewModel initalization")

        getStartPage()?.let { _searchText.value = TextFieldValue(it) }

        Log.d(TAG, "VurlValue=" + searchText.value)

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


    fun setAddress(addr: TextFieldValue) {
        _searchText.value = addr
        if (!useStartPage) setStartPage(addr.text)
    }

    fun setUrl() {
        /*    if (rawSearchText.isNotEmpty()) {
                setAddress(TextFieldValue(rawSearchText))
            }*/

        if (Patterns.WEB_URL.matcher(
                searchText.value.text
            ).matches()
        ) {
            rawSearchText = ""

            Log.d(
                TAG, "guessedUrl::" + URLUtil.guessUrl(
                    searchText.value.text
                )
            )

            _url.value = URLUtil.guessUrl(
                searchText.value.text
            )
            Log.d(
                TAG, "validUrl ::" + url.value
            )
        } else {
            rawSearchText = searchText.value.text
            _url.value = engineUrl.value + searchText.value.text
            Log.d(
                TAG, "invalidUrl " + url.value
            )
        }
    }

    fun setSourceText(src: String) {
        if (src.length > 0) {
            this.srcText = src
        }
    }


    fun setSelectionCenterCoords(coords: SizeF?) {
        this.selectionCoords = coords
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

    fun getTranslations() {
        //    return viewModelScope.launch(Dispatchers.IO) {
        //   allTranslations=   translationRepository.getTranslations()


}

private fun updateTranslationToShow() {
    lastTranslations.let {
        if (it.isNotEmpty()) {
            _translationToShow.value = it.get(
                (lastTranslationsIterator++)
                        % it.size
            ) as
                    Translation
            //   Log.d(TAG, "update translation to show: " + _translationToShow.value)

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
    Log.d(TAG, "rawSearchText= " + rawSearchText)
    if (rawSearchText.isNotEmpty()) {
        _searchText.value = TextFieldValue(rawSearchText)
    }
    Log.d(TAG, "set engine to: " + engineUrl.value)
    setUrl()
}

fun setSessionsManagerObserver(sessionObserver: SessionObserver) {
    sessionsManager.setObserver(sessionObserver)
}

fun getActiveSession(): GeckoSession {
    return sessionsManager.get(activeSession)
}

fun setActiveSession(index: Int) {
    activeSession = index
}

fun setStartPage(value: String) {
    prefs.edit().putString(START_PAGE, value).apply()
}

fun getStartPage(): String {
    return prefs.getString(START_PAGE, "") ?: ""
}

fun deleteAllTranslations() {
    viewModelScope.launch(Dispatchers.IO) {
        Log.d(TAG, "delete all translations")
        translationRepository.deleteTranslations()
        updateLastTranslations()
    }
}

}