package com.example.gratitudeplusplus

data class GratitudeState(
    val timestamps: List<Timestamp> = emptyList(),
    val timestampTime: String = "",
    val timestampType: TimestampType = TimestampType.ADDED_GRATITUDE,
    val sortType: SortType = SortType.TIMESTAMP_TIME
)
