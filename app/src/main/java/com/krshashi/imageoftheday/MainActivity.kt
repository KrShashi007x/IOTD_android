package com.krshashi.imageoftheday

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.request.CachePolicy
import com.google.android.material.snackbar.Snackbar
import com.krshashi.imageoftheday.databinding.ActivityMainBinding
import com.krshashi.imageoftheday.domain.model.ImageItem
import com.krshashi.imageoftheday.domain.model.MediaType
import com.krshashi.imageoftheday.network.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binder: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private var displayingContent: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binder.root)

        // Fetching new content on refresh
        binder.refreshLayout.setOnRefreshListener {
            viewModel.refreshImageOfTheDayContent()
        }

        lifecycleScope.launch {
            viewModel.imageUiState.filterNotNull()
                .onEach { binder.loadingView.visibility = View.GONE }
                .onEach { displayingContent = true }
                .collect(::updateContentView)
        }

        contentErrorHandler()
    }

    private fun contentErrorHandler() {
        val snackBar = Snackbar.make(
            binder.root,
            "Unable to load new content 😭",
            Snackbar.LENGTH_LONG
        )

        lifecycleScope.launch {
            viewModel.imageErrorState.collect {
                when (it) {
                    is ResponseState.Loading -> Unit
                    is ResponseState.Success -> binder.refreshLayout.isRefreshing = false
                    is ResponseState.Failure -> {
                        binder.refreshLayout.isRefreshing = false
                        snackBar.setAction("Ok") { snackBar.dismiss() }.show()
                    }
                }
            }
        }
    }

    private fun updateContentView(imageItem: ImageItem) {
        binder.titleTextView.text = imageItem.title
        binder.descriptionTextView.text = imageItem.title
        binder.dateTextView.text = imageItem.date
        binder.descriptionTextView.text = imageItem.explanation

        when (imageItem.mediaType) {
            MediaType.VIDEO -> binder.playButtonView.visibility = View.VISIBLE
            else -> binder.playButtonView.visibility = View.INVISIBLE
        }

        // Caching image offline
        binder.imageView.load(imageItem.hdurl) {
            diskCachePolicy(CachePolicy.ENABLED)
            placeholder(R.drawable.placeholder_view)
        }
    }

}