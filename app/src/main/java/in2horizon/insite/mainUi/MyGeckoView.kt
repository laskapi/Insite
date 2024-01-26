package in2horizon.insite.mainUi

import android.content.ClipboardManager
import android.content.Context
import android.os.SystemClock
import android.util.Log
import android.view.MotionEvent
import org.mozilla.geckoview.GeckoView


class MyGeckoView(context: Context, private val viewModel: TransViewModel) : GeckoView(context) {

    val TAG = javaClass.name
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as
            ClipboardManager

        override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
            super.onWindowFocusChanged(hasWindowFocus)

            //   setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS)
            Log.d(TAG, "window focus changed to : " + hasWindowFocus)
            if (hasWindowFocus && hasFocus()) {
                Log.d(TAG, "and geckoView has focus ")
                callMyOnClick()
                saveTranslation()

            } else {
                clipboardManager?.clearPrimaryClip()
            }
        }

        private fun saveTranslation() {
            var dst = clipboardManager?.primaryClip?.getItemAt(0)?.text
            Log.d(TAG, dst.toString())
            viewModel.addTranslation(dst)
        }


        private fun callMyOnClick() {
            Log.d(TAG, "clicked liistener")
            val downTime = SystemClock.uptimeMillis()
             viewModel.selectionCoords?.let{
                dispatchTouchEvent(
                    MotionEvent.obtain(
                        downTime,
                        SystemClock.uptimeMillis(),
                        MotionEvent.ACTION_DOWN,
                        it.width,
                        it.height,
                        0
                    )
                )

                dispatchTouchEvent(
                    MotionEvent.obtain(
                        downTime,
                        SystemClock.uptimeMillis(),
                        MotionEvent.ACTION_UP,
                        it.width,
                        it.height,
                        0
                    )
                )
            }
        }

    }