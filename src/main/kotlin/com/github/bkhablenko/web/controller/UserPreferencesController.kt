package com.github.bkhablenko.web.controller

import com.github.bkhablenko.service.UserPreferencesService
import com.github.bkhablenko.web.model.GetPreferencesResponse
import com.github.bkhablenko.web.model.UpdatePreferencesRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user/preferences")
@PreAuthorize("isAuthenticated()")
class UserPreferencesController(private val userPreferencesService: UserPreferencesService) {

    @GetMapping
    fun getPreferences(@AuthenticationPrincipal user: User): GetPreferencesResponse {
        val userPreferences = userPreferencesService.findByEmail(user.username)
        return GetPreferencesResponse.of(userPreferences)
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updatePreferences(
        @AuthenticationPrincipal user: User,
        @RequestBody @Valid payload: UpdatePreferencesRequest,
    ) {
        val userPreferences = payload.toUserPreferences()
        userPreferencesService.update(user.username, userPreferences)
    }
}
