package io.github.bradpatras.life.ui.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf

class MainViewModel : ViewModel() {
    var isEditing = MutableStateFlow<Boolean>(false)
}