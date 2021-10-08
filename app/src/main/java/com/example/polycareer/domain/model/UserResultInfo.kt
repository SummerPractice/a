package com.example.polycareer.domain.model

import com.example.polycareer.data.database.model.ResultInfo
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.fixedRateTimer
import kotlin.math.ceil

data class UserResultInfo(
    val tryNumber: Long,
    private val _date: Date
) {
    val date: String
        get() = parseDate()

    companion object {
        operator fun invoke(info: ResultInfo): UserResultInfo {
            val time = Date(info.time)
            return UserResultInfo(info.id, time)
        }

        private const val days = 86_400_000
        private val timeFormat = SimpleDateFormat("HH:mm", Locale("ru"))
        private val monthFormat = SimpleDateFormat("dd MMMM HH:mm", Locale("ru"))
        private val yearFormat = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale("ru"))
    }

    private fun parseDate(): String {
        val interval = intervalBetweenMidnight(_date)

        return when {
            interval == 0L -> "Сегодня ${timeFormat.format(_date)}"
            interval == 1L -> "Вчера ${timeFormat.format(_date)}"
            isInCurrentYear(_date) -> monthFormat.format(_date)
            else -> yearFormat.format(_date)
        }
    }

    private fun isInCurrentYear(date: Date): Boolean {
        val currentDate = Date()
        val format = SimpleDateFormat("dd.MM.yyyy", Locale("ru"))
        val yearString = format.format(currentDate)
        val year = format.parse(yearString)!!

        return date.after(year)
    }

    private fun intervalBetweenMidnight(date: Date): Long {
        val midnight = getMidnight()
        val interval = midnight.time - date.time
        return ceil(interval.toDouble() / days).toLong()
    }

    private fun getMidnight(): Date {
        val date = Date()
        val format = SimpleDateFormat("dd.MM.yyyy", Locale("ru"))
        val dateFormat = format.format(date)
        return format.parse(dateFormat)!!
    }
}