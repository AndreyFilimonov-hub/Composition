package com.filimonov.composition.domain.repository

import com.filimonov.composition.domain.entity.GameSettings
import com.filimonov.composition.domain.entity.Level
import com.filimonov.composition.domain.entity.Question

interface GameRepository {

    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question

    fun getGameSettings(level: Level): GameSettings
}