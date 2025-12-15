package com.example.springaichat.service;

import com.example.springaichat.dto.ChatResponse;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

/**
 * Service layer for handling AI chat interactions.
 * Encapsulates the business logic for communicating with the OpenAI chat model
 * via Spring AI's ChatClient abstraction.
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
     *
     * @param message The user's input message
     * @return ChatResponse containing the AI's response
     */
    public ChatResponse chat(String message) {
        // Create a prompt with the user message
        Prompt prompt = new Prompt(new UserMessage(message));
        
        // Call the AI model and get the response
        String response = chatClient.call(prompt).getResult().getOutput().getContent();
        
        return new ChatResponse(response);
    }
}

