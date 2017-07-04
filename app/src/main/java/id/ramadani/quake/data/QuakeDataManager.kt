package id.ramadani.quake.data

import android.content.Context
import android.preference.PreferenceManager
import id.ramadani.quake.R
import id.ramadani.quake.data.model.Quake
import id.ramadani.quake.data.model.SharedPrefs
import id.ramadani.quake.data.network.QuakeApi
import id.ramadani.quake.data.network.Usgs

/**
 * Created by dani on 6/15/17.
 */
class QuakeDataManager(val context: Context) : QuakeDataManagerContract {

    override fun getQuakes(): List<Quake> = arrayListOf()

    override fun saveQuakes(quakes: List<Quake>): Unit = TODO("not implemented")

    override fun getQuakesFromApi(): List<Quake> {
        val sharedPrefs = getSharedPrefs()

        return QuakeApi(Usgs()).getQuakes(sharedPrefs.minMagnitude)
    }

    override fun getSharedPrefs(): SharedPrefs {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val minMagnitude = sharedPrefs
                .getString(context.getString(R.string.settings_min_magnitude_key),
                        context.getString(R.string.settings_min_magnitude_default))

        return SharedPrefs(minMagnitude.toDouble())
    }
}
