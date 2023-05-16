package com.github.bkhablenko.web.controller

import com.github.bkhablenko.service.UserPreferencesService
import com.github.bkhablenko.service.model.UserPreferences
import org.hamcrest.Matchers.contains
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argThat
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.put

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
            whenever(userPreferencesService.findByEmail(TEST_EMAIL)) doReturn UserPreferences(
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

        private fun getPreferences() =
            mockMvc
                .get("/api/v1/user/preferences")
                .andDo { print() }
    }

    @DisplayName("PUT /api/v1/user/preferences")
    @Nested
    inner class UpdatePreferencesTest {

        @Test
        @WithMockUser(username = TEST_EMAIL)
        fun `should respond with 204 No Content on success`() {
            val payload = """{"toppings":["mozzarella","pepperoni"]}"""

            updatePreferences(payload).andExpect {
                status { isNoContent() }
                content {
                    bytes(ByteArray(0))
                }
            }

            verify(userPreferencesService).update(
                eq(TEST_EMAIL),
                argThat { toppings == setOf("mozzarella", "pepperoni") },
            )
        }

        @Test
        @WithMockUser(username = TEST_EMAIL)
        fun `should respond with 400 Bad Request on invalid payload`() {
            val payload = """{"toppings":[""]}"""

            updatePreferences(payload).andExpect {
                status { isBadRequest() }
            }
            verifyNoInteractions(userPreferencesService)
        }

        @Test
        fun `should respond with 401 Unauthorized on missing Authorization header`() {
            val payload = """{"toppings":["mozzarella","pepperoni"]}"""

            updatePreferences(payload).andExpect {
                status { isUnauthorized() }
            }
            verifyNoInteractions(userPreferencesService)
        }

        private fun updatePreferences(payload: String) =
            mockMvc
                .put("/api/v1/user/preferences") {
                    contentType = MediaType.APPLICATION_JSON
                    content = payload
                }
                .andDo { print() }
    }
}
