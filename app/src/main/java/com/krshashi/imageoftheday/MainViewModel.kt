package com.krshashi.imageoftheday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krshashi.imageoftheday.domain.repository.ImageRepository
import com.krshashi.imageoftheday.network.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val imageRepository: ImageRepository
) : ViewModel() {

    val imageUiState = imageRepository.observeDailyImage.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )

    private val _imageErrorState = MutableStateFlow<ResponseState<Unit>>(
        ResponseState.Loading()
    )
    val imageErrorState = _imageErrorState.asStateFlow()

    init {
        // Fetching new content when viewModel get initialised
        refreshImageOfTheDayContent()
    }

    fun refreshImageOfTheDayContent() {
        viewModelScope.launch(Dispatchers.IO) {
            _imageErrorState.update { ResponseState.Loading() }
            _imageErrorState.update { imageRepository.refreshDailyImage() }
        }
    }

}