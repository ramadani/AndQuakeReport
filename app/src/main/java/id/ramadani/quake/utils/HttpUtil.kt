package id.ramadani.quake.utils

import android.util.Log
import id.ramadani.quake.data.network.UsgsQuakesLoader
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.nio.charset.Charset

/**
 * Created by dani on 6/19/17.
 */
class HttpUtil {

    companion object {
        val LOG_TAG = HttpUtil::class.java.simpleName

        fun createUrl(urlStr: String): URL? {
            val url: URL

            try {
                url = URL(urlStr)
            } catch (e: MalformedURLException) {
                Log.e(LOG_TAG, "Error with creating URL", e)
                return null
            }

            return url
        }

        fun readFromStream(inputStream: InputStream?): String {
            val output = StringBuilder()

            if (inputStream != null) {
                val inputStreamReader = InputStreamReader(inputStream, Charset.forName("UTF-8"))
                val reader = BufferedReader(inputStreamReader)
                var line = reader.readLine()

                while (line != null) {
                    output.append(line)
                    line = reader.readLine()
                }
            }

            return output.toString()
        }
    }
}
