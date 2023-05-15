package com.github.bkhablenko.web.controller

import com.github.bkhablenko.domain.model.UserPreferencesEntity
import com.github.bkhablenko.service.UserPreferencesService
import org.hamcrest.Matchers.contains
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(UserPreferencesController::class)
class UserPreferencesControllerTest {

    companion object {
        private const val TEST_EMAIL = "john.smith@gmail.com"
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var userPreferencesService: UserPreferencesService

    @DisplayName("GET /api/v1/user/preferences")
    @Nested
    inner class GetPreferencesTest {

        @Test
        @WithMockUser(username = TEST_EMAIL)
        fun `should respond with 200 OK on success`() {
            whenever(userPreferencesService.getByEmail(TEST_EMAIL)) doReturn UserPreferencesEntity(
                email = TEST_EMAIL,
                toppings = setOf("mozzarella", "pepperoni"),
            )
            getPreferences().andExpect {
                status { isOk() }
                content {
                    jsonPath("$.toppings", contains("mozzarella", "pepperoni"))
                }
            }
        }

        @Test
        fun `should respond with 401 Unauthorized on missing Authorization header`() {
            getPreferences().andExpect {
                status { isUnauthorized() }
            }
            verifyNoInteractions(userPreferencesService)
        }

        private fun getPreferences() = mockMvc.get("/api/v1/user/preferences").andDo { print() }
    }
}
