package id.ramadani.quake.data

/**
 * Created by dani on 6/15/17.
 */
interface QuakeDataManagerContract {

    fun getQuakes(): List<Quake>

    fun saveQuakes(quakes: List<Quake>)

    fun getQuakesFromApi(): List<Quake>
}
