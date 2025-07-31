package com.example.parkingslot.modules.loginFlow.login

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.parkingslot.modules.loginFlow.ValidateViewModel
import com.example.parkingslot.utils.EncryptionUtils
import com.example.parkingslot.utils.LoggerUtils
import com.example.parkingslot.utils.NetworkUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/*Login page specific viewmodel */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val networkUtils: NetworkUtils,
    private val loggerUtils: LoggerUtils,
    private val encryptionUtils: EncryptionUtils,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    internal val emailStateFlow = MutableStateFlow<String>("")
    internal val passwordStateFlow = MutableStateFlow<String>("")
    internal val emailErrorStateFlow = MutableStateFlow<String>("")
    internal val passwordErrorStateFlow = MutableStateFlow<String>("")

    private var encryptKey = ""

    init {/*Since for very first time remote config not receiving */
        encryptKey = encryptionUtils.provideEncryptKey()
    }

    internal fun validateLogin(
        navController: NavHostController, validateViewModel: ValidateViewModel
    ) {

        emailErrorStateFlow.value = validateViewModel.validate(
            emailStateFlow.value, validateViewModel.validateEmail, "Invalid Email ID"
        )

        if (emailErrorStateFlow.value.isEmpty()) {
            passwordErrorStateFlow.value = validateViewModel.validate(
                passwordStateFlow.value, validateViewModel.validatePwd, "Invalid password"
            )
            if (passwordErrorStateFlow.value.isEmpty()) {
                loginFlow(navController)
            }
        }
    }

    /* check user available in firestore or not and move to nxt screen */
    private fun loginFlow(navController: NavHostController) {
        if (networkUtils.isNetworkAvailable()) {
            if (encryptKey.isEmpty()) {
                encryptKey = encryptionUtils.provideEncryptKey()
            }
            if (encryptKey.isNotEmpty()) {
                // now we are encrypting pswd and then check with firestore
                val encryptPassword = encryptionUtils.encrypt(passwordStateFlow.value, encryptKey)
                loggerUtils.info("Login", encryptPassword)
                loggerUtils.info("Login",encryptionUtils.decrypt(encryptPassword, encryptKey))

                firebaseAuth.signInWithEmailAndPassword(emailStateFlow.value, encryptPassword)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            loggerUtils.info("Login", "Login successful")
                            // move to nxt module
                        } else {
                            loggerUtils.error("Login", it.exception?.message.toString())
                        }
                    }
            } else {
                // need to show toast or popup dialog
            }
        } else {
            // need to show toast or popup dialog
        }
    }

    internal fun moveToRegistration(navController: NavHostController) {
        navController.navigate("registration") {
            popUpTo("login") {
                inclusive = true
            }
        }
    }
}