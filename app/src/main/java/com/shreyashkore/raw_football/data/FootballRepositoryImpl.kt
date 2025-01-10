package com.shreyashkore.raw_football.data

import android.content.res.AssetManager
import com.shreyashkore.raw_football.data.models.FileContent
import com.shreyashkore.raw_football.data.models.MatchSchedule
import com.shreyashkore.raw_football.data.models.MatchSchedulesData
import com.shreyashkore.raw_football.data.models.TeamDetails
import com.shreyashkore.raw_football.data.models.TeamsData
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

class FootballRepositoryImpl(
    private val assetManager: AssetManager,
    private val json: Json,
) : FootballRepository {


    override suspend fun getSchedules(): List<MatchSchedule> {
        val fileContent = assetManager.open("Schedule.json").use {
            json.decodeFromStream<FileContent<MatchSchedulesData>>(it)
        }
        return fileContent.data.schedules
    }

    override suspend fun getTeams(): List<TeamDetails> {
        val fileContent = assetManager.open("teams.json").use {
            json.decodeFromStream<FileContent<TeamsData>>(it)
        }

        return fileContent.data.teams
    }

}