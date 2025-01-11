package com.shreyashkore.raw_football.data

import com.shreyashkore.raw_football.data.models.MatchSchedule
import com.shreyashkore.raw_football.data.models.TeamDetails

/**
 * Main repository of the app
 */
interface FootballRepository {
    /**
     * Returns list of schedules without team details
     */
    suspend fun getSchedules(): List<MatchSchedule>

    /**
     * Returns list of team details
     */
    suspend fun getTeams(): List<TeamDetails>
}