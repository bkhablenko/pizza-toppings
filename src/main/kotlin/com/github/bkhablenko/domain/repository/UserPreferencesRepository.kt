package com.github.bkhablenko.domain.repository

import com.github.bkhablenko.domain.model.UserPreferencesEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserPreferencesRepository : JpaRepository<UserPreferencesEntity, UUID> {

    fun findByEmail(email: String): UserPreferencesEntity?
}
