package com.example.gratitudeplusplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.gratitudeplusplus.ui.theme.GratitudePlusPlusTheme

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            TimestampDatabase::class.java,
            "timestamps.db"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GratitudePlusPlusTheme {
                val dao = remember { db.dao }
                val viewModel: GratitudeViewModel = viewModel(factory = MyViewModelFactory(dao))

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val state by viewModel.state.collectAsState()
                    GratitudeScreen(
                        state,
                        onEvent = viewModel::onEvent,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

class MyViewModelFactory(
    private val dao: TimestampDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GratitudeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GratitudeViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GratitudePlusPlusTheme {
    }
}