package id.ramadani.quake.data.network

import android.os.AsyncTask
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
class Usgs : QuakeApiProvider {

    companion object {
        val LOG_TAG = Usgs::class.java.simpleName
        val USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=" +
                "geojson&starttime=2012-01-01&endtime=2012-12-01&minmagnitude=6";
    }

    override fun getQuakes(): List<Quake> {
        return arrayListOf()
    }
}
