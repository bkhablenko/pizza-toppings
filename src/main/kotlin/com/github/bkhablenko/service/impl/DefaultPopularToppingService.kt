package com.github.bkhablenko.service.impl

import com.github.bkhablenko.domain.repository.UserPreferencesRepository
import com.github.bkhablenko.service.PopularToppingService
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
