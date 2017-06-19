package id.ramadani.quake.ui.main

import android.content.Context
import android.support.v4.content.AsyncTaskLoader
import id.ramadani.quake.data.Quake
import id.ramadani.quake.data.network.Usgs

/**
 * Created by dani on 6/16/17.
 */
class QuakesLoader(context: Context?) :
        AsyncTaskLoader<List<Quake>>(context) {

    private var mQuakes: List<Quake>? = arrayListOf()

    override fun onStartLoading() {
        if (mQuakes!!.isNotEmpty()) {
            deliverResult(mQuakes)
        } else {
            forceLoad()
        }
    }

    override fun loadInBackground(): List<Quake> {
        return Usgs().getQuakes()
    }

    override fun deliverResult(data: List<Quake>?) {
        mQuakes = data
        super.deliverResult(data)
    }
}