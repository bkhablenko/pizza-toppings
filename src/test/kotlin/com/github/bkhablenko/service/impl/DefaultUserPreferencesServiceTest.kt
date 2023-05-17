package com.github.bkhablenko.service.impl

import com.github.bkhablenko.component.ToppingNormalizer
import com.github.bkhablenko.domain.model.UserPreferencesEntity
import com.github.bkhablenko.domain.repository.UserPreferencesRepository
import com.github.bkhablenko.mockito.getArgument
import com.github.bkhablenko.service.model.UserPreferences
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.sameInstance
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.argThat
import org.mockito.kotlin.check
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@DisplayName("DefaultUserPreferencesService")
class DefaultUserPreferencesServiceTest {

    companion object {
        private const val TEST_EMAIL = "john.smith@gmail.com"
    }

    private val userPreferencesRepository = mock<UserPreferencesRepository>()

    private val toppingNormalizer = mock<ToppingNormalizer> {
        // Return the same value
        on { normalizeTopping(any()) } doAnswer getArgument<String>(0)
    }

    private val userPreferencesService = DefaultUserPreferencesService(userPreferencesRepository, toppingNormalizer)

    @DisplayName("findByEmail")
    @Nested
    inner class FindByEmailTest {

        @Test
        fun `should return found values`() {
            val entity = UserPreferencesEntity(email = TEST_EMAIL, toppings = setOf("mozzarella", "pepperoni"))
            whenever(userPreferencesRepository.findByEmail(TEST_EMAIL)) doReturn entity

            val expectedValues = UserPreferences(toppings = entity.toppings)
            assertThat(userPreferencesService.findByEmail(TEST_EMAIL), equalTo(expectedValues))
        }

        @Test
        fun `should return default values if not found`() {
            whenever(userPreferencesRepository.findByEmail(TEST_EMAIL)) doReturn null

            val expectedValues = UserPreferences()
            assertThat(userPreferencesService.findByEmail(TEST_EMAIL), equalTo(expectedValues))
        }
    }

    @DisplayName("update")
    @Nested
    inner class UpdateTest {

        @Test
        fun `should update existing entity if found`() {
            val entity = UserPreferencesEntity(email = TEST_EMAIL, toppings = setOf("mozzarella"))
            whenever(userPreferencesRepository.findByEmail(TEST_EMAIL)) doReturn entity

            val userPreferences = UserPreferences(toppings = setOf("pepperoni"))
            userPreferencesService.update(TEST_EMAIL, userPreferences)

            verify(userPreferencesRepository).save(check {
                assertThat(it, sameInstance(entity))
                assertThat(it.email, equalTo(TEST_EMAIL))
                assertThat(it.toppings, equalTo(userPreferences.toppings))
            })
        }

        @Test
        fun `should persist new entity if not found`() {
            whenever(userPreferencesRepository.findByEmail(TEST_EMAIL)) doReturn null

            val userPreferences = UserPreferences(toppings = setOf("pepperoni"))
            userPreferencesService.update(TEST_EMAIL, userPreferences)

            verify(userPreferencesRepository).save(check {
                assertThat(it.email, equalTo(TEST_EMAIL))
                assertThat(it.toppings, equalTo(userPreferences.toppings))
            })
        }

        @Test
        fun `should normalize topping names`() {
            // "Normalize" by prepending a prefix
            val prefix = "normalized"
            whenever(toppingNormalizer.normalizeTopping(any())) doAnswer { "$prefix:${it.arguments[0] as String}" }

            val userPreferences = UserPreferences(toppings = setOf("MoZZareLLa", " pepperoni \t"))
            userPreferencesService.update(TEST_EMAIL, userPreferences)

            verify(userPreferencesRepository).save(argThat {
                toppings.all {
                    it.startsWith("normalized")
                }
            })
        }
    }
}
