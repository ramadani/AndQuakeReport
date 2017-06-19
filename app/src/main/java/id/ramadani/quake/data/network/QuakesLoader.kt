package id.ramadani.quake.data.network

import android.content.Context
import android.net.Uri
import android.support.v4.content.AsyncTaskLoader
import android.util.Log
import id.ramadani.quake.data.Quake
import id.ramadani.quake.utils.FormatterUtils
import id.ramadani.quake.utils.HttpUtils
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

/**
 * Created by dani on 6/16/17.
 */
class QuakesLoader(context: Context?, val minMag: Double, val startDate: Date,
                   val endDate: Date) : AsyncTaskLoader<List<Quake>>(context) {

    private var mQuakes: List<Quake>? = arrayListOf()

    companion object {
        val LOG_TAG = QuakesLoader::class.java.simpleName
    }

    override fun onStartLoading() {
        if (mQuakes!!.isNotEmpty()) {
            deliverResult(mQuakes)
        } else {
            forceLoad()
        }
    }

    override fun loadInBackground(): List<Quake> {
        val urlStr = buildUrl()
        val url = HttpUtils.createUrl(urlStr)
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

    private fun buildUrl(): String {
        val uriBuilder = Uri.Builder()

        uriBuilder.scheme("https")
                .authority("earthquake.usgs.gov")
                .appendPath("fdsnws")
                .appendPath("event")
                .appendPath("1")
                .appendPath("query")
                .appendQueryParameter("format", "geojson")
                .appendQueryParameter("starttime", FormatterUtils.formatDateTime(startDate))
                .appendQueryParameter("endtime", FormatterUtils.formatDateTime(endDate))
                .appendQueryParameter("minmagnitude", minMag.toString())

        return uriBuilder.build().toString()
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

            jsonRes = HttpUtils.readFromStream(inputStream)
        } catch (e: IOException) {
            Log.e(LOG_TAG, "IOException catched", e)
        } finally {
            if (urlConnection != null) urlConnection.disconnect()
            if (inputStream != null) inputStream.close()
        }

        return jsonRes
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