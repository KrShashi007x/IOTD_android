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

/**
 * ViewModel that is used to handling the UI logic
 * We don't need to modify imageState, so we are exposing state
 * directly to ui, and in imageErrorState we need to modify it's state
 * based on response so caching it here.
 *
 */

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
    val imageErrorState = _imageErrorState.asStateFlow() // Making it immutable

    init {
        // Fetching new content when viewModel get initialised
        // This viewModel hosted in mainActivity, so it will get created with activity
        refreshImageOfTheDayContent()
    }

    // Changing response state to Loading, so
    // when response showing error state twice, it will make sure
    // we are showing error message each time it failed
    fun refreshImageOfTheDayContent() {
        viewModelScope.launch(Dispatchers.IO) {
            _imageErrorState.update { ResponseState.Loading() }
            _imageErrorState.update { imageRepository.refreshDailyImage() }
        }
    }

}