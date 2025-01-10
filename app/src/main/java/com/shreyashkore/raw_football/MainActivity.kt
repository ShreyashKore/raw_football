package com.shreyashkore.raw_football

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shreyashkore.raw_football.data.FootballRepository
import com.shreyashkore.raw_football.data.FootballRepositoryImpl
import com.shreyashkore.raw_football.ui.ScheduleScreen
import com.shreyashkore.raw_football.ui.SchedulesViewModel
import com.shreyashkore.raw_football.ui.theme.Raw_footballTheme
import kotlinx.serialization.json.Json

private val json by lazy {
    Json { ignoreUnknownKeys = true }
}

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<SchedulesViewModel>() {
        SchedulesViewModel.Factory(FootballRepositoryImpl(this.assets, json))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Raw_footballTheme {
                ScheduleScreen(viewModel)
            }
        }
    }
}
