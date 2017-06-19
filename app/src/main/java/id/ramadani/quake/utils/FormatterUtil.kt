package id.ramadani.quake.utils

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by dani on 6/19/17.
 */
class FormatterUtil {

    companion object {
        fun formatDateTime(datetime: Date, format: String = "yyyy-MM-dd"): String
                = SimpleDateFormat(format, Locale.getDefault()).format(datetime)

        fun formatDecimal(decimal: Double, format: String = "0.0"): String
                = DecimalFormat(format).format(decimal)
    }
}
