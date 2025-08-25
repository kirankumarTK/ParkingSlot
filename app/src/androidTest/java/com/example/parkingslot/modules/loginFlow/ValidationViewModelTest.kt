package com.example.parkingslot.modules.loginFlow

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ValidationViewModelTest {

    private lateinit var validateViewModel: ValidateViewModel

    @Before
    fun initViewModel() {
        validateViewModel = ValidateViewModel()
    }

    @Test
    fun validateTest() {
        val result = validateViewModel.validate(
            "kirancool11@gmail.com", validateViewModel.validateEmail, "Invalid Email"
        )
        assertEquals("", result)
    }

}