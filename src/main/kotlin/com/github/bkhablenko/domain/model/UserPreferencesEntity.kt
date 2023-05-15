package com.github.bkhablenko.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.NaturalId

@Entity
@Table(name = "user_preferences")
class UserPreferencesEntity(

    @NaturalId
    @Column(name = "email")
    var email: String,

    @Column(name = "toppings")
    var toppings: Set<String> = emptySet()

) : AuditedEntity()
