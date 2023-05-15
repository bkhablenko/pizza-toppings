package com.github.bkhablenko.web.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class ErrorResponse(

    @JsonProperty("timestamp")
    val timestamp: LocalDateTime,

    @JsonProperty("status")
    val status: Int,

    @JsonProperty("error")
    val error: String,

    @JsonProperty("message")
    val message: String,

    @JsonProperty("path")
    val path: String,
)
