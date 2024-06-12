package com.example.numbersapplication.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.numbersapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumbersTopAppBar(
    title: String,
    onShareClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray)
    ) {
        TopAppBar(
            title = {
                Text(text = title,
                    color = Color(0xFF1A237E)
                    )
            },
            actions = {
                IconButton(
                    onClick = { onShareClick() },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_share_24),
                        contentDescription = "Share",
                        tint = Color(0xFF1A237E)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFD3E0F0),
                titleContentColor = Color.Black,
                actionIconContentColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()

        )
    }
}
