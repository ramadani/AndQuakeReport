package id.ramadani.quake

import android.graphics.Rect
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import id.ramadani.quake.data.Quake
import id.ramadani.quake.data.QuakeQueryUtils
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var mRvQuakes: RecyclerView
    private val mQuakes: ArrayList<Quake> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = getString(R.string.main_title)

        mRvQuakes = findViewById(R.id.rv_quakes) as RecyclerView
        mQuakes.addAll(QuakeQueryUtils.extractEarthquakes())

        val quakesAdapter = QuakesAdapter(mQuakes)

        mRvQuakes.adapter = quakesAdapter
        mRvQuakes.layoutManager = LinearLayoutManager(this)
    }
}
