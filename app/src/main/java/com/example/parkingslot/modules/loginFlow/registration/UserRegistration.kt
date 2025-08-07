package com.example.parkingslot.modules.loginFlow.registration

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.HomeRepairService
import androidx.compose.material.icons.filled.HomeWork
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.parkingslot.modules.loginFlow.ValidateViewModel
import com.example.parkingslot.ui.component.appBackground
import com.example.parkingslot.ui.component.DefaultEditTextField
import com.example.parkingslot.ui.component.DropdownBox
import com.example.parkingslot.ui.component.PasswordEditTextField

@Composable
internal fun UserRegistration(
    navController: NavHostController,
    validateViewModel: ValidateViewModel,
    innerPadding: PaddingValues
) {

    val registrationViewmodel: RegistrationViewmodel = hiltViewModel()
    val email by registrationViewmodel.emailStateFlow.collectAsState()
    val block by registrationViewmodel.blockStateFlow.collectAsState()
    val flat by registrationViewmodel.flatStateFlow.collectAsState()
    val phone by registrationViewmodel.phoneStateFlow.collectAsState()
    val password by registrationViewmodel.passwordStateFlow.collectAsState()
    val conformPassword by registrationViewmodel.conformPasswordStateFlow.collectAsState()

    val emailError by registrationViewmodel.emailErrorStateFlow.collectAsState()
    val blockError by registrationViewmodel.blockErrorStateFlow.collectAsState()
    val flatError by registrationViewmodel.flatErrorStateFlow.collectAsState()
    val phoneError by registrationViewmodel.phoneErrorStateFlow.collectAsState()
    val passwordError by registrationViewmodel.passwordErrorStateFlow.collectAsState()
    val conformPasswordError by registrationViewmodel.conformErrorPasswordStateFlow.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        registrationViewmodel.toastSharedFlow.collect { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(appBackground())
    ) {

        Card(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxWidth()
        ) {

            LazyColumn(
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                item {
                    DefaultEditTextField(
                        "Email",
                        email,
                        onTextChange = { it -> registrationViewmodel.emailStateFlow.value = it },
                        KeyboardType.Text,
                        trailIcon = Icons.Filled.Email,
                        isError = emailError.isNotEmpty(),
                        errorMsg = emailError

                    )
                }
                item {
                    DefaultEditTextField(
                        "Block",
                        block,
                        onTextChange = { it -> registrationViewmodel.blockStateFlow.value = it },
                        KeyboardType.Text,
                        trailIcon = Icons.Filled.HomeRepairService,
                        isError = blockError.isNotEmpty(),
                        errorMsg = blockError
                    )
                }
                item {
                    DefaultEditTextField(
                        "Flat No",
                        flat,
                        onTextChange = { it -> registrationViewmodel.flatStateFlow.value = it },
                        KeyboardType.Number,
                        trailIcon = Icons.Filled.HomeWork,
                        isError = flatError.isNotEmpty(),
                        errorMsg = flatError
                    )
                }
                item {
                    DefaultEditTextField(
                        "Phone No",
                        phone,
                        onTextChange = { it ->
                            if (it.length <= 10) {
                                registrationViewmodel.phoneStateFlow.value =
                                    it.filter { char -> char.isDigit() }
                            }
                        },
                        KeyboardType.Phone,
                        trailIcon = Icons.Filled.Phone,
                        isError = phoneError.isNotEmpty(),
                        errorMsg = phoneError
                    )
                }
                item {
                    PasswordEditTextField(
                        "Password",
                        password,
                        onTextChange = { it -> registrationViewmodel.passwordStateFlow.value = it },
                        KeyboardType.Password,
                        isError = passwordError.isNotEmpty(),
                        errorMsg = passwordError
                    )
                }
                item {
                    PasswordEditTextField(
                        "Conform Password",
                        conformPassword,
                        onTextChange = { it ->
                            registrationViewmodel.conformPasswordStateFlow.value = it
                        },
                        KeyboardType.Password,
                        isError = conformPasswordError.isNotEmpty(),
                        errorMsg = conformPasswordError
                    )
                }
                item {
                    DropdownBox()
                }
                item {
                    Button(
                        onClick = {
                            registrationViewmodel.validateRegistration(validateViewModel,navController)
                        }, modifier = Modifier
                            .padding(top = 15.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = "Register", modifier = Modifier.padding(5.dp))
                    }
                }
                item {

                    Text(
                        text = "Already having account ?",
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(top = 10.dp)
                    )

                }
                item {
                    Button(
                        onClick = {
                            navController.navigate("login") {
                                popUpTo("register") {
                                    inclusive = true
                                }
                            }
                        }, modifier = Modifier
                            .padding(bottom = 15.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = "Login", modifier = Modifier.padding(5.dp))
                    }
                }

            }

        }

    }


}


