package com.shreyashkore.raw_football.data.models

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class FileContent<T>(val data: T)
