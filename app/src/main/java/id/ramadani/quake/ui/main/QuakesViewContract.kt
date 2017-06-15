package id.ramadani.quake.ui.main

import id.ramadani.quake.base.View
import id.ramadani.quake.data.Quake

/**
 * Created by dani on 6/15/17.
 */
interface QuakesViewContract : View {

    fun updateQuakeList(quakes: List<Quake>)

    fun showLoading()

    fun hideLoading()
}