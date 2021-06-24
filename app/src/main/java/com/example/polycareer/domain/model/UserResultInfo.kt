package com.example.polycareer.domain.model

import com.example.polycareer.data.database.model.ResultInfo
import java.util.*

data class UserResultInfo(
    val id: Long,
    val date: Date
) {
    companion object {
        operator fun invoke(info: ResultInfo): UserResultInfo {
            val time = Date(info.time)
            return UserResultInfo(info.id, time)
        }
    }
}