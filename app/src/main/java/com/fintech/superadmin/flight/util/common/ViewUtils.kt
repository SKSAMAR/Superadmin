package com.fintech.superadmin.flight.util.common

import android.annotation.SuppressLint
import android.content.Context
import android.net.ParseException
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.fintech.superadmin.flight.domain.DateModel
import com.fintech.superadmin.flight.domain.timing.Time
import com.fintech.superadmin.flight.util.type.ClassType
import com.kizitonwose.calendar.core.CalendarDay
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONStringer
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*


object ViewUtils {

    const val BASE_FLIGHT_IMAGE = "https://ethhub.in/Dashboard/User/assets/img/"

    const val BASE_IMAGE_TYPE = ".png"

    fun String.timeFromMinutesToActual(): Time? {
        try {
            var timeFormat = this.toInt() / 60 // Hours
            val reminder = this.toInt() % 60 // Minutes
            val journeyDay = timeFormat / 24 //Journey Of the day
            if (journeyDay>0){
                val times = journeyDay * 24
                timeFormat -= times
            }

            return Time(
                hours = timeFormat,
                minutes = reminder,
                journeyOfTheDay = journeyDay,
                twentyFourHoursTime = "$timeFormat hrs $reminder min",
                twelveHoursTime = twentyHoursToTwelveHours("$timeFormat:$reminder"),

                )
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: Throwable) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


    @SuppressLint("SimpleDateFormat")
    private fun twentyHoursToTwelveHours(time: String): String {
        return try {
            val sdf = SimpleDateFormat("H:mm")
            val dateObj: Date = sdf.parse(time) as Date
            println(dateObj)
            SimpleDateFormat("K:mm a").format(dateObj)
        } catch (e: ParseException) {
            e.printStackTrace()
            e.localizedMessage ?: "some error"
        }
    }

    fun checkObjectOrArray(json: String, fieldName: String): ClassType {
        try {
            val dataObject = JSONObject(json)
            val json: Any = dataObject.get(fieldName)
            return if (json is JSONObject) {
                ClassType.OBJECT
            } else if (json is JSONArray) {
                ClassType.ARRAY
            } else if (json is JSONStringer) {
                ClassType.STRING
            } else{
                ClassType.SOMETHINELSE
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            return ClassType.SOMETHINELSE
        }
    }

    fun Context.showLongToast(message:String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun Context.showToast(message:String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun Time.toProperTime(): String{
        return "$hours : $minutes"
    }


    fun getStartOfDayInMillis(date: Date): Long {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    fun getEndOfDayInMillis(date: Date): Long {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.timeInMillis
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun CalendarDay.toDateModel(): DateModel{
        val calendar = Calendar.getInstance()
        calendar.set(this.date.year, this.date.month.value, this.date.dayOfYear)
        val date = calendar.time
        return toSpecificDateModel(date)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun LocalDate.toDateModel(): DateModel {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, this.year)
        calendar.set(Calendar.MONTH, this.monthValue - 1) // Calendar month is 0-based
        calendar.set(Calendar.DAY_OF_MONTH, this.dayOfMonth)
        val date = calendar.time
        return toSpecificDateModel(date)
    }


    private fun toSpecificDateModel(date: Date): DateModel {
        val sdf = SimpleDateFormat("MM/dd/yyyy")
        val currentDate = sdf.format(date)

        val sdf2 = SimpleDateFormat("MMM, d")
        val englishDate = sdf2.format(date)

        val sdf3 = SimpleDateFormat("EEEE")
        val day = sdf3.format(date)

        val sdf4 = SimpleDateFormat("HH:mm:ss z")
        val time = sdf4.format(date)
        return DateModel(
            classicDate = currentDate,
            englishData = englishDate,
            time = time,
            day = day,
            date = date,
            millisecond = date.time
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun YearMonth.displayText(short: Boolean = false): String {
        return "${this.month.displayText(short = short)} ${this.year}"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun Month.displayText(short: Boolean = true): String {
        val style = if (short) TextStyle.SHORT else TextStyle.FULL
        return getDisplayName(style, Locale.ENGLISH)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun DayOfWeek.displayText(uppercase: Boolean = false): String {
        return getDisplayName(TextStyle.SHORT, Locale.ENGLISH).let { value ->
            if (uppercase) value.uppercase(Locale.ENGLISH) else value
        }
    }

    fun Float.roundOffDecimal(): Float {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(this).toFloat()
    }

}