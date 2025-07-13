package com.filimonov.composition.domain.usecases

import com.filimonov.composition.domain.entity.Question
import com.filimonov.composition.domain.repository.GameRepository

class GenerateQuestionUseCase(private val repository: GameRepository) {

    operator fun invoke(maxOfSumValue: Int): Question{
        return repository.generateQuestion(maxOfSumValue, COUNT_OPTIONS)
    }

    private companion object {

        private const val COUNT_OPTIONS = 6
    }
}