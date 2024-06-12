package com.example.numbersapplication.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import java.util.Locale

@Composable
fun Game(
    onGameEnd: (Int) -> Unit,
    currentLocale: Locale,
    onChangeLanguageClick: (Locale) -> Unit
) {
    var showFinalScore by remember { mutableStateOf(false) }
    var score by remember{ mutableStateOf(0) }

    fun onGameEnd(finalScore: Int) {
        score = finalScore
        showFinalScore = true
    }

    fun onPlayAgain() {
        score = 0
        showFinalScore = false
    }

    if (showFinalScore) {
        FinalScoreScreen(
            score = score,
            onPlayAgain = ::onPlayAgain
        )
    } else {
        GameScreen(
            onGameEnd = ::onGameEnd
        )
    }
}
