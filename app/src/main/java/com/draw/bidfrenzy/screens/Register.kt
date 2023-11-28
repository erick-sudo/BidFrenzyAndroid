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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.draw.bidfrenzy.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Register(
    navHostController: NavHostController
) {

    val registrationFields = listOf(
        "First Name" to "text",
        "Last Name" to "text",
        "Email" to "email",
        "Age" to "number",
        "Address Line" to "text",
        "Password" to "password",
        "Confirm Password" to "password"
    )

    var formData by remember {
        mutableStateOf(registrationFields.associate { (k, _) ->
            toSnakeCase(k) to ""
        })
    }

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    val handleInputChange: (String, String) -> Unit = { name, value ->
        formData = formData + (name to value)
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxSize()
            .verticalScroll(scrollState),
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
                text = "Registration",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .padding(start = 10.dp)
            )
        }

        registrationFields.forEach { (inputName, inputType) ->
            when(inputType) {
                "password" -> OutlinedTextField(
                    label = {
                        Text(inputName)
                    },
                    value = when(passwordVisible) {
                        false -> formData[toSnakeCase(inputName)]?.replace(Regex("."), "*" ) ?: ""
                        else -> formData[toSnakeCase(inputName)] ?: ""
                    },
                    onValueChange = { handleInputChange(toSnakeCase(inputName), it) },
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
                    placeholder = {
                        Text(inputName)
                    },
                    trailingIcon = {
                        Icon(
                            modifier = Modifier
                                .clickable {
                                    passwordVisible = !passwordVisible
                                },
                            imageVector =  if(passwordVisible) ImageVector.vectorResource(id = R.drawable.outline_visibility_24) else ImageVector.vectorResource(id = R.drawable.outline_visibility_off_24) ,
                            contentDescription = inputName
                        )
                    }
                )
                else -> OutlinedTextField(
                    label = {
                        Text(inputName)
                    },
                    value = formData[toSnakeCase(inputName)] ?: "",
                    onValueChange = { handleInputChange(toSnakeCase(inputName), it) },
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
                    placeholder = {
                        Text(inputName)
                    },
                    trailingIcon = {
                        Icon(
                            Icons.Filled.AccountCircle,
                            contentDescription = inputName
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        Text(
            text = "Login",
            style = TextStyle(
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .clickable {
                    UseNavigate.navigate(navHostController, NavRoutes.Login)
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