package com.astrick.sports.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.astrick.sports.data.ContentRepository
import com.astrick.sports.data.Resource
import com.astrick.sports.data.Sport
import com.astrick.sports.ui.controllers.SportController
import com.astrick.sports.ui.controllers.SportController.*
import com.astrick.sports.ui.controllers.SportController.SportState
import com.astrick.sports.ui.controllers.SportStateController.*
import com.astrick.sports.ui.util.CoroutineDispatcherRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
internal class SportsDetailsViewModelTest {
    
    @get:Rule
    val instantRule = InstantTaskExecutorRule()
    @get:Rule
    val dispatcherRule = CoroutineDispatcherRule()
    
    lateinit var sports: List<Sport>
    lateinit var sportDetailsController: SportController
    lateinit var repo: ContentRepository
    lateinit var viewModel: SportsDetailsViewModel
    
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        sports = Sport.createMockedSports()
        sportDetailsController = SportController()
        repo = ContentRepository(dispatcherRule.dispatcher)
    }
    
    @Test
    fun `sport - is updated - When sport state is restored`() = runTest {
        setup()
        // Given
        viewModel = SportsDetailsViewModel(
            sportsRepo = repo,
            sportDetailsController = sportDetailsController,
        )
        
        viewModel.sport.test {
            assertThat(awaitItem())
                .isInstanceOf(Resource.Loading::class.java)
            
            // When
            sportDetailsController.restoreSportState(SportState.Ready(sports[0]), sports)
    
            // Then
            val sport = (awaitItem() as Resource.Ready).content
            assertThat(sport.name)
                .isEqualTo(sports[0].name)
            assertThat(sport.description)
                .isEqualTo(sports[0].description)
        }
    }
    
    @Test
    fun `sport - loads correctly - When sport state is empty`() = runTest {
        setup()
        // Given
        viewModel = SportsDetailsViewModel(
            sportsRepo = repo,
            sportDetailsController = sportDetailsController,
        )
        
        viewModel.sport.test {
            assertThat(awaitItem())
                .isInstanceOf(Resource.Loading::class.java)
            
            // When
            sportDetailsController.restoreSportState(SportState.Empty, emptyList())
            
            // Then
            assertThat(awaitItem())
                .isInstanceOf(Resource.Ready::class.java)
        }
    }
    
}
