package com.example.springaichat.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for ChatClient bean.
 * Creates a ChatClient bean from the auto-configured ChatModel.
 */
@Configuration
public class ChatClientConfig {

    /**
     * Creates a ChatClient bean from the ChatModel.
     * Spring AI auto-configures ChatModel based on application.yml settings.
     *
     * @param chatModel The ChatModel bean (auto-configured by Spring AI)
     * @return ChatClient instance
     */
    @Bean
    public ChatClient chatClient(ChatModel chatModel) {
        return ChatClient.builder(chatModel).build();
    }
}

