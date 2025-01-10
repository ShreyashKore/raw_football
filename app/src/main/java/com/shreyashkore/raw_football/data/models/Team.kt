package com.shreyashkore.raw_football.data.models

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@Keep
data class Team(
    val tid: String,
    @SerialName("re")
    val record: String,
    val ta: String,
    @SerialName("tc")
    val city: String,
    @SerialName("s")
    val score: String? = null,
    val name: String? = null,
)

@Serializable
@Keep
data class TeamDetails(
    val tid: String,
    @SerialName("re")
    val record: String? = null,
    val ta: String,
    @SerialName("tc")
    val city: String,
    val name: String? = null,
    val logo: String,
    val color: String,
)