package id.ramadani.quake.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ProgressBar
import id.ramadani.quake.R
import id.ramadani.quake.data.Quake
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), QuakesViewContract {

    private lateinit var mRvQuakes: RecyclerView
    private lateinit var mPbQuakes: ProgressBar
    private val mQuakes: ArrayList<Quake> = ArrayList()
    private val mQuakesAdapter: QuakesAdapter = QuakesAdapter(mQuakes)
    private val mPresenter: QuakesPresenterContract<QuakesViewContract> = QuakesPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = getString(R.string.main_title)

        mRvQuakes = findViewById(R.id.rv_quakes) as RecyclerView
        mPbQuakes = findViewById(R.id.pb_quakes) as ProgressBar

        mRvQuakes.adapter = mQuakesAdapter
        mRvQuakes.layoutManager = LinearLayoutManager(this)

        mPresenter.attachView(this)
        mPresenter.getQuakeList()
    }

    override fun updateQuakeList(quakes: List<Quake>) {
        mQuakes.addAll(quakes)
        mQuakesAdapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        mPbQuakes.visibility = ProgressBar.VISIBLE
    }

    override fun hideLoading() {
        mPbQuakes.visibility = ProgressBar.GONE
    }
}
