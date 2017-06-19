package id.ramadani.quake.data.network

import id.ramadani.quake.data.Quake

/**
 * Created by dani on 6/16/17.
 */
class Usgs : QuakeApiProvider {

    companion object {
        val LOG_TAG = Usgs::class.java.simpleName
    }

    override fun getQuakes(): List<Quake> {
        return UsgsQuakesRequest().getList()
    }
}
