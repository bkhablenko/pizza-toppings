package com.github.bkhablenko.service

import com.github.bkhablenko.domain.model.UserPreferencesEntity
import com.github.bkhablenko.domain.repository.UserPreferencesRepository
import com.github.bkhablenko.service.model.UserPreferences
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DefaultUserPreferencesService(
    private val userPreferencesRepository: UserPreferencesRepository,
) : UserPreferencesService {

    companion object {
        private val DEFAULT_VALUES = UserPreferences()
    }

    override fun findByEmail(email: String): UserPreferences {
        return userPreferencesRepository.findByEmail(email)?.toUserPreferences() ?: DEFAULT_VALUES
    }

    override fun update(email: String, userPreferences: UserPreferences) {
        val entity = userPreferencesRepository.findByEmail(email) ?: UserPreferencesEntity(email)
        entity.apply {
            toppings = userPreferences.toppings
        }
        userPreferencesRepository.save(entity)
    }

    private fun UserPreferencesEntity.toUserPreferences() = UserPreferences(toppings = toppings)
}
