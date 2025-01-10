package com.shreyashkore.raw_football.data.models

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class MatchSchedule(
    val uid: String,
    val year: Int,
    val st: Int,
    @SerialName("h")
    val homeTeam: Team,
    @SerialName("v")
    val visitingTeam: Team,
    @SerialName("arena_name")
    val arenaName: String,
    @SerialName("arena_city")
    val arenaCity: String,
    val gametime: String,
)

val MatchSchedule.status: GameStatus
    get() {
        return when (st) {
            1 -> GameStatus.Future
            2 -> GameStatus.Live
            else -> GameStatus.Past
        }
    }

enum class GameStatus {
    Future, Live, Past
}