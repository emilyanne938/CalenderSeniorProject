package com.example.calender;

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var dateTV: TextView
    lateinit var calendarView: CalendarView
    lateinit var datePickerSpinner: Spinner
    lateinit var calenderSelector: ImageView
    lateinit var colorButton: Button

    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        val paddingValueInDP = 16

        calenderSelector = findViewById(R.id.calender_selector_icon)
        datePickerSpinner = findViewById(R.id.datePickerSpinner)
        colorButton = findViewById(R.id.colorButton) // Initializing the color button

        // Create an array of date selection options
        val dateOptions = arrayOf("Day", "Month", "Year", "Week")

        // Create an ArrayAdapter for the Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dateOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Set the adapter for the Spinner
        datePickerSpinner.adapter = adapter

        // Set the initial selection to "Month"
        datePickerSpinner.setSelection(dateOptions.indexOf("Month"))

        val imageView = findViewById<ImageView>(R.id.calender_selector_icon)

        // Set an item selected listener for the calender selector
        calenderSelector.setOnClickListener {
            val popupMenu = PopupMenu(this, imageView)
            popupMenu.menuInflater.inflate(R.menu.selector_dropdown_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.personal_calender -> {
                        true
                    }
                    R.id.school_calender -> {
                        true
                    }
                    R.id.work_calender -> {
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }

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
        calendarView = findViewById<CalendarView>(R.id.calendarView)

        // using setonDateChangeListener
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
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

        // Set a click listener for the color button
        colorButton.setOnClickListener {
            // Show a color picker dialog
            showColorPickerDialog()
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


    private fun showColorPickerDialog() {
        val initialColor = (calendarView.background as? ColorDrawable)?.color ?: Color.WHITE

        val colorPickerDialog = AlertDialog.Builder(this)
        colorPickerDialog.setTitle("Choose Color")

        val colorPickerView = layoutInflater.inflate(R.layout.color_picker_view, null)
        val redSeekBar = colorPickerView.findViewById<SeekBar>(R.id.redSeekBar)
        val greenSeekBar = colorPickerView.findViewById<SeekBar>(R.id.greenSeekBar)
        val blueSeekBar = colorPickerView.findViewById<SeekBar>(R.id.blueSeekBar)

        // Customize the SeekBars and set their progress based on the initial color

        colorPickerDialog.setView(colorPickerView)
        colorPickerDialog.setPositiveButton("OK") { _, _ ->
            // Get the selected color from the SeekBars and set it as the background color
            val selectedColor = Color.rgb(redSeekBar.progress, greenSeekBar.progress, blueSeekBar.progress)
            calendarView.setBackgroundColor(selectedColor)
        }
        colorPickerDialog.setNegativeButton("Cancel", null)

        colorPickerDialog.show()
    }
}

