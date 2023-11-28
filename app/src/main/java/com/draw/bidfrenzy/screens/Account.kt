package com.draw.bidfrenzy.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun Account(
    // navHostController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "My Account",
            style = MaterialTheme.typography.headlineSmall
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            onClick = {
//                UseNavigate.navigate(
//                    navHostController = navHostController,
//                    NavRoutes.Login
//                )
            }
        ) {
            Text(text = "Login")
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            onClick = {
//                UseNavigate.navigate(
//                    navHostController = navHostController,
//                    NavRoutes.Register
//                )
            }
        ) {
            Text(text = "Register")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AccountPreview() {
    Account()
}