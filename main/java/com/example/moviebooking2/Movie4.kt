package com.example.moviebooking2

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.moviebooking2.AlarmBroad
import com.example.moviebooking2.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Movie4 : AppCompatActivity() {
    var formatDate = SimpleDateFormat("dd MMMM YYYY", Locale.US)
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie4)
        val btnDate = findViewById<Button>(R.id.btnDate4)

        btnDate.setOnClickListener {
            val getDate = Calendar.getInstance()
            val datePicker = DatePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth, DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->

                val selectDate = Calendar.getInstance()
                selectDate.set(Calendar.YEAR, i)
                selectDate.set(Calendar.MONTH, i2)
                selectDate.set(Calendar.DAY_OF_MONTH, i3)
                val date = formatDate.format(selectDate.time)
                Toast.makeText(this, "DATE: " + date, Toast.LENGTH_LONG).show()

            }, getDate.get(Calendar.YEAR), getDate.get(Calendar.MONTH), getDate.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        }

        val btnTime = findViewById<Button>(R.id.btnTime4)
        var alarmManager: AlarmManager
        val intent = Intent(this, AlarmBroad::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 234, intent,
            PendingIntent.FLAG_IMMUTABLE)

        var tv = findViewById<TextView>(R.id.tv4)
        var etText = findViewById<EditText>(R.id.etText4)
//      Putting the time picker
        btnTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                Toast.makeText(this, "TIME: "+ SimpleDateFormat("HH:mm").format(cal.time), Toast.LENGTH_LONG).show()

            }





        }

        val btntrail = findViewById<Button>(R.id.btnTrail4)

        btntrail.setOnClickListener {
            Intent(this, Trailer3::class.java).apply{
                startActivity(this)
            }
        }

        val btnBook = findViewById<Button>(R.id.btnBook4)
        btnBook.setOnClickListener {

            var i = etText.text.toString().toInt()
            alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (i*1000), pendingIntent)
            Toast.makeText(this, "Movie is Booked and Alarm set for $i seconds", Toast.LENGTH_LONG).show()
        }



    }
}