package com.astrick.sports.ui

import com.astrick.sports.data.Sport
import com.astrick.sports.ui.controllers.SportController
import com.astrick.sports.ui.controllers.SportController.SportState
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class SportControllerTest {
    
    lateinit var sportController: SportController
    
    @Before
    fun setup() {
        sportController = SportController()
    }
    
    @Test
    fun `randomize() - replaces previous sports - When updated`() {
        // Given
        val list = listOf(
            Sport("One Sport", "One description"),
            Sport("Two Sport", "Two description"),
            Sport("Three Sport", "Three description"),
            Sport("Four Sport", "Four description")
        )
        sportController.updateSports(list)
        
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
    fun `updateSports() - replaces previous sports - When updated`() {
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
        sportController.updateSports(listOne)
        sportController.updateSports(listTwo)
        
        // Then
        assertThat(sportController.sports)
            .containsExactlyElementsIn(listTwo)
    }
    
}
