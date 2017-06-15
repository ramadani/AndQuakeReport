package id.ramadani.quake.ui.main

/**
 * Created by dani on 6/15/17.
 */
class QuakesPresenter : QuakesPresenterContract<QuakesViewContract> {

    private var mView: QuakesViewContract? = null

    override fun attachView(view: QuakesViewContract) {
        mView = view
    }

    override fun getQuakeList() {
        mView!!.showLoading()
        mView!!.hideLoading()
    }
}
