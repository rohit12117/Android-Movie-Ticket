package com.example.moviebooking2

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moviebooking2.Movie1
import com.example.moviebooking2.Movie2
import com.example.moviebooking2.Movie3
import com.example.moviebooking2.Movie4

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val btnlala = findViewById<ImageButton>(R.id.btnlala)
        val btnJoker = findViewById<ImageButton>(R.id.btnJoker)
        val btnBlade = findViewById<ImageButton>(R.id.btnBlade)
        val btnlunch = findViewById<ImageButton>(R.id.btnlunch)

        btnlala.setOnClickListener {
            Intent(this, Movie1::class.java).apply{
                startActivity(this)
            }
        }

        btnlunch.setOnClickListener {
            Intent(this, Movie2::class.java).apply{
                startActivity(this)
            }
        }

        btnJoker.setOnClickListener {
            Intent(this, Movie3::class.java).apply{
                startActivity(this)
            }
        }

        btnBlade.setOnClickListener {
            Intent(this, Movie4::class.java).apply {
                startActivity(this)
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.bookedTickets -> {
                Intent(this, BookingActivity::class.java).apply{
                    startActivity(this)
                }
            }
            R.id.navigation->{
                Intent(this, MapActivityEx::class.java).apply {
                    startActivity(this)
                }

            }
            R.id.finish -> {

                finish()
            }
        }
        return true
    }
}
