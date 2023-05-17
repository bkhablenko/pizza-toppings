package com.github.bkhablenko.service.impl

import com.github.bkhablenko.component.ToppingNormalizer
import com.github.bkhablenko.domain.model.UserPreferencesEntity
import com.github.bkhablenko.domain.repository.UserPreferencesRepository
import com.github.bkhablenko.service.UserPreferencesService
import com.github.bkhablenko.service.model.UserPreferences
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DefaultUserPreferencesService(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val toppingNormalizer: ToppingNormalizer,
) : UserPreferencesService {

    companion object {
        private val DEFAULT_VALUES = UserPreferences()
    }

    override fun findByEmail(email: String): UserPreferences {
        val entity = userPreferencesRepository.findByEmail(email)
        return if (entity != null) {
            with(entity) {
                UserPreferences(toppings = toppings)
            }
        } else DEFAULT_VALUES
    }

    override fun update(email: String, userPreferences: UserPreferences) {
        val entity = userPreferencesRepository.findByEmail(email) ?: UserPreferencesEntity(email)
        entity.apply {
            toppings = normalizeToppings(userPreferences.toppings)
        }
        userPreferencesRepository.save(entity)
    }

    private fun normalizeToppings(toppings: Set<String>) =
        toppings.map { toppingNormalizer.normalizeTopping(it) }.toSet()
}
