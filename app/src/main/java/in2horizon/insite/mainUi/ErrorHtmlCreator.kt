package in2horizon.insite.mainUi

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import in2horizon.insite.R
import org.mozilla.geckoview.WebRequestError
import java.io.File
import java.io.IOException
import kotlin.reflect.full.declaredMembers


class ErrorHtmlCreator {
    companion object Factory {
        val TAG = "ErrorHtmlCreator"

        @ApplicationContext
        lateinit var context: Context


        fun createHtmlErrorMessage(error: WebRequestError, uri: String?): String {

            val errorMessage = WebRequestError::class.declaredMembers.find {
                it.parameters.isEmpty() && it.isFinal && it
                    .returnType.classifier == Int::class
                        && it.call() == error.code
            }?.name ?: (context.getString(
                R.string
                    .default_loading_error_message
            ) + uri)

            try {
                val file = File.createTempFile("errorMessage", null)

                file.appendText(
                    "<!DOCTYPE html>\n" +
                            "<html lang=\"en\">\n" +
                            "<head>\n" +
                            "  <title>HTML Elements Reference</title>\n" +
                            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "\n" +
                            "<h2>"
                )
                file.appendText(
                    errorMessage
                )

                file.appendText(
                    "</h2>" +
                            "</body>\n" +
                            "</html>"
                )
                return file.toURI().toString()

            } catch (exception: IOException) {
                Log.d(TAG, exception.toString())
                return errorMessage
            }
        }
    }
}