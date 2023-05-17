package com.github.bkhablenko.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.NaturalId

@Entity
@Table(name = "user_preferences")
class UserPreferencesEntity(

    @NaturalId
    @Column
    var email: String,

    @Column
    var toppings: Set<String> = emptySet()

) : AuditedEntity()
