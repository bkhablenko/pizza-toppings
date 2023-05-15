package com.github.bkhablenko.service

import com.github.bkhablenko.domain.model.UserPreferencesEntity

interface UserPreferencesService {

    fun getByEmail(email: String): UserPreferencesEntity
}
