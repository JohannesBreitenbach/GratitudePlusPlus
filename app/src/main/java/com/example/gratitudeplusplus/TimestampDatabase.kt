package com.example.gratitudeplusplus

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Timestamp::class],
    version = 1
)
abstract class TimestampDatabase: RoomDatabase() {
    abstract val dao: TimestampDao

}