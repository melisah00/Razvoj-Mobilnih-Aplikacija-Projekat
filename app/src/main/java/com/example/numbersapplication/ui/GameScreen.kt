package com.example.numbersapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.numbersapplication.R
import kotlinx.coroutines.delay
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    onGameEnd: (Int) -> Unit
) {
    val context = LocalContext.current

    var userInput by remember { mutableStateOf("") }
    var savedNumber by remember { mutableStateOf<Int?>(null) }
    var score by remember { mutableStateOf(0) }
    var attempts by remember { mutableStateOf(0) }
    var end by remember { mutableStateOf(false) }
    var timerFinished by remember { mutableStateOf(false) }
    var timer by remember { mutableStateOf(5) }

    var firstRandomNumber by remember { mutableStateOf(Random.nextInt(0, 10)) }
    var randomSign by remember { mutableStateOf(if (Random.nextBoolean()) "+" else "-") }
    var secondRandomNumber by remember {
        mutableStateOf(
            if (randomSign == "-") {
                Random.nextInt(0, firstRandomNumber + 1)
            } else {
                Random.nextInt(0, 10)
            }
        )
    }
    var result by remember {
        mutableStateOf(
            if (randomSign == "+") {
                firstRandomNumber + secondRandomNumber
            } else {
                firstRandomNumber - secondRandomNumber
            }
        )
    }

    var feedbackMessage by remember { mutableStateOf<String?>(null) }

    fun generateNewQuestion() {
        firstRandomNumber = Random.nextInt(0, 10)
        randomSign = if (Random.nextBoolean()) "+" else "-"
        secondRandomNumber = if (randomSign == "-") {
            Random.nextInt(0, firstRandomNumber + 1)
        } else {
            Random.nextInt(0, 10)
        }
        result = if (randomSign == "+") {
            firstRandomNumber + secondRandomNumber
        } else {
            firstRandomNumber - secondRandomNumber
        }
    }


    LaunchedEffect(attempts) {
        timerFinished = false
        timer = 5
        while (timer > 0) {
            delay(1000)
            timer--
        }
        timerFinished = true
        if (userInput.isEmpty()) {
            feedbackMessage = "No answer"
            delay(2000)
            feedbackMessage = null
            if (attempts < 9) {
                attempts++
                generateNewQuestion()
                userInput = ""
            } else {
                end = true
                onGameEnd(score)
            }
        }
    }

    LaunchedEffect(feedbackMessage) {
        if (feedbackMessage != null) {
            delay(2000)
            feedbackMessage = null
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize().background(Color(0xFFE1BEE7)),
        color = Color(0xFFE1BEE7)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .background(Color(0xFFE1BEE7)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = stringResource(R.string.score_label, score),
                    fontSize = 20.sp
                )
                Text(
                    text = stringResource(R.string.time_left_label, timer),
                    fontSize = 20.sp,
                    color = Color(0xFF8000FF)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Round
            Text(
                text = stringResource(R.string.round_label, attempts + 1),
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(14.dp))


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .border(width = 1.dp, color = Color.Black)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = "$firstRandomNumber",
                            fontSize = 40.sp
                        )
                        Text(
                            text = randomSign,
                            fontSize = 40.sp,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        Text(
                            text = "$secondRandomNumber",
                            fontSize = 40.sp
                        )

                        Spacer(modifier = Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // User Input
                    TextField(
                        value = userInput,
                        onValueChange = { userInput = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        placeholder = { Text(stringResource(R.string.enter_num)) },
                        modifier = Modifier
                            .width(200.dp)
                            .padding(2.dp)
                            .heightIn(min = 25.dp, max = 50.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))


                    Button(
                        onClick = {
                            val enteredNumber = userInput.toIntOrNull()
                            if (enteredNumber == result) {
                                score++
                                feedbackMessage = "Right answer"
                            } else if (enteredNumber != null) {
                                feedbackMessage = "Wrong answer"
                            } else {
                                feedbackMessage = "No answer"
                            }
                            savedNumber = enteredNumber

                            if (attempts < 9) {
                                attempts++
                                generateNewQuestion()
                                userInput = ""
                                timerFinished = false
                            } else {
                                end = true
                                onGameEnd(score)
                            }
                        },
                        modifier = Modifier
                            .width(200.dp)
                            .padding(2.dp)
                    ) {
                        Text(stringResource(R.string.save_number))
                    }

                    Spacer(modifier = Modifier.height(14.dp))


                    feedbackMessage?.let {
                        Text(
                            text = it,
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}
