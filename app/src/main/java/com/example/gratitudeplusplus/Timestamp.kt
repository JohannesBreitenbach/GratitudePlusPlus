package com.example.gratitudeplusplus

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Timestamp(
    val type: TimestampType,
    val time: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

enum class TimestampType {
    OPENED_APP,
    ADDED_GRATITUDE,
    INIT
}