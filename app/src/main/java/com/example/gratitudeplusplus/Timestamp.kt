package com.example.gratitudeplusplus

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Timestamp(
    val type: TimestampType,
    val day: Int,
    val month: Int,
    val year: Int,
    val hour: Int,
    val minute: Int,
    val second: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)

enum class TimestampType {
    SPORT, SOCIAL_ACTIVITY, ME_TIME, FOOD, ADDED_GRATITUDE, INIT
}