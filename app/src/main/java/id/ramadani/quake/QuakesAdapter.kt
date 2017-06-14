package id.ramadani.quake

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import id.ramadani.quake.data.Quake
import java.text.SimpleDateFormat
import java.util.*

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
        private val tvMag = itemView!!.findViewById(R.id.tv_mag) as TextView
        private val tvDate = itemView!!.findViewById(R.id.tv_date) as TextView
        private val tvTime = itemView!!.findViewById(R.id.tv_time) as TextView

        fun bind(quake: Quake) {
            tvCity.text = quake.city
            tvMag.text = quake.magnitude.toString()
            tvDate.text = SimpleDateFormat("LLL dd, yyyy", Locale.getDefault()).format(quake.date)
            tvTime.text = SimpleDateFormat("h:mm a", Locale.getDefault()).format(quake.date)
        }
    }
}