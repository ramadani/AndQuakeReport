package id.ramadani.quake.ui.main

import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import id.ramadani.quake.R
import id.ramadani.quake.data.Quake
import java.text.DecimalFormat
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

    override fun getItemCount(): Int = quakes.size

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        companion object {
            val LOCATION_SEPARATOR = " of "
        }

        private val tvLocation = itemView!!.findViewById(R.id.tv_primary_location) as TextView
        private val tvLocationOffset = itemView!!.findViewById(R.id.tv_location_offset) as TextView
        private val tvMag = itemView!!.findViewById(R.id.tv_mag) as TextView
        private val tvDate = itemView!!.findViewById(R.id.tv_date) as TextView
        private val tvTime = itemView!!.findViewById(R.id.tv_time) as TextView

        fun bind(quake: Quake) {
            val quakeLocation = getLocation(quake.location)
            val magnitudeCircle = tvMag.background as GradientDrawable

            tvLocation.text = quakeLocation.primaryLocation
            tvLocationOffset.text = quakeLocation.locationOffset
            tvMag.text = formatMagnitude(quake.magnitude)
            tvDate.text = formatDateTime(quake.date, "LLL dd, yyyy")
            tvTime.text = formatDateTime(quake.date, "h:mm a")

            magnitudeCircle.setColor(getMagnitudeColor(quake.magnitude))
        }

        private fun getLocation(location: String): QuakeLocation {
            val locationOffset: String
            val primaryLocation: String

            if (location.contains(LOCATION_SEPARATOR)) {
                val parts: List<String> = location.split(LOCATION_SEPARATOR)
                locationOffset = parts[0] + LOCATION_SEPARATOR
                primaryLocation = parts[1]
            } else {
                locationOffset = itemView.context.getString(R.string.near_the)
                primaryLocation = location
            }

            return QuakeLocation(locationOffset, primaryLocation)
        }

        private fun formatMagnitude(mag: Double, format: String = "0.0"): String
                = DecimalFormat(format).format(mag)

        private fun formatDateTime(datetime: Date, format: String): String
                = SimpleDateFormat(format, Locale.getDefault()).format(datetime)

        private fun getMagnitudeColor(mag: Double): Int {
            val magColorResId: Int
            val magFloor: Int = Math.floor(mag).toInt()

            when(magFloor) {
                1 -> magColorResId = R.color.magnitude1
                2 -> magColorResId = R.color.magnitude2
                3 -> magColorResId = R.color.magnitude3
                4 -> magColorResId = R.color.magnitude4
                5 -> magColorResId = R.color.magnitude5
                6 -> magColorResId = R.color.magnitude6
                7 -> magColorResId = R.color.magnitude7
                8 -> magColorResId = R.color.magnitude8
                9 -> magColorResId = R.color.magnitude9
                else -> magColorResId = R.color.magnitude10plus
            }

            return ContextCompat.getColor(itemView.context, magColorResId)
        }

        data class QuakeLocation(val locationOffset: String, val primaryLocation: String)
    }
}