package com.example.moviebooking2

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moviebooking2.AlarmBroad
import com.example.moviebooking2.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Movie1 : AppCompatActivity() {

    private lateinit var databaseHelper: MovieDatabaseHelper
    private lateinit var alarmManager: AlarmManager
    private lateinit var bookingActivity: BookingActivity

    var formatDate = SimpleDateFormat("dd MMMM YYYY", Locale.US)
    private lateinit var selectedTime: String
    private lateinit var selectedDate: String

    private lateinit var btnDate: Button
    private lateinit var btnTime: Button
    private lateinit var btnTrail: Button
    private lateinit var btnAlarm: Button
    private lateinit var btnAlarmStop: Button
    private lateinit var btnBook: Button
    private lateinit var tv: TextView
    private lateinit var etText: EditText

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie1) // Set the content view before finding views

        // Initialize your instance variables and database helper
        databaseHelper = MovieDatabaseHelper(this)

        // Initialize layout elements
        btnDate = findViewById(R.id.btnDate)
        btnTime = findViewById(R.id.btnTime)
        btnTrail = findViewById(R.id.btnTrail)
        btnAlarm = findViewById(R.id.btnAlarm)
        btnAlarmStop = findViewById(R.id.btnAlarmStop)
        btnBook = findViewById(R.id.btnBook)
        tv = findViewById(R.id.tv)
        etText = findViewById(R.id.etText)

        setupListeners() // Set up all listeners in a separate method
    }

    private fun setupListeners() {
        btnDate.setOnClickListener {
            val getDate = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                this,
                android.R.style.Theme_Holo_Dialog_MinWidth,
                { _, year, month, dayOfMonth ->
                    val selectDate = Calendar.getInstance().apply {
                        set(Calendar.YEAR, year)
                        set(Calendar.MONTH, month)
                        set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    }
                    selectedDate = formatDate.format(selectDate.time)
                    Toast.makeText(this, "DATE: $selectedDate", Toast.LENGTH_LONG).show()
                },
                getDate.get(Calendar.YEAR),
                getDate.get(Calendar.MONTH),
                getDate.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        btnTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                selectedTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(cal.time)
                Toast.makeText(this, "TIME: $selectedTime", Toast.LENGTH_LONG).show()
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        btnTrail.setOnClickListener {
            Intent(this, Trailer::class.java).apply {
                startActivity(this)
            }
        }

        btnAlarm.setOnClickListener {
           // val i = etText.text.toString().toIntOrNull() ?: 0
            //alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//
//            val intent = Intent(this, AlarmBroad::class.java)
//            val pendingIntent = PendingIntent.getBroadcast(this, 234, intent, PendingIntent.FLAG_IMMUTABLE)
//
//            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (i * 1000), pendingIntent)
//            Toast.makeText(this, "Alarm set for $i seconds", Toast.LENGTH_LONG).show()

            val i = etText.text.toString().toIntOrNull() ?: 0
            alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent = Intent(this, AlarmBroad::class.java).apply {
                action = AlarmBroad.ACTION_START_ALARM // Action to start the alarm
            }

            // Create the PendingIntent with FLAG_IMMUTABLE
            val pendingIntent = PendingIntent.getBroadcast(this, 234, intent, PendingIntent.FLAG_IMMUTABLE)

            // Set the alarm
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (i * 1000), pendingIntent)
            Toast.makeText(this, "Alarm set for $i seconds", Toast.LENGTH_LONG).show()
        }

        btnAlarmStop.setOnClickListener{
            val intent = Intent(this, AlarmBroad::class.java).apply {
                action = AlarmBroad.ACTION_STOP_ALARM // Set action to stop alarm
            }
            val pendingIntent = PendingIntent.getBroadcast(this, 234, intent, PendingIntent.FLAG_IMMUTABLE)
            sendBroadcast(intent) // Send broadcast to stop the alarm

            // Optionally, you can cancel the alarm
            alarmManager.cancel(pendingIntent)
            Toast.makeText(this, "Alarm stopped", Toast.LENGTH_SHORT).show()

        }

        btnBook.setOnClickListener {
            val movieName = findViewById<TextView>(R.id.textView).text.toString()
            val date = selectedDate
            val time = selectedTime
            val tickets = findViewById<EditText>(R.id.etTickets1).text.toString().toIntOrNull() ?: 0

            if (movieName.isNotBlank() && date.isNotBlank() && time.isNotBlank() && tickets > 0) {
                databaseHelper.insertBooking(movieName, date, time, tickets)
                Toast.makeText(this, "Added Successfully", Toast.LENGTH_LONG).show()
                bookingActivity.refreshData()
            }
            else {
                Toast.makeText(this, "some data is blank", Toast.LENGTH_LONG).show()
            }
        }
    }
}