package com.example.polycareer.domain.model

import com.example.polycareer.data.database.model.CoeffsEntity

data class UserAnswer(
    val professionNumber: Long,
    val score: Map<Long, Double>
) {
    companion object {
        operator fun invoke(coeffsEntity: CoeffsEntity): UserAnswer {
            val score = mapOf(
                1L to coeffsEntity.fi,
                2L to coeffsEntity.mot,
                3L to coeffsEntity.ivt,
                4L to coeffsEntity.ic,
                5L to coeffsEntity.pi1,
                6L to coeffsEntity.pi2,
                7L to coeffsEntity.yk,
                8L to coeffsEntity.cic,
                9L to coeffsEntity.ytc,
                10L to coeffsEntity.inn,
            )

            return UserAnswer(
                professionNumber = coeffsEntity.profession,
                score = score
            )
        }
    }
}