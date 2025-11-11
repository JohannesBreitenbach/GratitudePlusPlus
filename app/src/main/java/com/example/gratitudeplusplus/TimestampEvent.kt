package com.example.gratitudeplusplus

sealed interface TimestampEvent {
    object SaveTimestamp : TimestampEvent
    data class SetTimestampTime(val time: String) : TimestampEvent
    data class SetTimestampType(val type: TimestampType): TimestampEvent
    data class SortTimestamps(val sortType: SortType): TimestampEvent
    data class DeleteTimestamp(val timestamp: Timestamp): TimestampEvent
}