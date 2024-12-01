package com.example.moviebooking2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MovieDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "movie_booking.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "movie_bookings"
        private const val COLUMN_ID = "id"
        private const val COLUMN_MOVIE_NAME = "movie_name"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_TIME = "time"
        private const val COLUMN_TICKETS = "tickets"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_MOVIE_NAME TEXT NOT NULL,
                $COLUMN_DATE TEXT NOT NULL,
                $COLUMN_TIME TEXT NOT NULL,
                $COLUMN_TICKETS INTEGER NOT NULL
            )
        """
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertBooking(movieName: String, date: String, time: String, tickets: Int): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_MOVIE_NAME, movieName)
            put(COLUMN_DATE, date)
            put(COLUMN_TIME, time)
            put(COLUMN_TICKETS, tickets)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun getAllBookings(): List<MovieBooking> {
        val bookings = mutableListOf<MovieBooking>()
        val db = readableDatabase
        val cursor: Cursor = db.query(TABLE_NAME, null, null, null, null, null, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val movieName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_NAME))
            val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
            val time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME))
            val tickets = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TICKETS))
            bookings.add(MovieBooking(id, movieName, date, time, tickets))
        }
        cursor.close()
        return bookings
    }

    fun deleteBooking(id: Int): Int {
        val db = writableDatabase
        return db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }
}

