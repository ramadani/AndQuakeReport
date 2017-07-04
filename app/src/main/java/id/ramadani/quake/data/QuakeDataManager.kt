package id.ramadani.quake.data

import id.ramadani.quake.data.model.Quake
import id.ramadani.quake.data.network.QuakeApi
import id.ramadani.quake.data.network.Usgs

/**
 * Created by dani on 6/15/17.
 */
class QuakeDataManager : QuakeDataManagerContract {

    override fun getQuakes(): List<Quake> {
        return arrayListOf()
    }

    override fun saveQuakes(quakes: List<Quake>) {
        TODO("not implemented")
    }

    override fun getQuakesFromApi(): List<Quake> {
        return QuakeApi(Usgs()).getQuakes()
    }
}
