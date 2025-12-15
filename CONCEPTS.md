# Core Concepts Explained

This document explains the fundamental concepts used in this Spring AI application.

## Table of Contents

1. [Spring AI Framework](#spring-ai-framework)
2. [ChatClient Abstraction](#chatclient-abstraction)
3. [Prompts and Messages](#prompts-and-messages)
4. [Dependency Injection](#dependency-injection)
5. [RESTful API Design](#restful-api-design)
6. [DTO Pattern](#dto-pattern)
7. [Exception Handling](#exception-handling)
8. [Configuration Management](#configuration-management)

## Spring AI Framework

### What is Spring AI?

Spring AI is a framework that provides a **portable abstraction** for interacting with AI models. It allows you to switch between different AI providers (OpenAI, Anthropic, Azure OpenAI, etc.) without changing your code.

### Key Benefits

1. **Provider Agnostic**: Write code once, use any AI provider
2. **Consistent API**: Same interface regardless of underlying model
3. **Spring Integration**: Native Spring Boot support with auto-configuration
4. **Extensible**: Easy to add custom providers or features

### Architecture

```
Your Application Code
        ↓
Spring AI Abstraction Layer
        ↓
Provider-Specific Implementation
        ↓
AI Model API (OpenAI, Anthropic, etc.)
```

**Example:**
```java
// This code works with any provider
ChatClient chatClient; // Injected by Spring
String response = chatClient.call(prompt).getResult().getOutput().getContent();
```

## ChatClient Abstraction

### What is ChatClient?

`ChatClient` is Spring AI's main interface for interacting with chat models. It provides a unified API for sending messages and receiving responses.

### How It Works

1. **Auto-Configuration**: Spring Boot automatically creates a `ChatClient` bean based on your configuration
2. **Provider Selection**: Determined by dependencies and configuration
3. **Injection**: Available for dependency injection throughout your application

### Usage Pattern

```java
@Service
public class ChatService {
    private final ChatClient chatClient; // Injected automatically
    
    public String chat(String message) {
        // Create a prompt
        Prompt prompt = new Prompt(new UserMessage(message));
        
        // Call the AI model
        ChatResponse chatResponse = chatClient.call(prompt);
        
        // Extract the content
        return chatResponse.getResult().getOutput().getContent();
    }
}
```

### ChatClient Methods

- `call(Prompt)`: Synchronous call to AI model
- `stream(Prompt)`: Streaming response (for real-time updates)
- `prompt()`: Fluent API builder (alternative approach)

## Prompts and Messages

### Prompt

A `Prompt` is a container for messages sent to the AI model. It can contain:
- User messages
- System messages
- Assistant messages (for conversation history)
- Tool/function definitions

### Message Types

1. **UserMessage**: Message from the user
   ```java
   UserMessage userMsg = new UserMessage("Hello, AI!");
   ```

2. **SystemMessage**: Instructions for the AI's behavior
   ```java
   SystemMessage systemMsg = new SystemMessage("You are a helpful assistant");
   ```

3. **AssistantMessage**: Previous AI responses (for context)
   ```java
   AssistantMessage assistantMsg = new AssistantMessage("Previous response");
   ```

### Creating Prompts

**Simple Prompt:**
```java
Prompt prompt = new Prompt(new UserMessage("What is Spring Boot?"));
```

**Prompt with System Message:**
```java
Prompt prompt = new Prompt(
    Arrays.asList(
        new SystemMessage("You are a Java expert"),
        new UserMessage("Explain Spring Boot")
    )
);
```

**Fluent API (Alternative):**
```java
String response = chatClient.prompt()
    .system("You are a helpful assistant")
    .user("Hello")
    .call()
    .content();
```

## Dependency Injection

### What is Dependency Injection?

Dependency Injection (DI) is a design pattern where objects receive their dependencies from an external source rather than creating them internally.

### Benefits

1. **Loose Coupling**: Classes don't depend on concrete implementations
2. **Testability**: Easy to mock dependencies in tests
3. **Flexibility**: Can swap implementations without code changes
4. **Single Responsibility**: Classes focus on their core logic

### Constructor Injection

**Why Constructor Injection?**

- ✅ Immutability (final fields)
- ✅ Required dependencies (fail fast if missing)
- ✅ Testability (easy to pass mocks)
- ✅ Clear dependencies

**Example:**
```java
@Service
public class ChatService {
    // Final = immutable, can't be changed after construction
    private final ChatClient chatClient;
    
    // Constructor injection - Spring provides the ChatClient
    public ChatService(ChatClient chatClient) {
        this.chatClient = chatClient; // Required dependency
    }
}
```

### How Spring Finds Dependencies

1. **Component Scanning**: Spring scans for `@Component`, `@Service`, `@Repository`, `@Controller`
2. **Bean Creation**: Creates beans and stores them in the application context
3. **Dependency Resolution**: When creating a bean, Spring looks for matching types in the context
4. **Injection**: Injects the dependencies via constructor, setter, or field

**Flow:**
```
@SpringBootApplication
    ↓
Component Scan
    ↓
Find @Service, @Controller, etc.
    ↓
Create Beans
    ↓
Resolve Dependencies
    ↓
Inject via Constructor
```

## RESTful API Design

### REST Principles

1. **Resource-Based URLs**: `/api/ai/chat` represents a resource
2. **HTTP Methods**: Use appropriate methods (POST for creating/processing)
3. **Stateless**: Each request contains all information needed
4. **JSON**: Standard data format

### Our API Design

**Endpoint:** `POST /api/ai/chat`

**Why POST?**
- We're sending data to be processed
- Not retrieving a resource (GET)
- Not updating a resource (PUT)
- Creating a new interaction (POST)

**Request Body:**
```json
{
  "message": "User's question or message"
}
```

**Response Body:**
```json
{
  "response": "AI's response"
}
```

### Controller Annotations

- `@RestController`: Combines `@Controller` + `@ResponseBody`
- `@RequestMapping`: Base path for all endpoints in the controller
- `@PostMapping`: HTTP POST method mapping
- `@RequestBody`: Deserializes JSON to Java object
- `@Valid`: Triggers validation

## DTO Pattern

### What is a DTO?

Data Transfer Object (DTO) is an object that carries data between processes or layers without exposing internal implementation details.

### Why Use DTOs?

1. **API Contract**: Defines what clients send/receive
2. **Validation**: Can add validation constraints
3. **Decoupling**: Internal models can change without affecting API
4. **Type Safety**: Compile-time checking

### Our DTOs

**ChatRequest (Input):**
```java
public record ChatRequest(
    @NotBlank(message = "Message cannot be blank")
    String message
) {}
```

**Benefits:**
- Immutable (record)
- Validation annotation
- Clear contract

**ChatResponse (Output):**
```java
public record ChatResponse(String response) {}
```

**Benefits:**
- Simple structure
- JSON serialization
- Type-safe

### Java Records

Records are a Java 17 feature that automatically generates:
- Constructor
- Getters
- `equals()` and `hashCode()`
- `toString()`

**Equivalent to:**
```java
public class ChatRequest {
    private final String message;
    
    public ChatRequest(String message) {
        this.message = message;
    }
    
    public String message() {
        return message;
    }
    
    // equals, hashCode, toString...
}
```

## Exception Handling

### Why Centralized Exception Handling?

1. **Consistency**: Same error format across all endpoints
2. **Separation of Concerns**: Controllers don't handle errors
3. **Maintainability**: Change error handling in one place
4. **User Experience**: Friendly error messages

### GlobalExceptionHandler

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    // Handles validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ChatResponse> handleValidationException(...) {
        // Returns 400 Bad Request
    }
    
    // Handles all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ChatResponse> handleGenericException(...) {
        // Returns 500 Internal Server Error
    }
}
```

### Exception Flow

```
Exception Thrown
    ↓
Spring MVC catches it
    ↓
Looks for @ExceptionHandler
    ↓
GlobalExceptionHandler handles it
    ↓
Returns ResponseEntity with error
    ↓
Client receives error response
```

### HTTP Status Codes

- **200 OK**: Successful request
- **400 Bad Request**: Validation error or invalid input
- **500 Internal Server Error**: Server-side error

## Configuration Management

### Externalized Configuration

Configuration is stored in `application.yml` instead of hardcoded in Java.

**Benefits:**
- Environment-specific values
- No code changes for different environments
- Security (secrets not in code)

### Configuration Hierarchy

Spring Boot loads configuration in this order (later overrides earlier):

1. Default properties
2. `application.yml`
3. Environment variables
4. Command-line arguments

### Our Configuration

```yaml
spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY:your-api-key-here}
      # ↑ Uses environment variable, falls back to default
      chat:
        options:
          model: gpt-3.5-turbo
          temperature: 0.7
```

### Environment Variable Usage

**Syntax:** `${VARIABLE_NAME:default-value}`

- If `OPENAI_API_KEY` exists → use it
- If not → use `your-api-key-here`

**Setting Environment Variable:**

**Linux/Mac:**
```bash
export OPENAI_API_KEY=sk-...
```

**Windows (PowerShell):**
```powershell
$env:OPENAI_API_KEY="sk-..."
```

**Windows (CMD):**
```cmd
set OPENAI_API_KEY=sk-...
```

### Configuration Properties

Spring Boot automatically binds YAML properties to Java objects:

```yaml
spring:
  ai:
    openai:
      api-key: xxx
```

Spring AI reads this and configures the `ChatClient` bean automatically.

## Key Takeaways

1. **Spring AI** provides a portable abstraction for AI models
2. **ChatClient** is the main interface for chat interactions
3. **Prompts** contain messages sent to AI models
4. **Dependency Injection** makes code testable and flexible
5. **RESTful APIs** follow standard HTTP conventions
6. **DTOs** define API contracts and enable validation
7. **Exception Handling** should be centralized
8. **Configuration** should be externalized for flexibility

## Next Steps

- Read [ARCHITECTURE.md](ARCHITECTURE.md) for design patterns
- Read [FLOW.md](FLOW.md) for request flow details
- Experiment with different AI models
- Add RAG capabilities
- Implement agent patterns

