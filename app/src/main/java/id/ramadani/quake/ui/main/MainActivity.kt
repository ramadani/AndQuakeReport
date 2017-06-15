package id.ramadani.quake.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import id.ramadani.quake.R
import id.ramadani.quake.data.Quake
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), QuakesViewContract {

    private lateinit var mRvQuakes: RecyclerView
    private val mQuakes: ArrayList<Quake> = ArrayList()
    private val mQuakesAdapter: QuakesAdapter = QuakesAdapter(mQuakes)
    private val mPresenter: QuakesPresenterContract<QuakesViewContract> = QuakesPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = getString(R.string.main_title)

        mPresenter.attachView(this)

        mRvQuakes = findViewById(R.id.rv_quakes) as RecyclerView
        mRvQuakes.adapter = mQuakesAdapter
        mRvQuakes.layoutManager = LinearLayoutManager(this)

        mPresenter.getQuakeList()
    }

    override fun updateQuakeList(quakes: List<Quake>) {
        mQuakes.addAll(quakes)
        mQuakesAdapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        TODO("not implemented")
    }

    override fun hideLoading() {
        TODO("not implemented")
    }
}
