package com.example.springaichat.service;

import com.example.springaichat.dto.ChatResponse;
import org.springframework.ai.chat.ChatClient;
import org.springframework.stereotype.Service;

/**
 * Service layer for handling AI chat interactions.
 * Encapsulates the business logic for communicating with the OpenAI chat model
 * via Spring AI's ChatClient abstraction.
 * 
 * Uses the fluent API which is the recommended approach in Spring AI 1.0.0-M4.
 */
@Service
public class ChatService {

    private final ChatClient chatClient;

    /**
     * Constructor injection of ChatClient.
     * Spring AI automatically configures the ChatClient bean based on
     * the OpenAI configuration in application.yml.
     */
    public ChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    /**
     * Processes a user message and returns the AI model's response.
     * Uses the fluent API which is more stable and recommended.
     *
     * @param message The user's input message
     * @return ChatResponse containing the AI's response
     */
    public ChatResponse chat(String message) {
        try {
            // Using fluent API - recommended approach for Spring AI 1.0.0-M4
            String response = chatClient.prompt()
                .user(message)
                .call()
                .content();
            
            return new ChatResponse(response);
        } catch (Exception e) {
            throw new RuntimeException("Error calling AI service: " + e.getMessage(), e);
        }
    }
}

