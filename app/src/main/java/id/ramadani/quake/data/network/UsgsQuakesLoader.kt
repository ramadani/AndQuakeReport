package id.ramadani.quake.data.network

import android.content.Context
import android.support.v4.content.AsyncTaskLoader
import android.util.Log
import id.ramadani.quake.data.Quake
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.nio.charset.Charset
import java.util.*

/**
 * Created by dani on 6/16/17.
 */
class UsgsQuakesLoader(context: Context?) : AsyncTaskLoader<List<Quake>>(context) {

    private var mQuakes: List<Quake>? = arrayListOf()

    companion object {
        val LOG_TAG = UsgsQuakesLoader::class.java.simpleName
        val USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=" +
                "geojson&starttime=2012-01-01&endtime=2012-12-01&minmagnitude=6";
    }

    override fun onStartLoading() {
        if (mQuakes!!.isNotEmpty()) {
            deliverResult(mQuakes)
        } else {
            forceLoad()
        }
    }

    override fun loadInBackground(): List<Quake> {
        val url = createUrl(USGS_REQUEST_URL)
        var quakesJSON: String = ""

        try {
            quakesJSON = makeHttpRequest(url)
        } catch (e: IOException) {
            Log.e(LOG_TAG, "Failed to get quakes result", e)
        }

        val quakes = extractFromJson(quakesJSON)

        return quakes
    }

    override fun deliverResult(data: List<Quake>?) {
        mQuakes = data

        super.deliverResult(data)
    }

    private fun createUrl(urlStr: String): URL? {
        val url: URL

        try {
            url = URL(urlStr)
        } catch (e: MalformedURLException) {
            Log.e(LOG_TAG, "Error with creating URL", e)
            return null
        }

        return url
    }

    private fun makeHttpRequest(url: URL?): String {
        var jsonRes: String = ""
        var urlConnection: HttpURLConnection? = null
        var inputStream: InputStream? = null

        try {
            urlConnection = url?.openConnection() as HttpURLConnection
            urlConnection.requestMethod = "GET"
            urlConnection.readTimeout = 10000
            urlConnection.connectTimeout = 15000
            urlConnection.connect()
            inputStream = urlConnection.inputStream

            jsonRes = readFromStream(inputStream)

        } catch (e: IOException) {
            Log.e(LOG_TAG, "IOException catched", e)
        } finally {
            if (urlConnection != null) urlConnection.disconnect()
            if (inputStream != null) inputStream.close()
        }

        return jsonRes
    }

    private fun readFromStream(inputStream: InputStream?): String {
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

    private fun extractFromJson(quakesJSON: String): ArrayList<Quake> {
        val quakes: ArrayList<Quake> = ArrayList()

        try {
            val quakesJson = JSONObject(quakesJSON)
            val arrayOfQuake = quakesJson.getJSONArray("features")

            for (i in (0..(arrayOfQuake.length() - 1))) {
                val quakeObj = arrayOfQuake.getJSONObject(i)
                val quakeProperties = quakeObj.getJSONObject("properties")
                val magnitude = quakeProperties.getDouble("mag")
                val location = quakeProperties.getString("place")
                val timeInMilliseconds = quakeProperties.getLong("time")
                val datetime = Date(timeInMilliseconds)

                quakes.add(Quake(location, magnitude, datetime))
            }
        } catch (e: JSONException) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e)
        }

        return quakes
    }
}