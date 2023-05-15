package com.github.bkhablenko.web.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.bkhablenko.domain.model.UserPreferencesEntity

data class GetPreferencesResponse(

    @JsonProperty("toppings")
    val toppings: Set<String> = emptySet(),
) {

    companion object {
        fun of(userPreferences: UserPreferencesEntity) = GetPreferencesResponse(toppings = userPreferences.toppings)
    }
}
