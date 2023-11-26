package in2horizon.insite

/*
import android.util.Log
import android.util.Patterns
import android.webkit.URLUtil
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.in2horizon.insite.db.Site
import com.gmail.in2horizon.insite.db.SiteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransViewModel @Inject constructor(private val siteRepository: SiteRepository): ViewModel() {

    private val _goForward=MutableStateFlow(false)
    val goForward=_goForward.asStateFlow()

    private val _goBack=MutableStateFlow(false)
    val goBack=_goBack.asStateFlow()

    var history= emptyList<Site>()

    private val _translation = MutableStateFlow("")
    val translation = _translation.asStateFlow()

    private val _sourceText = MutableStateFlow("")
    val sourceText = _sourceText.asStateFlow()

    private val _showTranslation = MutableStateFlow(false)
    val showTranslation = _showTranslation.asStateFlow()

    private val _address = MutableStateFlow(TextFieldValue(""))
    val address = _address.asStateFlow()

    private val _url =MutableStateFlow("https://www.tagesschau.de/")
    val url = _url.asStateFlow()



    fun setTranslation(translation:String){
        _translation.value=translation
    }
    fun setSourceText(text: String) {
        _sourceText.value = text


 }

    fun setShowTranslation(showTranslation: Boolean) {
     _showTranslation.value = showTranslation
    }

    fun setAddress(addr: TextFieldValue) {
        _address.value = addr
        */
/*getHistory()
        Log.d("transview",history.toString())*//*


    }

    fun setUrl(){

        if(Patterns.WEB_URL.matcher(address.value.text).matches()){
            val guessedUrl=URLUtil.guessUrl(address.value.text)
            _url.value=StringBuilder(guessedUrl).insert(4,"s").toString()
            Log.d("transview","valid "+address.value+" :: " +_url.value)
        }
        else{
            _url.value="https://www.google.pl/search?q="+address.value.text
            Log.d("transview","invalid "+_url.value)
        }
    }

    fun addToHistory(url: String?) {
        viewModelScope.launch(Dispatchers.IO) {
          url?.let{siteRepository.insertSite(it)
            }
        }
    }

    fun getHistory(){
        viewModelScope.launch(Dispatchers.IO){
            history=siteRepository.getSites()


        }
    }


    fun goBack(doNow:Boolean) {
        _goBack.value=doNow;
    }
    fun goForward(doNow:Boolean) {
        _goForward.value=doNow;
    }


}*/
