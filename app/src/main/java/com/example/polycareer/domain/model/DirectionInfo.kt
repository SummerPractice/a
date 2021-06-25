package com.example.polycareer.domain.model

import com.google.gson.annotations.SerializedName

data class DirectionInfo(
    val id: Long,
    @SerializedName("title")
    val name: String,
    val description: String,
    @SerializedName("link")
    val url: String
)