package com.example.polycareer.screens.quiz.quiz_results.charts

import com.example.polycareer.domain.model.Profession

interface Chart {
    fun render(professions: List<Profession>)
}