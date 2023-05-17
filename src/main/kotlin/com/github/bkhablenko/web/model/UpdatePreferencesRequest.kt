package com.github.bkhablenko.web.model

import com.github.bkhablenko.service.model.UserPreferences
import jakarta.validation.constraints.NotEmpty

data class UpdatePreferencesRequest(

    val toppings: Set<@NotEmpty String> = emptySet(),
) {

    fun toUserPreferences() = UserPreferences(toppings = toppings)
}
