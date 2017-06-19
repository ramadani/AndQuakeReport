package id.ramadani.quake.ui.main

import id.ramadani.quake.base.View
import id.ramadani.quake.data.Quake

/**
 * Created by dani on 6/15/17.
 */
interface QuakesViewContract : View {

    fun addToQuakeList(quakes: List<Quake>)

    fun openQuakeMap(quake: Quake)

    fun clearingQuakeList()

    fun showLoading()

    fun hideLoading()
}
