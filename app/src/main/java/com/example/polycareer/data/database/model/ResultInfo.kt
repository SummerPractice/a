package com.example.polycareer.data.database.model

import androidx.room.ColumnInfo

data class ResultInfo(
    @ColumnInfo(name = "try_number") val id: Long,
    @ColumnInfo(name = "time") val time: Long,
)
