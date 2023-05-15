package com.github.bkhablenko.domain.repository

import com.github.bkhablenko.config.JpaConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@DataJpaTest
@Import(JpaConfig::class)
abstract class AbstractDataJpaTest {

    @Autowired
    protected lateinit var entityManager: TestEntityManager
}
