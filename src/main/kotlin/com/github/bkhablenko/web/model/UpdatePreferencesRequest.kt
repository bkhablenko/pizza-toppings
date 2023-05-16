package com.github.bkhablenko.web.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.bkhablenko.service.model.UserPreferences
import jakarta.validation.constraints.NotEmpty

data class UpdatePreferencesRequest(

    @JsonProperty("toppings")
    val toppings: Set<@NotEmpty String> = emptySet(),
) {

    fun toUserPreferences() = UserPreferences(toppings = toppings)
}
