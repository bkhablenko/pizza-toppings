package com.github.bkhablenko.web.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.bkhablenko.service.model.UserPreferences

data class GetPreferencesResponse(

    @JsonProperty("toppings")
    val toppings: Set<String> = emptySet(),
) {

    companion object {
        fun of(userPreferences: UserPreferences) = GetPreferencesResponse(toppings = userPreferences.toppings)
    }
}
