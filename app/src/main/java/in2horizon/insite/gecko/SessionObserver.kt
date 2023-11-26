package in2horizon.insite.gecko

import android.os.Bundle

interface SessionObserver {

       companion object {
           val COORDS = "coords"
           val TEXT = "text"
       }

    enum class Action{
        SELECT,NAVIGATE
    }

    fun update(action:Action,data: Bundle)
}