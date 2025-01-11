package com.shreyashkore.raw_football.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.shreyashkore.raw_football.data.models.GameStatus
import com.shreyashkore.raw_football.data.models.MatchSchedule
import com.shreyashkore.raw_football.data.models.TeamDetails
import com.shreyashkore.raw_football.data.models.status
import com.shreyashkore.raw_football.format
import com.shreyashkore.raw_football.toLocal
import java.time.Instant
import java.time.Month

@Composable
fun ScheduleScreen(
    viewModel: SchedulesViewModel
) {
    ScheduleScreen(
        appTeam = "1610612748",
        isLoading = viewModel.isLoading.collectAsState(false).value,
//        schedules = viewModel.scheduleDetails.collectAsState(emptyList()).value,
        schedulesByMonth = viewModel.scheduleDetailsByMonth.collectAsState(emptyMap()).value
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ScheduleScreen(
    appTeam: String,
    isLoading: Boolean,
//    schedules: List<MatchScheduleDetails>,
    schedulesByMonth: Map<Pair<Month, Int>, List<MatchScheduleDetails>>
) {
    val listState = rememberLazyListState()

    // Autoscroll to current month
    LaunchedEffect(schedulesByMonth) {
        val today = Instant.now().toLocal()
        var todaysGameIndex = 0
        for ((monthYear, schedules) in schedulesByMonth) {
            todaysGameIndex++ // adding the sticky header index
            val (month, year) = monthYear
            if (today.year == year && today.month == month) {
                // found the current month
                break
            } else {
                todaysGameIndex += schedules.size // adding all items index
            }
        }

        listState.scrollToItem(todaysGameIndex)
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = { Text(text = "Games") })
    }) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(innerPadding)
        ) {

            if (isLoading) {
                CircularProgressIndicator()
                Text(text = "Loading...")
                return@Column
            }

            LazyColumn(
                modifier = Modifier.padding(16.dp),
                state = listState
            ) {
                schedulesByMonth.entries.map {
                    val (month, year) = it.key
                    val items = it.value

                    stickyHeader {
                        Text(
                            text = "$month $year",
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                ),
                            textAlign = TextAlign.Center,
                        )
                    }
                    items(items) {
                        ScheduleCard(
                            appTeam = appTeam,
                            schedule = it,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }

            }
        }
    }
}

data class MatchScheduleDetails(
    val schedule: MatchSchedule, val homeTeam: TeamDetails, val visitingTeam: TeamDetails
)

@Composable
fun ScheduleCard(
    appTeam: String,
    schedule: MatchScheduleDetails,
    modifier: Modifier = Modifier
) {
    val gameTime = Instant.parse(schedule.schedule.gametime).toLocal()
    Card(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(vertical = 6.dp)
            ) {
                Text(
                    text = schedule.schedule.arenaName,
                )
                VerticalDivider()

                if (schedule.schedule.status != GameStatus.Live) {
                    Text(gameTime.format("EEE MMM dd"))
                    VerticalDivider()
                }
                if (schedule.schedule.status == GameStatus.Future) {
                    Text(gameTime.format("HH:mm"))
                }
            }
            if (schedule.schedule.status == GameStatus.Live) {
                Text(
                    text = "LIVE",
                    Modifier
                        .background(MaterialTheme.colorScheme.onSurface.copy(.4f))
                        .padding(6.dp)
                        .clip(RoundedCornerShape(2.dp))
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(6.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = schedule.visitingTeam.logo,
                        contentDescription = null,
                        Modifier.size(52.dp),
                    )
                    if (schedule.schedule.status != GameStatus.Future) {
                        Text(
                            text = schedule.schedule.visitingTeam.ta,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                }
                if (schedule.schedule.status != GameStatus.Future) {
                    Text(
                        "${schedule.schedule.visitingTeam.score}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Text(
                        text = schedule.visitingTeam.ta,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }


                Text(
                    if (appTeam == schedule.homeTeam.tid) "VS"
                    else "@",
                    style = MaterialTheme.typography.titleMedium
                )


                if (schedule.schedule.status != GameStatus.Future) {
                    Text(
                        "${schedule.schedule.homeTeam.score}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Text(
                        text = schedule.homeTeam.ta,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = schedule.homeTeam.logo,
                        contentDescription = null,
                        Modifier.size(52.dp),
                    )
                    if (schedule.schedule.status != GameStatus.Future) {
                        Text(
                            text = schedule.homeTeam.ta,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}

