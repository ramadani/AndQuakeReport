package id.ramadani.quake.data.network

import id.ramadani.quake.data.model.Quake

/**
 * Created by dani on 6/16/17.
 */
class Usgs : QuakeApiProvider {

    override fun getQuakes(): List<Quake> {
        return UsgsQuakesRequest().getList()
    }
}
