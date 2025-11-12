package com.example.gratitudeplusplus

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TimestampDao {
    @Upsert
    suspend fun upsertTimestamp(timestamp: Timestamp)

    @Delete
    suspend fun deleteTimestamp(timestamp: Timestamp)

    @Query("SELECT * FROM timestamp ORDER BY day DESC")
    fun getTimestampsOrderedByTime(): Flow<List<Timestamp>>

    @Query("SELECT * FROM timestamp ORDER BY type ASC")
    fun getTimestampsOrderedByType(): Flow<List<Timestamp>>

    @Query("SELECT COUNT(*) FROM timestamp")
    fun getTimestampCount(): Flow<Int>
}