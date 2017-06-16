package id.ramadani.quake.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.LoaderManager.LoaderCallbacks
import android.support.v4.content.Loader
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ProgressBar
import id.ramadani.quake.R
import id.ramadani.quake.data.Quake
import id.ramadani.quake.data.QuakeDataManager
import id.ramadani.quake.data.network.UsgsQuakesLoader
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), QuakesViewContract {

    private lateinit var mRvQuakes: RecyclerView
    private lateinit var mPbQuakes: ProgressBar
    private var mPresenter: QuakesPresenterContract<QuakesViewContract>? = null
    private val mQuakes: ArrayList<Quake> = ArrayList()
    private val mQuakesAdapter: QuakesAdapter = QuakesAdapter(mQuakes)

    private val mLoaderCallbacks = object : LoaderCallbacks<List<Quake>> {
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<Quake>>? {
            return UsgsQuakesLoader(this@MainActivity)
        }

        override fun onLoadFinished(loader: Loader<List<Quake>>?, data: List<Quake>?) {
            addToQuakeList(data!!)
        }

        override fun onLoaderReset(loader: Loader<List<Quake>>?) {
            clearingQuakeList()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = getString(R.string.main_title)

        mRvQuakes = findViewById(R.id.rv_quakes) as RecyclerView
        mPbQuakes = findViewById(R.id.pb_quakes) as ProgressBar

        mRvQuakes.adapter = mQuakesAdapter
        mRvQuakes.layoutManager = LinearLayoutManager(this)

        mPresenter = QuakesPresenter(QuakeDataManager())
        mPresenter!!.attachView(this)
//        mPresenter!!.getQuakeList()

        supportLoaderManager.initLoader(0, null, mLoaderCallbacks)
    }

    override fun addToQuakeList(quakes: List<Quake>) {
        mQuakes.addAll(quakes)
        mQuakesAdapter.notifyDataSetChanged()
    }

    override fun clearingQuakeList() {
        mQuakes.clear()
        mQuakesAdapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        mPbQuakes.visibility = ProgressBar.VISIBLE
    }

    override fun hideLoading() {
        mPbQuakes.visibility = ProgressBar.GONE
    }
}
