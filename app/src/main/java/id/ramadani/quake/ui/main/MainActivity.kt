package id.ramadani.quake.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.LoaderManager.LoaderCallbacks
import android.support.v4.content.ContextCompat
import android.support.v4.content.Loader
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ProgressBar
import id.ramadani.quake.R
import id.ramadani.quake.data.Quake
import id.ramadani.quake.data.network.UsgsQuakesLoader
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), QuakesViewContract {

    private lateinit var mRvQuakes: RecyclerView
    private lateinit var mPbQuakes: ProgressBar
    private val mQuakes: ArrayList<Quake> = ArrayList()
    private val mQuakesAdapter: QuakesAdapter = QuakesAdapter(mQuakes)

    private val mQuakesLoaderCallbacks = object : LoaderCallbacks<List<Quake>> {
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<Quake>>? {
            showLoading()

            val calendar = Calendar.getInstance()
            val now = calendar.time
            calendar.add(Calendar.MONTH, -3)
            val threeMonthsAgo = calendar.time

            return UsgsQuakesLoader(this@MainActivity, 3.0, threeMonthsAgo, now)
        }

        override fun onLoadFinished(loader: Loader<List<Quake>>?, data: List<Quake>?) {
            addToQuakeList(data!!)
            hideLoading()
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

        val divider = DividerItemDecoration(mRvQuakes.context, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(baseContext, R.drawable.item_quake_divider))
        mRvQuakes.addItemDecoration(divider)

        supportLoaderManager.initLoader(0, null, mQuakesLoaderCallbacks)
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
