package com.example.polycareer.domain.model

import com.example.polycareer.data.database.model.CoeffsEntity

data class UserAnswer(
    val professionNumber: Long,
    val score: Map<String, Double>
) {
    companion object {
        operator fun invoke(coeffsEntity: CoeffsEntity): UserAnswer {
            val score = mapOf(
                "yk" to coeffsEntity.yk,
                "ytc" to coeffsEntity.ytc,
                "inn" to coeffsEntity.inn,
                "ivt" to coeffsEntity.ivt,
                "pi1" to coeffsEntity.pi1,
                "pi2" to coeffsEntity.pi2,
                "cic" to coeffsEntity.cic,
                "ic" to coeffsEntity.ic,
                "fi" to coeffsEntity.fi,
                "mot" to coeffsEntity.mot,
            )

            return UserAnswer(
                professionNumber = coeffsEntity.profession,
                score = score
            )
        }
    }
}