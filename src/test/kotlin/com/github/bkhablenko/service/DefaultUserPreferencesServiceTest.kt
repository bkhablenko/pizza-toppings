package com.github.bkhablenko.service

import com.github.bkhablenko.domain.model.UserPreferencesEntity
import com.github.bkhablenko.domain.repository.UserPreferencesRepository
import com.github.bkhablenko.mockito.getArgument
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@DisplayName("DefaultUserPreferencesService")
class DefaultUserPreferencesServiceTest {

    companion object {
        private const val TEST_EMAIL = "john.smith@gmail.com"
    }

    private val userPreferencesRepository = mock<UserPreferencesRepository>()

    private val userPreferencesService = DefaultUserPreferencesService(userPreferencesRepository)

    @DisplayName("getByEmail")
    @Nested
    inner class GetByEmailTest {

        @Test
        fun `should return found UserPreferencesEntity`() {
            val expectedEntity = UserPreferencesEntity(TEST_EMAIL)
            whenever(userPreferencesRepository.findByEmail(TEST_EMAIL)) doReturn expectedEntity

            val result = userPreferencesService.getByEmail(TEST_EMAIL)
            assertThat(result, equalTo(expectedEntity))
        }

        @Test
        fun `should save new UserPreferencesEntity if not found`() {
            val expectedEntity = UserPreferencesEntity(TEST_EMAIL)

            whenever(userPreferencesRepository.findByEmail(TEST_EMAIL)) doReturn null
            whenever(userPreferencesRepository.save(any())) doAnswer getArgument<UserPreferencesEntity>(0)

            with(userPreferencesService.getByEmail(TEST_EMAIL)) {
                assertThat(email, equalTo(expectedEntity.email))
                assertThat(toppings, equalTo(expectedEntity.toppings))
            }
        }
    }
}
