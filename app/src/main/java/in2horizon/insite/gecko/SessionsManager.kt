package in2horizon.insite.gecko

import android.content.Context
import android.os.Bundle
import android.util.SizeF
import dagger.hilt.android.qualifiers.ApplicationContext
import org.mozilla.geckoview.GeckoRuntime
import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.GeckoSession.NavigationDelegate
import org.mozilla.geckoview.GeckoSession.SelectionActionDelegate
import org.mozilla.geckoview.GeckoSessionSettings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionsManager @Inject constructor(@ApplicationContext var ctx: Context) {

    private var observer: SessionObserver? = null

    private var runtime = GeckoRuntime.create(ctx)

    private val selDelegate = object : SelectionActionDelegate {
        override fun onShowActionRequest(
            session: GeckoSession,
            selection: SelectionActionDelegate.Selection
        ) {

            var bundle = Bundle()
            bundle.putString(SessionObserver.TEXT, selection.text)
            selection.screenRect?.let {
                bundle.putSizeF(
                    SessionObserver.COORDS, SizeF(
                        it.centerX(),
                        it.centerY()
                    )
                )
                observer?.update(SessionObserver.Action.SELECT, bundle)
            }
        }
    }
        private val navDelegate = object : NavigationDelegate {
            override fun onLocationChange(
                session: GeckoSession,
                url: String?,
                perms: MutableList<GeckoSession.PermissionDelegate.ContentPermission>
            ) {
                url?.let{
                    var bundle = Bundle()
                    bundle.putString(SessionObserver.TEXT,it)
                    observer?.update(SessionObserver.Action.NAVIGATE, bundle)
                }
                    super.onLocationChange(session, url, perms)
            }
        }

        private val sessions = ArrayList<GeckoSession>()
        var index = 0

        init {
            //TODO load populate sessions array with  persisted sessions

            //or creating initial session
            addSession()

        }


        fun addSession(): GeckoSession {
            sessions.add(setupSession())
            index = sessions.size - 1
            return sessions.get(index)

        }

        fun get(index: Int): GeckoSession {
            return sessions.get(index)
        }

        fun setObserver(observer: SessionObserver) {
            this.observer = observer
        }

        fun removeObserver() {
            observer = null
        }


        private fun setupSession(): GeckoSession {

            val settings = GeckoSessionSettings.Builder()
                .usePrivateMode(true)
                .useTrackingProtection(true)
                .userAgentMode(GeckoSessionSettings.USER_AGENT_MODE_MOBILE)
                .userAgentOverride("")
                .suspendMediaWhenInactive(true)
                .allowJavascript(true)
                .build()


            val mSession = GeckoSession(settings)

            mSession.selectionActionDelegate = selDelegate
            mSession.navigationDelegate = navDelegate
            mSession.open(runtime)
            return mSession
        }


    }