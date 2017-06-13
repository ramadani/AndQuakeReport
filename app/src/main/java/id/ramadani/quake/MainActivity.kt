package id.ramadani.quake

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import id.ramadani.quake.data.Quake
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var mRvQuakes: RecyclerView
    private val mQuakes: ArrayList<Quake> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = getString(R.string.main_title)

        mRvQuakes = findViewById(R.id.rv_quakes) as RecyclerView

        (1..15).forEach {
            val numberOfMags = Array(10, { it + 1 }).toMutableList()
            Collections.shuffle(numberOfMags)
            val mag = numberOfMags.first()

            mQuakes.add(Quake("City $it", mag.toDouble(), "20 May, 2017"))
        }
        val quakesAdapter = QuakesAdapter(mQuakes)

        mRvQuakes.adapter = quakesAdapter
        mRvQuakes.layoutManager = LinearLayoutManager(this)
    }
}
