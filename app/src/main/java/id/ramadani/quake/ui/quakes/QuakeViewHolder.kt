package id.ramadani.quake.ui.quakes

import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import id.ramadani.quake.R
import id.ramadani.quake.data.model.Quake
import id.ramadani.quake.utils.FormatterUtils

/**
 * Created by dani on 6/19/17.
 */
class QuakeViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    companion object {
        val LOCATION_SEPARATOR = " of "
    }

    private val tvLocation = itemView!!.findViewById(R.id.tv_primary_location) as TextView
    private val tvLocationOffset = itemView!!.findViewById(R.id.tv_location_offset) as TextView
    private val tvMag = itemView!!.findViewById(R.id.tv_mag) as TextView
    private val tvDate = itemView!!.findViewById(R.id.tv_date) as TextView
    private val tvTime = itemView!!.findViewById(R.id.tv_time) as TextView

    fun bind(quake: Quake, itemClickListener: QuakesAdapter.OnItemClickListener?) {
        val (locationOffset, primaryLocation) = getLocation(quake.location)
        val magnitudeCircle = tvMag.background as GradientDrawable

        tvLocation.text = primaryLocation
        tvLocationOffset.text = locationOffset
        tvMag.text = FormatterUtils.formatDecimal(quake.magnitude)
        tvDate.text = FormatterUtils.formatDateTime(quake.date, "LLL dd, yyyy")
        tvTime.text = FormatterUtils.formatDateTime(quake.date, "h:mm a")

        magnitudeCircle.setColor(getMagnitudeColor(quake.magnitude))

        itemView.setOnClickListener {
            itemClickListener?.onItemClick(quake)
        }
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