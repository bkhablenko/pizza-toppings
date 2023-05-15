package com.github.bkhablenko.service

import com.github.bkhablenko.domain.model.UserPreferencesEntity
import com.github.bkhablenko.domain.repository.UserPreferencesRepository
import org.springframework.stereotype.Service

@Service
class DefaultUserPreferencesService(
    private val userPreferencesRepository: UserPreferencesRepository,
) : UserPreferencesService {

    override fun getByEmail(email: String): UserPreferencesEntity {
        return with(userPreferencesRepository) {
            findByEmail(email) ?: save(UserPreferencesEntity(email))
        }
    }
}
