package com.filimonov.composition.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult (
    val winner: Boolean,
    val countOfRightAnswers: Int,
    val countOfQuestions: Int,
    val percentOfRightAnswers: Int,
    val gameSettings: GameSettings
) : Parcelable