package com.filimonov.composition.domain.usecases

import com.filimonov.composition.domain.entity.GameSettings
import com.filimonov.composition.domain.entity.Level
import com.filimonov.composition.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {

    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}