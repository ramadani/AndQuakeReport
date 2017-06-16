package id.ramadani.quake.ui.main

import id.ramadani.quake.data.QuakeDataManagerContract

/**
 * Created by dani on 6/15/17.
 */
class QuakesPresenter(dataManager: QuakeDataManagerContract) :
        QuakesPresenterContract<QuakesViewContract> {

    private val quakeDataManager: QuakeDataManagerContract = dataManager
    private var mView: QuakesViewContract? = null

    override fun attachView(view: QuakesViewContract) {
        mView = view
    }

    override fun getQuakeList() {
        mView!!.showLoading()

        val quakes = quakeDataManager.getQuakesFromApi()
        mView!!.addToQuakeList(quakes)

        mView!!.hideLoading()
    }
}
