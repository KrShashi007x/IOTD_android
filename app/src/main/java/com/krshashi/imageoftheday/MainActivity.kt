package com.krshashi.imageoftheday

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.request.CachePolicy
import com.krshashi.imageoftheday.databinding.ActivityMainBinding
import com.krshashi.imageoftheday.domain.model.ImageItem
import com.krshashi.imageoftheday.domain.model.MediaType
import com.krshashi.imageoftheday.utils.logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binder: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binder.root)

        // Fetching new content on refresh
        binder.refreshLayout.setOnRefreshListener {
            viewModel.refreshImageOfTheDayContent()
        }

        // TODO : Show error initial + regular

        lifecycleScope.launch {
            viewModel.imageUiState
                .filterNotNull()
                .onEach { logger(it.toString()) }
                .collect {
                    binder.refreshLayout.isRefreshing = false
                    binder.loadingView.visibility = View.GONE
                    updateContentView(it)
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