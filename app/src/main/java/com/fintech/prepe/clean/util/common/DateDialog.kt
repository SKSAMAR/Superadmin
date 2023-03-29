package com.fintech.prepe.clean.util.common

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import java.util.*

object DateDialogSys{

    // Initializing a Calendar
    private val mCalendar = Calendar.getInstance()
    private var mYear: Int
    private var mMonth: Int
    private var mDay: Int

    init {
        // Fetching current year, month and day
        mYear = mCalendar.get(Calendar.YEAR)
        mMonth = mCalendar.get(Calendar.MONTH)
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
        mCalendar.time = Date()
    }


    fun Context.showDateDialogFromPresent(onDateChange:(Date)->Unit){
        val mDatePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                mCalendar.set(mYear, mMonth, mDayOfMonth)
                onDateChange(mCalendar.time)
            }, mYear, mMonth, mDay
        )
        mDatePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        mDatePickerDialog.show()
    }

    fun Context.showDateDialogFromPast(onDateChange:(Date)->Unit){
        val mDatePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                mCalendar.set(mYear, mMonth, mDayOfMonth)
                onDateChange(mCalendar.time)
            }, mYear, mMonth, mDay
        )

        mDatePickerDialog.datePicker.maxDate = System.currentTimeMillis() - 1000
        mDatePickerDialog.show()
    }

}