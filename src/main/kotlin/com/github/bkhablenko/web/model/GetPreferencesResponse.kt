package com.github.bkhablenko.web.model

import com.github.bkhablenko.service.model.UserPreferences

data class GetPreferencesResponse(

    val toppings: Set<String> = emptySet(),
) {

    companion object {
        fun of(userPreferences: UserPreferences) = GetPreferencesResponse(toppings = userPreferences.toppings)
    }
}
