package id.ramadani.quake.ui.main

import id.ramadani.quake.data.QuakeQueryUtils

/**
 * Created by dani on 6/15/17.
 */
class QuakesPresenter : QuakesPresenterContract<QuakesViewContract> {

    private var mView: QuakesViewContract? = null

    override fun attachView(view: QuakesViewContract) {
        mView = view
    }

    override fun getQuakeList() {
        val quakes = QuakeQueryUtils.extractEarthquakes()

        mView!!.updateQuakeList(quakes)
    }
}