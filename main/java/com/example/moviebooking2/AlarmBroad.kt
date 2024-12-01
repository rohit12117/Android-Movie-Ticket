package com.example.moviebooking2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moviebooking2.R

class AlarmBroad : BroadcastReceiver() {
//    override fun onReceive(context: Context, intent: Intent){
//        val mp = MediaPlayer.create(context, R.raw.softcore)
//        Log.d("Hello", "Repeating alarm")
//        mp.start()
//        Toast.makeText(context, "Alarm Ringing", Toast.LENGTH_LONG).show()
//    }

private var mp: MediaPlayer? = null // Declare MediaPlayer as nullable

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            ACTION_START_ALARM -> startAlarm(context)
            ACTION_STOP_ALARM -> stopAlarm(context)
        }
    }

    private fun startAlarm(context: Context) {
        Log.d("AlarmBroad", "Alarm started")

        if (mp == null) {
            mp = MediaPlayer.create(context, R.raw.softcore) // Replace softcore with your audio file
            mp?.setOnCompletionListener { releaseMediaPlayer() } // Release media player when the sound stops
        }

        mp?.start()
        Toast.makeText(context, "Alarm Ringing", Toast.LENGTH_LONG).show()
    }

    private fun stopAlarm(context: Context) {
        Log.d("AlarmBroad", "Alarm stopped")

        mp?.let {
            if (it.isPlaying) {
                it.stop() // Stop the audio if it is playing
                releaseMediaPlayer() // Release media player resources
                Toast.makeText(context, "Alarm Stopped", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun releaseMediaPlayer() {
        mp?.reset()
        mp?.release()
        mp = null // Set to null after releasing it
    }

    companion object {
        const val ACTION_START_ALARM = "com.example.moviebooking2.action.START_ALARM"
        const val ACTION_STOP_ALARM = "com.example.moviebooking2.action.STOP_ALARM"
    }
}