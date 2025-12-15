package com.example.springaichat.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for chat request payload.
 * Contains the user's message to be sent to the AI model.
 */
public record ChatRequest(
        @NotBlank(message = "Message cannot be blank")
        String message
) {
}

