package com.krshashi.imageoftheday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krshashi.imageoftheday.domain.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
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

    init {
        // Fetching new content when viewModel get initialised
        refreshImageOfTheDayContent()
    }

    fun refreshImageOfTheDayContent() {
        viewModelScope.launch(Dispatchers.IO) {
            imageRepository.refreshDailyImage()
        }
    }

}