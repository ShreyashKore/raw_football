package com.shreyashkore.raw_football.data

import com.shreyashkore.raw_football.data.models.MatchSchedule
import com.shreyashkore.raw_football.data.models.TeamDetails

interface FootballRepository {
    suspend fun getSchedules(): List<MatchSchedule>

    suspend fun getTeams(): List<TeamDetails>
}