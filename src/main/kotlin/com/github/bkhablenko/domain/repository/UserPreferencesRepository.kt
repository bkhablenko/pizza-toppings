package com.github.bkhablenko.domain.repository

import com.github.bkhablenko.domain.view.ToppingAndEmailsCount
import com.github.bkhablenko.domain.model.UserPreferencesEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserPreferencesRepository : JpaRepository<UserPreferencesEntity, UUID> {

    fun findByEmail(email: String): UserPreferencesEntity?

    @Query(
        "select unnest(toppings) as topping, count(distinct email) from user_preferences group by topping order by count desc",
        nativeQuery = true,
    )
    fun countDistinctEmailsByTopping(): List<ToppingAndEmailsCount>
}
