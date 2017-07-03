package id.ramadani.quake.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.LoaderManager.LoaderCallbacks
import android.support.v4.content.ContextCompat
import android.support.v4.content.Loader
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import id.ramadani.quake.R
import id.ramadani.quake.data.Quake
import id.ramadani.quake.data.QuakeDataManager
import id.ramadani.quake.utils.InternetUtils
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), QuakesViewContract {
    companion object {
        private val LOG_TAG = MainActivity::class.java.simpleName
        private val QUAKES_LOADER_ID = 1
    }

    private lateinit var mRvQuakes: RecyclerView
    private lateinit var mPbQuakes: ProgressBar
    private lateinit var mTvEmpty: TextView
    private val mQuakes: ArrayList<Quake> = ArrayList()
    private val mQuakesAdapter: QuakesAdapter = QuakesAdapter(mQuakes)

    private val mQuakesLoaderCallbacks = object : LoaderCallbacks<List<Quake>> {
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<Quake>>? {
            toggleLoading(true)
            return QuakesLoader(this@MainActivity, QuakeDataManager())
        }

        override fun onLoadFinished(loader: Loader<List<Quake>>?, data: List<Quake>?) {
            toggleLoading(false)

            if (data!!.isNotEmpty()) {
                toggleList(true)
                addToQuakeList(data)
            } else {
                mTvEmpty.text = getString(R.string.quakes_empty)
                toggleEmptyState(true)
            }
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
        mTvEmpty = findViewById(R.id.tv_empty) as TextView

        mRvQuakes.adapter = mQuakesAdapter
        mRvQuakes.layoutManager = LinearLayoutManager(this)

        val divider = DividerItemDecoration(mRvQuakes.context, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(baseContext, R.drawable.item_quake_divider))
        mRvQuakes.addItemDecoration(divider)

        if (InternetUtils(applicationContext).isConnected()) {
            supportLoaderManager.initLoader(QUAKES_LOADER_ID, null, mQuakesLoaderCallbacks)
        } else {
            toggleEmptyState(true)
            mTvEmpty.text = getString(R.string.no_internet)
        }
    }

    override fun onStart() {
        super.onStart()

        mQuakesAdapter.setOnItemClickListener(object : QuakesAdapter.OnItemClickListener {
            override fun onItemClick(quake: Quake) = openQuakeMap(quake)
        })
    }

    override fun addToQuakeList(quakes: List<Quake>) {
        mQuakes.addAll(quakes)
        mQuakesAdapter.notifyDataSetChanged()
    }

    override fun clearingQuakeList() {
        mQuakes.clear()
        mQuakesAdapter.notifyDataSetChanged()
    }

    override fun openQuakeMap(quake: Quake) {
        Toast.makeText(this, quake.location, Toast.LENGTH_SHORT).show()
    }

    override fun toggleLoading(state: Boolean) {
        mPbQuakes.visibility = if (state) ProgressBar.VISIBLE else {
            ProgressBar.GONE
        }
    }

    override fun toggleList(state: Boolean) {
        mRvQuakes.visibility = if (state) View.VISIBLE else {
            View.GONE
        }
    }

    override fun toggleEmptyState(state: Boolean) {
        mTvEmpty.visibility = if (state) View.VISIBLE else {
            View.GONE
        }
    }
}
