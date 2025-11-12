package com.example.gratitudeplusplus

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    state: GratitudeState,
    onEvent: (event: TimestampEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End
            ) {
                if (expanded) {
                    ExtendedFloatingActionButton(
                        onClick = {
                            onEvent(TimestampEvent.SetTimestampType(TimestampType.SPORT))
                            onEvent(TimestampEvent.SaveTimestamp)
                            expanded = false
                        },
                        modifier = Modifier.padding(bottom = 8.dp),
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Sport"
                            )
                        },
                        text = { Text("Sport done") }
                    )

                    ExtendedFloatingActionButton(
                        onClick = {
                            onEvent(TimestampEvent.SetTimestampType(TimestampType.SOCIAL_ACTIVITY))
                            onEvent(TimestampEvent.SaveTimestamp)
                            expanded = false
                        },
                        modifier = Modifier.padding(bottom = 8.dp),
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Social Activity"
                            )
                        },
                        text = { Text("Social Activity") }
                    )

                    ExtendedFloatingActionButton(
                        onClick = {
                            onEvent(TimestampEvent.SetTimestampType(TimestampType.ME_TIME))
                            onEvent(TimestampEvent.SaveTimestamp)
                            expanded = false
                        },
                        modifier = Modifier.padding(bottom = 8.dp),
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Me-Time"
                            )
                        },
                        text = { Text("Me-Time") }
                    )

                    ExtendedFloatingActionButton(
                        onClick = {
                            onEvent(TimestampEvent.SetTimestampType(TimestampType.FOOD))
                            onEvent(TimestampEvent.SaveTimestamp)
                            expanded = false
                        },
                        modifier = Modifier.padding(bottom = 8.dp),
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Comfort Food"
                            )
                        },
                        text = { Text("Comfort Food") }
                    )
                }

                // Haupt FAB
                FloatingActionButton(
                    onClick = { expanded = !expanded }
                ) {
                    val rotation by animateFloatAsState(if(expanded) 45f else 0f, label = "fab-rotation")
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = if (expanded) "Menu schließen" else "Menu öffnen",
                        modifier = Modifier.rotate(rotation)
                    )
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                ) {
                    SortType.entries.forEach { sortType ->
                        Row(
                            modifier = Modifier.clickable {
                                onEvent(TimestampEvent.SortTimestamps(sortType))
                            },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = state.sortType == sortType,
                                onClick = {
                                    onEvent(TimestampEvent.SortTimestamps(sortType))
                                }
                            )
                            Text(text = sortType.name)
                        }
                    }
                }
            }
            items(state.timestamps){timestamp ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = timestamp.day.toString(),
                            fontSize = 20.sp
                        )
                        Text(text = timestamp.type.toString(), fontSize = 12.sp)
                    }
                    IconButton(onClick = {
                        onEvent(TimestampEvent.DeleteTimestamp(timestamp))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete contact"
                        )
                    }
                }
            }
        }
    }
}
