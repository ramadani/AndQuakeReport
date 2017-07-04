package id.ramadani.quake.data

import id.ramadani.quake.data.model.Quake

/**
 * Created by dani on 6/15/17.
 */
interface QuakeDataManagerContract {

    fun getQuakes(): List<Quake>

    fun saveQuakes(quakes: List<Quake>)

    fun getQuakesFromApi(): List<Quake>
}
