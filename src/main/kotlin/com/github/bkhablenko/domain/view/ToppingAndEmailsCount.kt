package com.github.bkhablenko.domain.view

import com.github.bkhablenko.domain.repository.UserPreferencesRepository
import org.springframework.beans.factory.annotation.Value

/**
 * @see [UserPreferencesRepository.countDistinctEmailsByTopping]
 */
interface ToppingAndEmailsCount {

    val topping: String

    @get:Value("#{target.count}")
    val emailsCount: Int
}
