package com.example.parkingslot.modules.loginFlow.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.parkingslot.modules.loginFlow.ValidateViewModel
import com.example.parkingslot.utils.EncryptionUtils
import com.example.parkingslot.utils.LoggerUtils
import com.example.parkingslot.utils.NetworkUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewmodel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val networkUtils: NetworkUtils,
    private val firestore: FirebaseFirestore,
    private val loggerUtils: LoggerUtils
) : ViewModel() {
    //shared flow used for one time event
    internal val toastSharedFlow = MutableSharedFlow<String>()

    internal val emailStateFlow = MutableStateFlow("")
    internal val blockStateFlow = MutableStateFlow("")
    internal val flatStateFlow = MutableStateFlow("")
    internal val phoneStateFlow = MutableStateFlow("")
    internal val passwordStateFlow = MutableStateFlow("")
    internal val conformPasswordStateFlow = MutableStateFlow("")

    internal val emailErrorStateFlow = MutableStateFlow("")
    internal val blockErrorStateFlow = MutableStateFlow("")
    internal val flatErrorStateFlow = MutableStateFlow("")
    internal val phoneErrorStateFlow = MutableStateFlow("")
    internal val passwordErrorStateFlow = MutableStateFlow("")
    internal val conformErrorPasswordStateFlow = MutableStateFlow("")

    /*Registration validation will held here */
    internal fun validateRegistration(
        validateViewModel: ValidateViewModel, navController: NavHostController
    ) {
        emailErrorStateFlow.value = validateViewModel.validate(
            emailStateFlow.value, validateViewModel.validateEmail, "Invalid EmailId"
        )
        blockErrorStateFlow.value = validateViewModel.validate(
            blockStateFlow.value, validateViewModel.validateBlock, "Invalid Block"
        )
        flatErrorStateFlow.value = validateViewModel.validate(
            flatStateFlow.value, validateViewModel.validateBlock, "Invalid Flat"
        )
        phoneErrorStateFlow.value = validateViewModel.validate(
            phoneStateFlow.value, validateViewModel.validatePhone, "Invalid Phone"
        )
        passwordErrorStateFlow.value = validateViewModel.validate(
            passwordStateFlow.value, validateViewModel.validatePwd, "Invalid Password"
        )
        conformErrorPasswordStateFlow.value = validateViewModel.compare(
            passwordStateFlow.value,
            conformPasswordStateFlow.value,
            validateViewModel.compareStrings,
            "Password not matched"
        )
        if (emailErrorStateFlow.value.isEmpty() && blockErrorStateFlow.value.isEmpty() && flatErrorStateFlow.value.isEmpty() && passwordErrorStateFlow.value.isEmpty() && conformErrorPasswordStateFlow.value.isEmpty()) {
            register(navController)
        }

    }

    /* using firebase auth to create user
    * on further investigation we should not encrypt password while firebase auth*/
    private fun register(navController: NavHostController) {
        if (networkUtils.isNetworkAvailable()) {

            firebaseAuth.createUserWithEmailAndPassword(
                emailStateFlow.value, passwordStateFlow.value
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    val userId = firebaseAuth.currentUser?.uid
                    if (userId != null) {
                        val userMap = hashMapOf(
                            "email" to emailStateFlow.value,
                            "block" to blockStateFlow.value,
                            "flat" to flatStateFlow.value,
                            "phone" to phoneStateFlow.value
                        )
                        firestore.collection("users").document(userId).set(userMap)
                            .addOnSuccessListener {
                                viewModelScope.launch {
                                    toastSharedFlow.emit("Registration successful.")
                                }
                                navController.navigate("login") {
                                    popUpTo("register") {
                                        inclusive = true
                                    }
                                }
                            }.addOnFailureListener {
                                loggerUtils.error("Registration", it.message.toString())
                            }
                    } else {
                        // something went wrong during firebase auth
                    }
                } else {
                    viewModelScope.launch {
                        toastSharedFlow.emit(it.exception?.message.toString())
                    }
                }
            }
        } else {
            // show popup to connect internet
        }
    }
}