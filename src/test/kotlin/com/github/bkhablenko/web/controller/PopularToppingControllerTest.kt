package com.github.bkhablenko.web.controller

import com.github.bkhablenko.service.PopularToppingService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(PopularToppingController::class)
class PopularToppingControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var popularToppingService: PopularToppingService

    @DisplayName("GET /api/v1/toppings/popular")
    @Nested
    inner class GetPopularToppingsTest {

        @Test
        fun `should respond with 200 OK on success`() {
            whenever(popularToppingService.countDistinctEmailsByTopping()) doReturn mapOf(
                "bacon" to 3,
                "mozzarella" to 2,
                "pepperoni" to 1,
            )

            getPopularToppings().andExpect {
                status { isOk() }
                content {
                    json("""{"bacon":3,"mozzarella":2,"pepperoni":1}""")
                }
            }
        }

        private fun getPopularToppings() =
            mockMvc
                .get("/api/v1/toppings/popular")
                .andDo { print() }
    }
}
