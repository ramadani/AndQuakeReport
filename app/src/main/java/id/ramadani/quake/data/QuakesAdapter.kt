package id.ramadani.quake.data

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import id.ramadani.quake.R

/**
 * Created by dani on 6/13/17.
 */
class QuakesAdapter(val quakes: List<Quake>) : RecyclerView.Adapter<QuakesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val quakeView = inflater.inflate(R.layout.item_quake, parent, false)

        return ViewHolder(quakeView)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val quake = quakes[position]

        holder?.bind(quake)
    }

    override fun getItemCount(): Int {
        return quakes.size
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        private val tvCity = itemView!!.findViewById(R.id.tv_city) as TextView

        fun bind(quake: Quake) {
            tvCity.text = quake.city
        }
    }
}