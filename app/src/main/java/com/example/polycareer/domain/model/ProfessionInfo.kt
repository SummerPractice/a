package com.example.polycareer.domain.model

import com.google.gson.annotations.SerializedName

data class ProfessionInfo(
    val id: Long,
    @SerializedName("value")
    val name: String
)
