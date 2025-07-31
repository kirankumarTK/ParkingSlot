package com.example.parkingslot.modules.loginFlow

import android.util.Patterns
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/*Shared view model for login & registration flow inorder to validate fields*/

@HiltViewModel
class ValidateViewModel @Inject constructor() : ViewModel() {

    internal val isLoadingStateFlow = MutableStateFlow<Boolean>(false)

    internal fun validate(input: String, validator: (String) -> Boolean, errorMsg: String): String {
        return if (validator(input)) "" else errorMsg
    }

    internal fun compare(
        input: String, input1: String, validator: (String, String) -> Boolean, errorMsg: String
    ): String {
        return if (validator(input, input1)) "" else errorMsg
    }

    internal val validateEmail: (String) -> Boolean = {
        it.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(it).matches()
    }

    internal val validatePwd: (String) -> Boolean = {
        it.isNotEmpty() && it.length > 8
    }

    internal val validateBlock: (String) -> Boolean = {
        it.isNotEmpty()
    }

    internal val compareStrings: (String, String) -> Boolean = { s1, s2 ->
        s1 == s2
    }
    internal val validatePhone: (String) -> Boolean = {
        it.isNotEmpty() && Patterns.PHONE.matcher(it).matches()
    }
}