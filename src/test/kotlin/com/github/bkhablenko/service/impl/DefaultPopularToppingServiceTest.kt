package com.github.bkhablenko.service.impl

import com.github.bkhablenko.domain.repository.UserPreferencesRepository
import com.github.bkhablenko.domain.view.ToppingAndEmailsCount
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.aMapWithSize
import org.hamcrest.Matchers.hasEntry
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@DisplayName("DefaultPopularToppingService")
class DefaultPopularToppingServiceTest {

    private val userPreferencesRepository = mock<UserPreferencesRepository>()

    private val popularToppingService = DefaultPopularToppingService(userPreferencesRepository)

    @DisplayName("countDistinctEmailsByTopping")
    @Nested
    inner class CountDistinctEmailsByToppingTest {

        @Test
        fun `should map topping to emailsCount`() {
            whenever(userPreferencesRepository.countDistinctEmailsByTopping()) doReturn listOf(
                SimpleToppingAndEmailsCount("bacon", 3),
                SimpleToppingAndEmailsCount("mozzarella", 2),
                SimpleToppingAndEmailsCount("pepperoni", 1),
            )

            val result = popularToppingService.countDistinctEmailsByTopping()
            assertThat(result, aMapWithSize(3))
            assertThat(result, hasEntry("bacon", 3))
            assertThat(result, hasEntry("mozzarella", 2))
            assertThat(result, hasEntry("pepperoni", 1))
        }
    }

    private data class SimpleToppingAndEmailsCount(
        override val topping: String,
        override val emailsCount: Int,
    ) : ToppingAndEmailsCount
}
