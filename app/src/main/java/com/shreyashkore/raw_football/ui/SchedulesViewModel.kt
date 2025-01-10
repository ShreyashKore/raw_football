package com.shreyashkore.raw_football.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.shreyashkore.raw_football.data.FootballRepository
import com.shreyashkore.raw_football.data.FootballRepositoryImpl
import com.shreyashkore.raw_football.data.models.MatchSchedule
import com.shreyashkore.raw_football.data.models.TeamDetails
import com.shreyashkore.raw_football.format
import com.shreyashkore.raw_football.toLocal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant

class SchedulesViewModel(
    val repository: FootballRepository
) : ViewModel() {
    private var schedules = listOf<MatchSchedule>()
    private var teams = mapOf<String, TeamDetails>()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _scheduleDetails = MutableStateFlow<List<MatchScheduleDetails>>(emptyList())
    val scheduleDetails = _scheduleDetails.asStateFlow()

    val scheduleDetailsByMonth = scheduleDetails.map {
        it.groupBy {
            val localDate = Instant.parse(it.schedule.gametime).toLocal()
            localDate.month  to localDate.year
        }
    }

    init {
        fillScheduleDetails()
    }

    fun fillScheduleDetails() = viewModelScope.launch {
        schedules = repository.getSchedules()
        teams = repository.getTeams().associateBy { it.tid }
        _isLoading.update { true }
        _scheduleDetails.update {
            schedules.map { schedule ->
                val homeTeam = teams[schedule.homeTeam.tid]!!
                val visitingTeam = teams[schedule.visitingTeam.tid]!!
                MatchScheduleDetails(schedule, homeTeam, visitingTeam)
            }
        }
        _isLoading.update { false }
    }


    class Factory(private val repository: FootballRepositoryImpl) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SchedulesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SchedulesViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}