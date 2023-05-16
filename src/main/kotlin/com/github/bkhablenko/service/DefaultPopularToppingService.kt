package com.github.bkhablenko.service

import com.github.bkhablenko.domain.repository.UserPreferencesRepository
import org.springframework.stereotype.Service

@Service
class DefaultPopularToppingService(
    private val userPreferencesRepository: UserPreferencesRepository,
) : PopularToppingService {

    override fun countDistinctEmailsByTopping() =
        userPreferencesRepository
            .countDistinctEmailsByTopping()
            .associate {
                it.topping to it.emailsCount
            }
}
