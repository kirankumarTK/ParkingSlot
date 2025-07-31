package com.example.parkingslot.modules.loginFlow.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.parkingslot.R
import com.example.parkingslot.modules.loginFlow.ValidateViewModel
import com.example.parkingslot.ui.component.AppBackground
import com.example.parkingslot.ui.component.DefaultEditTextField
import com.example.parkingslot.ui.component.PasswordEditTextField

@Composable
internal fun Login(navController: NavHostController, shareValidateViewModel: ValidateViewModel) {

    val loginViewModel: LoginViewModel = hiltViewModel()
    val emailID by loginViewModel.emailStateFlow.collectAsState()
    val emailError by loginViewModel.emailErrorStateFlow.collectAsState()
    val password by loginViewModel.passwordStateFlow.collectAsState()
    val passwordError by loginViewModel.passwordErrorStateFlow.collectAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(AppBackground())
            .padding(10.dp)
            .fillMaxSize()
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.applogo),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier.size(150.dp)
            )
            Card(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(30.dp)

                ) {
                    item {
                        Text(text = "Welcome back", style = MaterialTheme.typography.labelMedium)
                    }

                    item {
                        DefaultEditTextField(
                            "EmailId",
                            text = emailID,
                            onTextChange = { it ->
                                loginViewModel.emailStateFlow.value = it
                            },
                            KeyboardType.Text,
                            isError = emailError.isNotEmpty(),
                            errorMsg = emailError,
                            trailIcon = Icons.Filled.Email
                        )
                    }
                    item {
                        PasswordEditTextField(
                            "Password",
                            text = password,
                            onTextChange = { it -> loginViewModel.passwordStateFlow.value = it },
                            KeyboardType.Password,
                            imeAction = ImeAction.Done,
                            isError = passwordError.isNotEmpty(),
                            errorMsg = passwordError
                        )
                    }
                    item {
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                        ) {
                            Text(
                                text = "Forget password?",
                                style = MaterialTheme.typography.labelSmall,
                                textAlign = TextAlign.End,
                                modifier = Modifier
                                    .clickable {

                                    }
                                    .padding(8.dp))
                        }
                    }
                    item {
                        Button(
                            onClick = {
                                loginViewModel.validateLogin(navController, shareValidateViewModel)
                            }, modifier = Modifier
                                .padding(top = 15.dp)
                                .fillMaxWidth()
                        ) {
                            Text(text = "Login", modifier = Modifier.padding(10.dp))
                        }
                    }
                    item {
                        Text(
                            text = buildAnnotatedString {
                            withStyle(style = SpanStyle(MaterialTheme.colorScheme.onPrimaryContainer)) {
                                append(
                                    "New user?"
                                )
                            }
                            withStyle(style = SpanStyle(MaterialTheme.colorScheme.tertiary)) {
                                append(
                                    " Sign Up"
                                )
                            }
                        },
                            style = MaterialTheme.typography.labelSmall,
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .clickable {
                                    loginViewModel.moveToRegistration(navController)
                                }
                                .padding(8.dp))
                    }
                }
            }
        }

    }
}

