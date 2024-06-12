package com.example.numbersapplication.ui

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.numbersapplication.R
import java.util.Locale

@Composable
fun MainScreen() {
    var isPlaying by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val resources = context.resources

    var currentLocale by remember { mutableStateOf(resources.configuration.locales[0]) }

    val changeLanguage: (Locale) -> Unit = { newLocale ->
        if (newLocale != currentLocale) {
            val configuration = resources.configuration
            configuration.setLocale(newLocale)
            resources.updateConfiguration(configuration, resources.displayMetrics)
            currentLocale = newLocale
        }
    }

    val shareMessage = stringResource(R.string.share_game_success_message)

    fun shareContent() {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareMessage)
            type = "text/plain"
        }
        context.startActivity(Intent.createChooser(shareIntent, null))
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NumbersTopAppBar(
            title = stringResource(R.string.title),
            onShareClick = { shareContent() }
        )

        if (isPlaying) {
            Game(
                onGameEnd = { finalScore -> },
                currentLocale = currentLocale,
                onChangeLanguageClick = changeLanguage
            )
        } else {
            PlayScreen(
                onStartGame = { isPlaying = true },
                onShareClick = { shareContent() },
                onChangeLanguageClick = changeLanguage,
                currentLocale = currentLocale
            )
        }
    }
}
