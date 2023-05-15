package com.github.bkhablenko.domain.repository

import com.github.bkhablenko.domain.model.UserPreferencesEntity
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.notNullValue
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@DisplayName("UserPreferencesRepository")
class UserPreferencesRepositoryTest : AbstractDataJpaTest() {

    @Autowired
    private lateinit var userPreferencesRepository: UserPreferencesRepository

    @DisplayName("findByEmail")
    @Nested
    inner class FindByEmailTest {

        @Test
        fun `should return UserPreferencesEntity if found`() {
            val email = "john.smith@gmail.com"
            val expectedToppings = setOf("mozzarella", "pepperoni")

            entityManager.persistAndFlush(UserPreferencesEntity(email = email, toppings = expectedToppings))
            entityManager.clear()

            val foundEntity = userPreferencesRepository.findByEmail(email)
            assertThat(foundEntity, notNullValue())
            assertThat(foundEntity!!.toppings, equalTo(expectedToppings))
        }

        @Test
        fun `should return null if not found`() {
            val foundEntity = userPreferencesRepository.findByEmail("john.smith@gmail.com")
            assertThat(foundEntity, nullValue())
        }
    }
}
