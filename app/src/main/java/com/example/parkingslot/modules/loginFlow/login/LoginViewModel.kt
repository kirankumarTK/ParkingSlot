package com.example.parkingslot.modules.loginFlow.login

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.parkingslot.AppModule.DOCUMENT_ID
import com.example.parkingslot.modules.loginFlow.ValidateViewModel
import com.example.parkingslot.utils.EncryptionUtils
import com.example.parkingslot.utils.LoggerUtils
import com.example.parkingslot.utils.NetworkUtils
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/*Login page specific viewmodel */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val networkUtils: NetworkUtils,
    private val loggerUtils: LoggerUtils,
    private val encryptionUtils: EncryptionUtils,
    private val firebaseAuth: FirebaseAuth,
    private val appPreference: SharedPreferences
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
                loginFlow(navController, validateViewModel)
            }
        }
    }

    /* check user available in firestore or not and move to nxt screen */
    private fun loginFlow(navController: NavHostController, validateViewModel: ValidateViewModel) {
        if (networkUtils.isNetworkAvailable()) {

            validateViewModel.isLoadingStateFlow.value = true

            if (encryptKey.isEmpty()) encryptKey = encryptionUtils.provideEncryptKey()

            firebaseAuth.signInWithEmailAndPassword(emailStateFlow.value, passwordStateFlow.value)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        loggerUtils.info(msg = "Login successful")
                        //saving uid from firebase auth to access firestore
                        if (encryptKey.isNotEmpty() && firebaseAuth.currentUser != null && firebaseAuth.currentUser?.uid?.isNotEmpty() == true) {
                            appPreference.edit {
                                putString(
                                    DOCUMENT_ID, firebaseAuth.currentUser?.uid
                                )
                            }
                            validateViewModel.isLoadingStateFlow.value = false
                            // move to nxt module
                            validateViewModel.moveToDashboardLiveData.value = true
                        } else {
                            validateViewModel.isLoadingStateFlow.value = false
                            loggerUtils.error(
                                "Login", "Something went wrong! Encryption key missing"
                            )
                        }

                    } else {
                        validateViewModel.isLoadingStateFlow.value = false
                        loggerUtils.error("Login", it.exception?.message.toString())
                    }
                }

        } else {
            validateViewModel.isLoadingStateFlow.value = false
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

    fun checkUserAlreadyLoggedIn(validateViewModel: ValidateViewModel) {
        appPreference.getString(DOCUMENT_ID, "").let {
            var documentID = it
            if (documentID != null && documentID.isNotEmpty()) {
                // move to nxt module
                validateViewModel.moveToDashboardLiveData.value = true
            }
        }
    }
}