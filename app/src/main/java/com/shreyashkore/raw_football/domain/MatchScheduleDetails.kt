package com.shreyashkore.raw_football.domain

import com.shreyashkore.raw_football.data.models.MatchSchedule
import com.shreyashkore.raw_football.data.models.TeamDetails

/**
 * Contains [MatchSchedule], and home and visiting [TeamDetails]
 */
// TODO: Make structure flatter and avoid dto classes here
data class MatchScheduleDetails(
    val schedule: MatchSchedule, val homeTeam: TeamDetails, val visitingTeam: TeamDetails
)