package com.example.numbersapplication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.numbersapplication.R
import java.util.Locale

@Composable
fun PlayScreen(
    onStartGame: () -> Unit,
    onShareClick: () -> Unit,
    onChangeLanguageClick: (Locale) -> Unit,
    currentLocale: Locale
) {
    val context = LocalContext.current
    val resources = context.resources

    fun changeLanguage(newLocale: Locale) {
        if (newLocale != currentLocale) {
            val configuration = resources.configuration
            configuration.setLocale(newLocale)
            resources.updateConfiguration(configuration, resources.displayMetrics)
            onChangeLanguageClick(newLocale)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize().background(Color(0xFFE1BEE7)),
        color = Color(0xFFE1BEE7)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onStartGame,

                modifier = Modifier
                    .widthIn(max = 200.dp)
                    .padding(horizontal = 16.dp)

            ) {
                Text(
                    text = stringResource(id = R.string.play, currentLocale),
                    fontSize = 24.sp,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.flag_bosnia),
                    contentDescription = "Bosnian Flag",
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            changeLanguage(Locale("bs"))
                        }
                )
                Spacer(modifier = Modifier.width(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.uk_flag),
                    contentDescription = "English Flag",
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            changeLanguage(Locale.ENGLISH)
                        }
                )
            }
        }
    }
}



