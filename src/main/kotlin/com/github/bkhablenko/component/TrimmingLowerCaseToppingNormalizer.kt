package com.github.bkhablenko.component

import org.springframework.stereotype.Component

@Component
class TrimmingLowerCaseToppingNormalizer : ToppingNormalizer {

    override fun normalizeTopping(topping: String) = topping.lowercase().trim()
}
