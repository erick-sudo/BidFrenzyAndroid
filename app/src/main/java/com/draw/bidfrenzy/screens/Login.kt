package com.draw.bidfrenzy.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.draw.bidfrenzy.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(
    navHostController: NavHostController
) {

    var formData by remember {
        mutableStateOf(mapOf(
            "email" to "",
            "password" to "password"
        ))
    }

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    val handleInputChange: (String, String) -> Unit = { name, value ->
        formData = formData + (name to value)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier
                )
            }
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .padding(start = 10.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            label = {
                Text("Email")
            },
            value = formData["email"] ?: "",
            onValueChange = { handleInputChange("email", it) },
            modifier = Modifier
                .fillMaxWidth(0.8f),
            placeholder = {
                Text("Email")
            },
            trailingIcon = {
                Icon(
                    Icons.Filled.Email,
                    contentDescription = "Email"
                )
            }
        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            label = {
                Text("Password")
            },
            value = when(passwordVisible) {
                false -> formData["password"]?.replace(Regex("."), "*" ) ?: ""
                else -> formData["password"] ?: ""
            },
            onValueChange = { handleInputChange("password", it) },
            modifier = Modifier
                .fillMaxWidth(0.8f),
            placeholder = {
                Text("Password")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            trailingIcon = {
                Icon(
                    modifier = Modifier
                        .clickable {
                            passwordVisible = !passwordVisible
                        },
                    imageVector =  if(passwordVisible) ImageVector.vectorResource(id = R.drawable.outline_visibility_24) else ImageVector.vectorResource(id = R.drawable.outline_visibility_off_24) ,
                    contentDescription = "Password"
                )
            }
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Create account",
            style = TextStyle(
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .clickable {
                    UseNavigate.navigate(navHostController, NavRoutes.Register)
                }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f),
            onClick = {

            }
        ) {
            Text("Submit")
        }

    }
}

fun toSnakeCase(s: String) : String {
    return s.lowercase().split(" ").joinToString("")
}