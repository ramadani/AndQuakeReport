package id.ramadani.quake.ui.main

import id.ramadani.quake.base.View

/**
 * Created by dani on 6/15/17.
 */
interface QuakesPresenterContract<in T : View> {

    fun attachView(view: T)

    fun getQuakeList()
}