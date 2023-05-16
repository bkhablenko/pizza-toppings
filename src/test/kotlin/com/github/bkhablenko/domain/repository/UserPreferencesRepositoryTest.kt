package com.github.bkhablenko.domain.repository

import com.github.bkhablenko.domain.model.UserPreferencesEntity
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
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

    @DisplayName("countDistinctEmailsByTopping")
    @Nested
    inner class CountDistinctEmailsByToppingTest {

        @Test
        fun `should order toppings by popularity`() {
            with(entityManager) {
                persist("user.A@gmail.com" to setOf("bacon", "mozzarella", "pepperoni"))
                persist("user.B@gmail.com" to setOf("bacon", "mozzarella"))
                persist("user.C@gmail.com" to setOf("bacon"))
                flush()
                clear()
            }

            val result = userPreferencesRepository.countDistinctEmailsByTopping()
            assertThat(result, hasSize(3))

            // Dealing with proxies here, so must compare properties explicitly
            with(result[0]) {
                assertThat(topping, equalTo("bacon"))
                assertThat(emailsCount, equalTo(3))
            }
            with(result[1]) {
                assertThat(topping, equalTo("mozzarella"))
                assertThat(emailsCount, equalTo(2))
            }
            with(result[2]) {
                assertThat(topping, equalTo("pepperoni"))
                assertThat(emailsCount, equalTo(1))
            }
        }

        private infix fun String.to(toppings: Set<String>) = UserPreferencesEntity(email = this, toppings = toppings)
    }
}
