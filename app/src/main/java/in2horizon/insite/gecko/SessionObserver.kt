package in2horizon.insite.gecko

import android.os.Bundle

interface SessionObserver {


       companion object {
           const val COORDS = "coords"
           const val TEXT = "text"
       }

    enum class Action{
        SELECT,NAVIGATE
    }

    fun update(action:Action, bundle: Bundle)
}