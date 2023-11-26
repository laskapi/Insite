package in2horizon.insite.gecko

import android.app.Activity
import org.mozilla.geckoview.GeckoRuntime

class MyGeckoRuntime private constructor() {
    companion object {
        @Volatile
        private var instance: GeckoRuntime? = null
        fun getInstance(activity: Activity): GeckoRuntime {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = GeckoRuntime.create(activity)
                    }
                }
            }
            return instance!!
        }
    }
}