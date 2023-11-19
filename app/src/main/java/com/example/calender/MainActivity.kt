package com.example.calender;

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu;
import android.view.MenuItem;


class MainActivity : AppCompatActivity() {
    // on below line we are creating
    // variables for text view and calendar view
    lateinit var dateTV: TextView
    lateinit var calendarView: CalendarView
    lateinit var datePickerSpinner: Spinner

    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        datePickerSpinner = findViewById(R.id.datePickerSpinner)

        // Create an array of date selection options
        val dateOptions = arrayOf("Day", "Month", "Year", "Week")

        // Create an ArrayAdapter for the Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dateOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Set the adapter for the Spinner
        datePickerSpinner.adapter = adapter

        // Set the initial selection to "Month"
        datePickerSpinner.setSelection(dateOptions.indexOf("Month"))

        // Set an item selected listener for the Spinner
        datePickerSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Handle the selected date option (day, month, year, week)
                val selectedOption = dateOptions[position]
                // You can perform actions based on the selected option here.
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
        // Declaring and initializing
        // the button from the layout file
        val mButton = findViewById<Button>(R.id.button)

        // Registering ID's
        val textView = findViewById<TextView>(R.id.idTVDate)
        val calenderView = findViewById<CalendarView>(R.id.calendarView)

        // using setonDateChangeListener
        calenderView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val correctedMonth = month + 1
            val date = "$correctedMonth/$dayOfMonth/$year"

            // Update the TextView with the corrected date.
            textView.text = date
        }

        // Event start and end time with date
        val startTime = "2022-02-1T09:00:00"
        val endTime = "2022-02-1T12:00:00"

        // Parsing the date and time
        val mSimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val mStartTime = mSimpleDateFormat.parse(startTime)
        val mEndTime = mSimpleDateFormat.parse(endTime)

        // When Button is clicked, Intent started
        // to create an event with given time
        mButton.setOnClickListener {
            val mIntent = Intent(Intent.ACTION_EDIT)
            mIntent.type = "vnd.android.cursor.item/event"
            mIntent.putExtra("beginTime", mStartTime.time)
            mIntent.putExtra("time", true)
            mIntent.putExtra("rule", "FREQ=YEARLY")
            mIntent.putExtra("endTime", mEndTime.time)
            mIntent.putExtra("title", "Geeksforgeeks Event")
            startActivity(mIntent)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        // first parameter is the file for icon and second one is menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // We are using switch case because multiple icons can be kept
        when (item.getItemId()) {
            R.id.shareButton -> {
                val sharingIntent = Intent(Intent.ACTION_SEND)

                // type of the content to be shared
                sharingIntent.type = "text/plain"

                // Body of the content
                val shareBody = ""

                // subject of the content. you can share anything
                val shareSubject = "Join my event!"

                // passing body of the content
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)

                // passing subject of the content
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject)
                startActivity(Intent.createChooser(sharingIntent, "Share using"))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
