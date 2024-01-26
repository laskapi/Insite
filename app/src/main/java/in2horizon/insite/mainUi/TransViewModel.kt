package in2horizon.insite.mainUi

import android.content.Context
import android.util.Log
import android.util.Patterns
import android.util.SizeF
import android.webkit.URLUtil
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gmail.in2horizon.insite.db.Translation
import com.gmail.in2horizon.insite.db.TranslationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import in2horizon.insite.R
import in2horizon.insite.gecko.MyUrl
import in2horizon.insite.gecko.SessionObserver
import in2horizon.insite.gecko.SessionsManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.mozilla.geckoview.GeckoSession
import java.util.Collections
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.schedule

enum class Screen {
    SETTINGS, MAIN, TRANSLATIONS
}

@HiltViewModel
class TransViewModel @Inject constructor(
    private val translationRepository:
    TranslationRepository, @ApplicationContext
    appContext: Context
) :
    ViewModel() {


    private val START_PAGE = "start_page"
    private val USE_START_PAGE = "use_start_page"
    private val CURRENT_ENGINE = "current_engine"
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

//    private val _url = MutableStateFlow("")
    //   var url = _url.asStateFlow()

    private val _mUrl = MutableStateFlow(MyUrl())
    var mUrl = _mUrl.asStateFlow()

    private var activeSession = 0
    var selectionCoords: SizeF? = null

    @OptIn(ExperimentalFoundationApi::class)
    val pager = PagerState(initialPage = Screen.MAIN.ordinal)


    private var srcText = ""

    private var _engineUrl = MutableStateFlow("https://duckduckgo.com/")
    var engineUrl = _engineUrl.asStateFlow()


    private var rawSearchText = ""

    private val _bottomBarHeight = MutableStateFlow(0.dp)
    var bottomBarHeight = _bottomBarHeight.asStateFlow()

    var useStartPage: Boolean
        get() = prefs.getBoolean(USE_START_PAGE, true)
        set(value) {
            Log.d(TAG, "Use start page: " + value)
            prefs.edit().putBoolean(USE_START_PAGE, value).apply()
        }

    val allTranslationsPagedFlow: Flow<PagingData<Translation>> = translationRepository
        .getAllTranslationsPaged().cachedIn(viewModelScope)


    init {
        Log.d(TAG, "ViewModel initalization")

        prefs.getString(CURRENT_ENGINE, appContext.getString(R.string.DuckDuckGo))
            ?.let { _engineUrl.value = it }

        getStartPage().let { setSearchText(TextFieldValue(it))/*_searchText.value = TextFieldValue
        (it, TextRange(0,it
        .length))
        */ }
        /*  if(useStartPage){
              setAddress(TextFieldValue(getStartPage()))
          }*/

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


    fun setSearchText(addr: TextFieldValue) {
        Log.d(TAG, Log.getStackTraceString(Exception()))

        _searchText.value = addr
        if (!useStartPage) setStartPage(addr.text)
    }


    /**
     *
     */
    fun setUrl() {

        val isValid = Patterns.WEB_URL.matcher(
                searchText.value.text
            ).matches()

        if (isValid) {
            rawSearchText = ""
            _mUrl.value = MyUrl(
                URLUtil.guessUrl(
                    searchText.value.text
                ), true
            )
            Log.d(
                TAG, "validUrl ::" + mUrl.value.url
            )
        } else {
            rawSearchText = searchText.value.text
            _mUrl.value = MyUrl(
                engineUrl.value + searchText.value.text,
                false
            )
            Log.d(
                TAG, "invalidUrl " + mUrl.value.url
            )
        }
    }


    fun setSourceText(src: String) {
        if (src.isNotEmpty()) {
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

    fun setEngine(engine: String) {

        _engineUrl.value = engine
        prefs.edit().putString(CURRENT_ENGINE, engine).apply()

        Log.d(TAG, "rawSearchText= " + rawSearchText)
        if (rawSearchText.isNotEmpty()) {
            setSearchText(TextFieldValue(rawSearchText))
            //_searchText.value = TextFieldValue(rawSearchText)

            Log.d(TAG, "set engine to: " + engineUrl.value)
            setUrl()

        }
        /*
        *TODO check if works when only in condition above
        */
        /*
            Log.d(TAG, "set engine to: " + engineUrl.value)
            setUrl()
    */
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


    fun setBottomBarHeight(height: Dp) {
        _bottomBarHeight.value = height
    }

    fun deleteAllTranslations() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "delete all translations")
            translationRepository.deleteTranslations()
            updateLastTranslations()
        }
    }


}