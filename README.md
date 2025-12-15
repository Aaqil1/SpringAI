# Spring AI Chat Application

A production-ready Spring Boot application demonstrating integration with OpenAI's chat models using Spring AI framework.

## Overview

This application provides a RESTful API endpoint for interacting with OpenAI's GPT models through Spring AI's abstraction layer. It follows clean architecture principles with proper separation of concerns, error handling, and validation.

## Technology Stack

- **Java 17** - Modern Java features and records
- **Spring Boot 3.2.0** - Latest stable Spring Boot version
- **Spring AI 1.0.0-M4** - Spring's AI abstraction framework
- **OpenAI GPT-3.5-turbo** - AI model for chat interactions
- **Maven** - Dependency management and build tool

## Project Structure

```
spring-ai-chat/
├── pom.xml                                    # Maven dependencies and configuration
├── README.md                                  # This file
├── ARCHITECTURE.md                            # Detailed architecture explanation
├── CONCEPTS.md                                # Core concepts explained
├── FLOW.md                                    # Request flow documentation
└── src/
    └── main/
        ├── java/com/example/springaichat/
        │   ├── SpringAiChatApplication.java   # Main application entry point
        │   ├── controller/
        │   │   └── ChatController.java        # REST API endpoint
        │   ├── service/
        │   │   └── ChatService.java           # Business logic layer
        │   ├── dto/
        │   │   ├── ChatRequest.java           # Request DTO
        │   │   └── ChatResponse.java          # Response DTO
        │   └── exception/
        │       └── GlobalExceptionHandler.java # Global error handling
        └── resources/
            └── application.yml                # Configuration file
```

## Quick Start

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- OpenAI API key

### Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Aaqil1/SpringAI.git
   cd SpringAI
   ```

2. **Configure API Key:**
   
   Option 1: Set environment variable
   ```bash
   export OPENAI_API_KEY=your-api-key-here
   ```
   
   Option 2: Update `application.yml`
   ```yaml
   spring:
     ai:
       openai:
         api-key: your-api-key-here
   ```

3. **Build and Run:**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Test the API:**
   ```bash
   curl -X POST http://localhost:8080/api/ai/chat \
     -H "Content-Type: application/json" \
     -d '{"message": "Hello, how are you?"}'
   ```

## API Documentation

### Endpoint: POST /api/ai/chat

**Request:**
```json
{
  "message": "Your question or message here"
}
```

**Response:**
```json
{
  "response": "AI model's response"
}
```

**Example:**
```bash
curl -X POST http://localhost:8080/api/ai/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "Explain Spring Boot in one sentence"}'
```

**Response:**
```json
{
  "response": "Spring Boot is a framework that simplifies the development of Spring applications by providing auto-configuration, embedded servers, and production-ready features out of the box."
}
```

## Configuration

The application configuration is externalized in `application.yml`:

```yaml
spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY:your-api-key-here}
      chat:
        options:
          model: gpt-3.5-turbo
          temperature: 0.7

server:
  port: 8080
```

### Configuration Options

- **api-key**: Your OpenAI API key (use environment variable for security)
- **model**: The OpenAI model to use (gpt-3.5-turbo, gpt-4, etc.)
- **temperature**: Controls randomness (0.0 = deterministic, 1.0 = creative)

## Error Handling

The application includes comprehensive error handling:

- **Validation Errors (400)**: Invalid request format or missing required fields
- **Server Errors (500)**: Internal errors during AI processing

Example error response:
```json
{
  "response": "Validation error: Message cannot be blank"
}
```

## Key Features

✅ **Clean Architecture** - Separation of concerns with layered architecture  
✅ **Constructor Injection** - Dependency injection best practices  
✅ **Input Validation** - Jakarta Bean Validation  
✅ **Error Handling** - Global exception handler  
✅ **Production Ready** - No demo shortcuts, proper error handling  
✅ **Externalized Configuration** - Environment-based configuration  

## Documentation

- [ARCHITECTURE.md](ARCHITECTURE.md) - Detailed architecture and design patterns
- [CONCEPTS.md](CONCEPTS.md) - Core concepts and Spring AI fundamentals
- [FLOW.md](FLOW.md) - Request flow through the system

## Extending the Application

See [ARCHITECTURE.md](ARCHITECTURE.md) for detailed information on:
- Adding RAG (Retrieval-Augmented Generation)
- Implementing AI Agents
- Function calling capabilities
- Multi-modal support

## License

This project is open source and available for educational purposes.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

