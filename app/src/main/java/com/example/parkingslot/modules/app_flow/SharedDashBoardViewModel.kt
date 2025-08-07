package com.example.parkingslot.modules.app_flow

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SharedDashBoardViewModel @Inject constructor() : ViewModel() {

    internal val showPageLoadingStateFlow = MutableStateFlow<Boolean>(false)
}