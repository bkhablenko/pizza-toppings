package com.github.bkhablenko.component

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("TrimmingLowerCaseToppingNormalizer")
class TrimmingLowerCaseToppingNormalizerTest {

    private val toppingNormalizer = TrimmingLowerCaseToppingNormalizer()

    @Test
    fun `should convert characters to lower case`() {
        val result = toppingNormalizer.normalizeTopping("MoZZareLLa")
        assertThat(result, equalTo("mozzarella"))
    }

    @Test
    fun `should trim leading and trailing whitespace characters`() {
        val result = toppingNormalizer.normalizeTopping(" mozzarella \t")
        assertThat(result, equalTo("mozzarella"))
    }
}
