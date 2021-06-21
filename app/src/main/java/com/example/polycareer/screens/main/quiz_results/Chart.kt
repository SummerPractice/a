package com.example.polycareer.screens.main.quiz_results

import com.example.polycareer.domain.model.Profession

interface Chart {
    fun render(professions: List<Profession>)
}