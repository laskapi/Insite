package in2horizon.insite.gecko

import android.os.Bundle
import android.util.Log
import in2horizon.insite.mainUi.ErrorHtmlCreator
import kotlinx.coroutines.flow.MutableStateFlow
import org.mozilla.geckoview.GeckoResult
import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.WebRequestError

object MyNavigationDelegate : GeckoSession.NavigationDelegate {
    private final val TAG = "MyNavigationDelegate"
    var canGoBack = MutableStateFlow(false);
    var observer: SessionObserver? = null
    override fun onCanGoBack(session: GeckoSession, canGoBack: Boolean) {
        this.canGoBack.value = canGoBack
    }

    override fun onLocationChange(
        session: GeckoSession,
        url: String?,
        perms: MutableList<GeckoSession.PermissionDelegate.ContentPermission>
    ) {
        Log.d(TAG, "onLocationChange to " + url.toString())
        url?.let {
            var bundle = Bundle()
            bundle.putString(SessionObserver.TEXT, it)
            observer?.update(SessionObserver.Action.NAVIGATE, bundle)
        }
        super.onLocationChange(session, url, perms)
    }

    override fun onLoadError(
        session: GeckoSession,
        uri: String?,
        error: WebRequestError
    ): GeckoResult<String>? {

        Log.d(TAG, "error = " + error.code.toString())
        return GeckoResult.fromValue(ErrorHtmlCreator.createHtmlErrorMessage(error, uri))

    }
}