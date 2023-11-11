package com.astrick.sports.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.astrick.sports.data.ContentRepository
import com.astrick.sports.ui.controllers.SportStateController

/**
 * Composable function representing a container for displaying sports details.
 *
 * This container is responsible for managing the lifecycle of the associated
 * ViewModel and rendering the overall structure of the sports details card.
 *
 * @param sportStateController The controller managing the state of the sports details.
 * @param modifier The modifier to be applied to the card container.
 */
@Composable
fun SportDetailsCardContainer(
    sportStateController: SportStateController,
    modifier: Modifier = Modifier
) {
    val viewModel = remember {
        SportsDetailsViewModel(
            sportsRepo = ContentRepository(),
            sportDetailsController = sportStateController,
        )
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val lifecycleObserver = object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                viewModel.onDestroy()
                super.onDestroy(owner)
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }
    
    val randomSport by viewModel.sport.collectAsState()
    SportDetailsCardContent(randomSport, modifier)
}
