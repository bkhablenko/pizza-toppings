package com.github.bkhablenko.service

import com.github.bkhablenko.service.model.UserPreferences

interface UserPreferencesService {

    fun findByEmail(email: String): UserPreferences

    fun update(email: String, userPreferences: UserPreferences)
}
