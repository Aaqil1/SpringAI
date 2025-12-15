package com.example.springaichat.controller;

import com.example.springaichat.dto.ChatRequest;
import com.example.springaichat.dto.ChatResponse;
import com.example.springaichat.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling chat API requests.
 * Provides the /api/ai/chat endpoint for interacting with the AI model.
 */
@RestController
@RequestMapping("/api/ai")
public class ChatController {

    private final ChatService chatService;

    /**
     * Constructor injection of ChatService.
     */
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    /**
     * POST endpoint for chat interactions.
     * Accepts a ChatRequest with a message and returns a ChatResponse.
     *
     * @param request The chat request containing the user's message
     * @return ResponseEntity containing the AI's response
     */
    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@Valid @RequestBody ChatRequest request) {
        try {
            ChatResponse response = chatService.chat(request.message());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Log the error (in production, use proper logging framework)
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ChatResponse("Error processing request: " + e.getMessage()));
        }
    }
}

