package id.ramadani.quake.ui.quakes

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import id.ramadani.quake.R
import id.ramadani.quake.data.model.Quake

/**
 * Created by dani on 6/13/17.
 */
class QuakesAdapter(val quakes: List<Quake>) : RecyclerView.Adapter<QuakeViewHolder>() {

    var mItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): QuakeViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val quakeView = inflater.inflate(R.layout.item_quake, parent, false)

        return QuakeViewHolder(quakeView)
    }

    override fun onBindViewHolder(holder: QuakeViewHolder?, position: Int) {
        val quake = quakes[position]
        holder?.bind(quake, mItemClickListener)
    }

    override fun getItemCount(): Int = quakes.size

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mItemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(quake: Quake)
    }
}