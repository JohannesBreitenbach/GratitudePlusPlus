package com.example.gratitudeplusplus

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Dining
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GratitudeScreen(
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
                            onEvent(TimestampEvent.SetTimestampType(TimestampType.OTHER))
                            onEvent(TimestampEvent.SaveTimestamp)
                            expanded = false
                        },
                        modifier = Modifier.padding(bottom = 8.dp),
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Apps,
                                contentDescription = "Other"
                            )
                        },
                        text = { Text("Other") }
                    )

                    ExtendedFloatingActionButton(
                        onClick = {
                            onEvent(TimestampEvent.SetTimestampType(TimestampType.SPORT))
                            onEvent(TimestampEvent.SaveTimestamp)
                            expanded = false
                        },
                        modifier = Modifier.padding(bottom = 8.dp),
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.FitnessCenter,
                                contentDescription = "Sport"
                            )
                        },
                        text = { Text("Sport") }
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
                                imageVector = Icons.Default.Person,
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
                                imageVector = Icons.Default.Dining,
                                contentDescription = "Comfort Food"
                            )
                        },
                        text = { Text("Comfort Food") }
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
                                imageVector = Icons.Default.Diversity3,
                                contentDescription = "Social Activity"
                            )
                        },
                        text = { Text("Social Activity") }
                    )
                }

                // Haupt FAB
                FloatingActionButton(
                    onClick = { expanded = !expanded }
                ) {
                    val rotation by animateFloatAsState(
                        if (expanded) 45f else 0f,
                        label = "fab-rotation"
                    )
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                val todayCount = state.timestamps.count { timestamp ->
                    timestamp.day == Calendar.getInstance().get(Calendar.DAY_OF_MONTH) &&
                            timestamp.month == Calendar.getInstance().get(Calendar.MONTH) + 1 &&
                            timestamp.year == Calendar.getInstance().get(Calendar.YEAR)
                }
                Text(todayCount.toString(), fontSize = 120.sp)
            }
            item {
                Text(
                    state.timestamps.size.toString(), fontSize = 50.sp, color = Color.LightGray
                )
            }
        }
    }
}

@Preview
@Composable
fun GratitudeScreenPreview() {
    GratitudeScreen(GratitudeState(), {})
}