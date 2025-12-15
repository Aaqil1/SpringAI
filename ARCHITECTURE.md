# Architecture Documentation

## System Architecture Overview

This document explains the architecture, design patterns, and how to extend the application for advanced features like RAG and Agents.

## Layered Architecture

The application follows a **layered architecture pattern** with clear separation of concerns:

```
┌─────────────────────────────────────┐
│         Presentation Layer          │
│      (ChatController)               │
│  - HTTP request/response handling   │
│  - Input validation                 │
│  - Error response formatting        │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│          Service Layer              │
│       (ChatService)                 │
│  - Business logic                   │
│  - AI interaction orchestration     │
│  - Response transformation          │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│         Integration Layer            │
│      (Spring AI ChatClient)          │
│  - AI model abstraction              │
│  - API communication                │
│  - Configuration management          │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│         External Services            │
│         (OpenAI API)                │
└─────────────────────────────────────┘
```

## Component Details

### 1. Presentation Layer - ChatController

**Responsibility:** Handle HTTP requests and responses

**Key Features:**
- RESTful endpoint mapping (`@RestController`, `@RequestMapping`)
- Request validation using `@Valid` annotation
- Response entity construction
- Basic error handling

**Design Pattern:** Controller pattern (MVC)

```java
@RestController
@RequestMapping("/api/ai")
public class ChatController {
    // Constructor injection ensures immutability
    private final ChatService chatService;
    
    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@Valid @RequestBody ChatRequest request) {
        // Delegates to service layer
    }
}
```

### 2. Service Layer - ChatService

**Responsibility:** Business logic and AI interaction orchestration

**Key Features:**
- Encapsulates AI communication logic
- Creates prompts from user messages
- Transforms AI responses to DTOs
- Single Responsibility Principle

**Design Pattern:** Service pattern

```java
@Service
public class ChatService {
    private final ChatClient chatClient; // Injected by Spring
    
    public ChatResponse chat(String message) {
        // 1. Create prompt
        Prompt prompt = new Prompt(new UserMessage(message));
        
        // 2. Call AI model
        String response = chatClient.call(prompt)
            .getResult()
            .getOutput()
            .getContent();
        
        // 3. Transform to DTO
        return new ChatResponse(response);
    }
}
```

### 3. Data Transfer Objects (DTOs)

**Purpose:** Transfer data between layers without exposing internal implementation

**Benefits:**
- Type safety
- Validation constraints
- API contract definition
- Immutability (using Java records)

```java
// Request DTO
public record ChatRequest(
    @NotBlank(message = "Message cannot be blank")
    String message
) {}

// Response DTO
public record ChatResponse(String response) {}
```

### 4. Exception Handling - GlobalExceptionHandler

**Responsibility:** Centralized error handling

**Key Features:**
- Consistent error responses
- Validation error formatting
- Generic exception handling
- HTTP status code mapping

**Design Pattern:** Exception Handler pattern

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ChatResponse> handleValidationException(...) {
        // Returns 400 Bad Request
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ChatResponse> handleGenericException(...) {
        // Returns 500 Internal Server Error
    }
}
```

## Dependency Injection

The application uses **Constructor Injection** throughout:

**Benefits:**
- Immutability (final fields)
- Testability (easy to mock)
- Clear dependencies
- Compile-time safety

**Example:**
```java
@Service
public class ChatService {
    private final ChatClient chatClient; // Final = immutable
    
    // Constructor injection
    public ChatService(ChatClient chatClient) {
        this.chatClient = chatClient; // Required dependency
    }
}
```

## Spring AI Integration

### ChatClient Abstraction

Spring AI provides a `ChatClient` interface that abstracts AI model interactions:

**Benefits:**
- Provider-agnostic (can switch between OpenAI, Anthropic, etc.)
- Consistent API regardless of underlying model
- Configuration-driven model selection

**Configuration Flow:**
```
application.yml
    ↓
Spring Boot Auto-configuration
    ↓
ChatClient Bean Creation
    ↓
Injected into ChatService
```

### Configuration

```yaml
spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}  # Externalized secret
      chat:
        options:
          model: gpt-3.5-turbo     # Model selection
          temperature: 0.7         # Model parameters
```

## Extending for RAG (Retrieval-Augmented Generation)

### Architecture Changes

```
┌──────────────┐
│  Controller  │
└──────┬───────┘
       │
┌──────▼──────────┐
│  RAG Service    │
│  - Retrieval    │
│  - Context      │
│  - Generation   │
└──────┬──────────┘
       │
   ┌───┴────┬──────────────┐
   │        │              │
┌──▼───┐ ┌──▼───┐    ┌─────▼─────┐
│Vector│ │Chat  │    │Embedding  │
│Store │ │Client│    │Model      │
└──────┘ └──────┘    └───────────┘
```

### Implementation Steps

1. **Add Vector Store Dependency:**
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-pgvector-store</artifactId>
</dependency>
```

2. **Create RAG Service:**
```java
@Service
public class RagChatService {
    private final ChatClient chatClient;
    private final VectorStore vectorStore;
    
    public ChatResponse chatWithContext(String message) {
        // Step 1: Retrieve relevant documents
        List<Document> docs = vectorStore.similaritySearch(
            SearchRequest.query(message).withTopK(5)
        );
        
        // Step 2: Build context
        String context = docs.stream()
            .map(Document::getContent)
            .collect(Collectors.joining("\n\n"));
        
        // Step 3: Create enhanced prompt
        String enhancedPrompt = String.format(
            "Based on the following context:\n\n%s\n\nAnswer: %s",
            context, message
        );
        
        // Step 4: Generate response
        return chatClient.call(new Prompt(enhancedPrompt));
    }
}
```

3. **Use Advisor API (Alternative):**
```java
@Service
public class AdvisorRagService {
    public ChatResponse chat(String message) {
        return chatClient.prompt()
            .advisors(advisor -> advisor
                .name("document-retrieval")
                .input(message)
                .call(vectorStore::similaritySearch)
                .output(docs -> buildContext(docs))
            )
            .user(message)
            .call()
            .content();
    }
}
```

## Extending for AI Agents

### Agent Architecture

```
┌──────────────┐
│   Agent      │
│  Controller  │
└──────┬───────┘
       │
┌──────▼──────────┐
│  Agent Service  │
│  - Planning     │
│  - Tool Use     │
│  - Execution    │
└──────┬──────────┘
       │
   ┌───┴────┬──────────────┐
   │        │              │
┌──▼───┐ ┌──▼───┐    ┌─────▼─────┐
│Tools │ │Chat  │    │Functions  │
│      │ │Client│    │           │
└──────┘ └──────┘    └───────────┘
```

### Implementation Steps

1. **Define Functions:**
```java
@Service
public class AgentChatService {
    private final ChatClient chatClient;
    
    @Description("Get current weather for a location")
    public String getWeather(
        @Description("The city name") String city
    ) {
        // Call weather API
        return weatherService.getCurrentWeather(city);
    }
    
    @Description("Get stock price")
    public String getStockPrice(
        @Description("Stock symbol") String symbol
    ) {
        // Call stock API
        return stockService.getPrice(symbol);
    }
}
```

2. **Register Functions:**
```java
@Service
public class ToolAgentService {
    public ChatResponse executeAgent(String message) {
        return chatClient.prompt()
            .system("You are a helpful assistant with access to tools")
            .user(message)
            .functions("getWeather", "getStockPrice")
            .call()
            .content();
    }
}
```

3. **Multi-Step Agent:**
```java
@Service
public class WorkflowAgentService {
    public ChatResponse executeWorkflow(String goal) {
        // Agent can make multiple function calls
        // Spring AI handles orchestration automatically
        return chatClient.prompt()
            .system("Break down tasks and use available tools")
            .user(goal)
            .functions("searchDatabase", "callAPI", "processData")
            .call()
            .content();
    }
}
```

## Design Principles Applied

1. **Single Responsibility Principle (SRP)**
   - Each class has one reason to change
   - Controller handles HTTP, Service handles business logic

2. **Dependency Inversion Principle (DIP)**
   - Depend on abstractions (ChatClient interface)
   - Not on concrete implementations

3. **Open/Closed Principle (OCP)**
   - Open for extension (RAG, Agents)
   - Closed for modification (core structure)

4. **Interface Segregation Principle (ISP)**
   - Small, focused interfaces
   - ChatClient provides only what's needed

5. **Don't Repeat Yourself (DRY)**
   - Centralized error handling
   - Reusable service methods

## Testing Strategy

### Unit Testing
- Test service layer in isolation
- Mock ChatClient
- Test error scenarios

### Integration Testing
- Test full request flow
- Use TestContainers for vector store
- Mock external APIs

### Example Test:
```java
@SpringBootTest
class ChatServiceTest {
    @MockBean
    private ChatClient chatClient;
    
    @Autowired
    private ChatService chatService;
    
    @Test
    void testChat() {
        // Arrange
        when(chatClient.call(any())).thenReturn(mockResponse);
        
        // Act
        ChatResponse response = chatService.chat("Hello");
        
        // Assert
        assertNotNull(response);
    }
}
```

## Security Considerations

1. **API Key Management**
   - Never commit API keys
   - Use environment variables
   - Consider secret management services

2. **Input Validation**
   - Validate all inputs
   - Sanitize user messages
   - Rate limiting (future enhancement)

3. **Error Messages**
   - Don't expose internal details
   - Generic error messages to clients
   - Detailed logs server-side only

## Performance Considerations

1. **Async Processing** (Future Enhancement)
   - Use `@Async` for long-running AI calls
   - Return immediately with job ID
   - Poll for results

2. **Caching** (Future Enhancement)
   - Cache common responses
   - Use Spring Cache abstraction

3. **Connection Pooling**
   - Spring AI handles HTTP connection pooling
   - Configure timeouts appropriately

## Monitoring and Observability

### Recommended Additions:
- Spring Boot Actuator for health checks
- Micrometer for metrics
- Distributed tracing (Zipkin/Jaeger)
- Logging framework (Logback/Log4j2)

## Conclusion

This architecture provides:
- ✅ Clean separation of concerns
- ✅ Easy to test
- ✅ Easy to extend
- ✅ Production-ready patterns
- ✅ Scalable design

The layered approach makes it straightforward to add RAG, agents, or other AI capabilities without major refactoring.
