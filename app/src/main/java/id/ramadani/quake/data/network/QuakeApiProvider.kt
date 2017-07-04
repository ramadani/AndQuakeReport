package id.ramadani.quake.data.network

import id.ramadani.quake.data.model.Quake

/**
 * Created by dani on 6/16/17.
 */
interface QuakeApiProvider {
    fun getQuakes(minMag: Double) : List<Quake>
}
