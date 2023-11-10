package com.astrick.sports.ui

import app.cash.turbine.test
import com.astrick.sports.data.Sport
import com.astrick.sports.ui.controllers.SportController
import com.astrick.sports.ui.controllers.SportController.SportState
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SportControllerTest {
    
    lateinit var sportController: SportController
    
    @Before
    fun setup() {
        sportController = SportController()
    }
    
    @Test
    fun `randomize() - prevents getting replaced with the same sport - When updated`() {
        // Given
        val list = listOf(
            Sport("One Sport", "One description"),
            Sport("Two Sport", "Two description"),
            Sport("Three Sport", "Three description"),
            Sport("Four Sport", "Four description")
        )
        sportController.onSportsLoaded(list)
        
        var previous: Sport? = null
        repeat(1_000) {
            // When
            sportController.randomize()
    
            // Then
            val ready = (sportController.sportState.value as SportState.Ready)
            assertThat(previous)
                .isNotEqualTo(ready.sport)
            previous = ready.sport
        }
    }
    
    @Test
    fun `randomize() - does not crash - When sportState is Loading`() = runTest {
        // Given
        val initialLoadingState = sportController.sportState.value
        assertThat(initialLoadingState)
            .isInstanceOf(SportState.Loading::class.java)
    
        // When
        sportController.randomize()
    
        // Then
        val currentState = sportController.sportState.value
        assertThat(currentState)
            .isInstanceOf(SportState.Loading::class.java)
    }
    
    @Test
    fun `onSportsLoaded() - replaces previous sports - When updated`() {
        // Given
        val listOne = listOf(
            Sport("One Sport", "One description"),
            Sport("Two Sport", "Two description")
        )
        val listTwo = listOf(
            Sport("Three Sport", "Three description"),
            Sport("Four Sport", "Four description")
        )
        
        // When
        sportController.onSportsLoaded(listOne)
        sportController.onSportsLoaded(listTwo)
        
        // Then
        assertThat(sportController.sports)
            .containsExactlyElementsIn(listTwo)
    }
    
    @Test
    fun `onSportsLoaded() - updates sportState to Ready`() = runTest {
        // Given
        val listOne = listOf(
            Sport("One Sport", "One description"),
            Sport("Two Sport", "Two description")
        )
        
        sportController.sportState.test {
            assertThat(awaitItem())
                .isInstanceOf(SportState.Loading::class.java)
            
            // When
            sportController.onSportsLoaded(listOne)
    
            // Then
            assertThat(awaitItem())
                .isInstanceOf(SportState.Ready::class.java)
        }

    }
    
}
