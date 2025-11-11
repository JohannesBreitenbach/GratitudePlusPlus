package com.example.gratitudeplusplus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class GratitudeViewModel(
    private val dao: TimestampDao
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortType.TIMESTAMP_TIME)
    private val _state = MutableStateFlow(GratitudeState())

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _timestamps = _sortType
        .flatMapLatest { sortType ->
            when(sortType){
                SortType.TIMESTAMP_TYPE -> dao.getTimestampsOrderedByType()
                SortType.TIMESTAMP_TIME -> dao.getTimestampsOrderedByTime()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val state = combine(_state, _sortType, _timestamps){ state, sortType, timestamps ->
        state.copy(
            timestamps = timestamps,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), GratitudeState())

    fun onEvent(event: TimestampEvent) {
        when (event) {
            is TimestampEvent.DeleteTimestamp -> {
                viewModelScope.launch {
                    dao.deleteTimestamp(event.timestamp)
                }
            }

            TimestampEvent.SaveTimestamp -> {
                val time = state.value.timestampTime
                val type = state.value.timestampType
                /*if(time.isBlank() || type == TimestampType.INIT) {
                    return
                }*/
                val timestamp = Timestamp(
                    time = getTimeString(),
                    type = type
                )
                viewModelScope.launch{
                    dao.upsertTimestamp(timestamp)
                }
                _state.update {
                    it.copy(
                        timestampTime = "",
                        timestampType = TimestampType.INIT
                    )
                }
            }
            is TimestampEvent.SetTimestampTime -> {
                _state.update {
                    it.copy(
                        timestampTime = event.time
                    )
                }
            }
            is TimestampEvent.SetTimestampType -> {
                _state.update {
                    it.copy(
                        timestampType = event.type
                    )
                }
            }
            is TimestampEvent.SortTimestamps -> {
                _sortType.value = event.sortType
            }
        }
    }


    fun addTimeStamp(type: TimestampType) {
        viewModelScope.launch {
            dao.upsertTimestamp(
                Timestamp(
                    id = 0,
                    type = type,
                    time = getTimeString()
                )
            )
        }
    }

    fun getTimeString(): String {
        // simple date format
        val sdf =
            SimpleDateFormat("'Date: 'dd-MM-yyyy '\nTime: 'HH:mm:ss z", Locale.getDefault())

        // current date and time and calling a simple date format
        return sdf.format(Date())
    }
}