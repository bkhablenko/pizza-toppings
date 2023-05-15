package com.github.bkhablenko.web.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotEmpty

data class UpdatePreferencesRequest(

    @JsonProperty("toppings")
    val toppings: Set<@NotEmpty String> = emptySet(),
) {

    @JsonIgnore
    val normalizedToppings = toppings.forEach { it.lowercase().trim() }
}
