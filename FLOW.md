# Request Flow Documentation

This document explains in detail how a request flows through the Spring AI Chat application from the moment it arrives until a response is sent back.

## Complete Request Flow

```
Client Request
    ↓
HTTP Server (Port 8080)
    ↓
Spring MVC DispatcherServlet
    ↓
ChatController.chat()
    ↓
Validation (@Valid)
    ↓
ChatService.chat()
    ↓
ChatClient.call()
    ↓
OpenAI API
    ↓
Response flows back
    ↓
Client receives JSON
```

## Step-by-Step Flow

### Step 1: Client Sends HTTP Request

**Request:**
```http
POST /api/ai/chat HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "message": "What is Spring Boot?"
}
```

**What Happens:**
- HTTP client (curl, Postman, browser) sends POST request
- Request includes JSON body with user's message
- Headers specify content type as JSON

### Step 2: Spring Boot HTTP Server Receives Request

**Component:** Embedded Tomcat Server (configured in `application.yml`)

**Configuration:**
```yaml
server:
  port: 8080
```

**What Happens:**
- Tomcat listens on port 8080
- Receives HTTP request
- Parses headers and body
- Passes to Spring MVC framework

### Step 3: Spring MVC DispatcherServlet Routes Request

**Component:** `DispatcherServlet` (Spring MVC core)

**What Happens:**
1. **URL Matching**: Matches `/api/ai/chat` to controller
2. **Method Matching**: Matches POST method
3. **Handler Selection**: Finds `ChatController.chat()` method
4. **Parameter Binding**: Binds JSON body to `ChatRequest` object

**Code Reference:**
```java
@PostMapping("/chat")
public ResponseEntity<ChatResponse> chat(@Valid @RequestBody ChatRequest request)
```

**Process:**
- `@PostMapping("/chat")` → matches POST /api/ai/chat
- `@RequestBody` → deserializes JSON to `ChatRequest`
- `@Valid` → triggers validation

### Step 4: Request Validation

**Component:** Jakarta Bean Validation

**What Happens:**
1. Spring validates `ChatRequest` object
2. Checks `@NotBlank` constraint on `message` field
3. If invalid → throws `MethodArgumentNotValidException`
4. If valid → continues to controller method

**Validation Logic:**
```java
public record ChatRequest(
    @NotBlank(message = "Message cannot be blank")
    String message
) {}
```

**Possible Outcomes:**

**Valid Request:**
```json
{"message": "Hello"}  ✅ Continues
```

**Invalid Request:**
```json
{"message": ""}  ❌ Validation fails
{"message": null}  ❌ Validation fails
{}  ❌ Validation fails
```

**If Validation Fails:**
- Exception caught by `GlobalExceptionHandler`
- Returns 400 Bad Request
- Response: `{"response": "Validation error: Message cannot be blank"}`

### Step 5: Controller Method Execution

**Component:** `ChatController.chat()`

**Code:**
```java
@PostMapping("/chat")
public ResponseEntity<ChatResponse> chat(@Valid @RequestBody ChatRequest request) {
    try {
        ChatResponse response = chatService.chat(request.message());
        return ResponseEntity.ok(response);
    } catch (Exception e) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ChatResponse("Error: " + e.getMessage()));
    }
}
```

**What Happens:**
1. Extracts `message` from validated `ChatRequest`
2. Calls `chatService.chat(request.message())`
3. Waits for service to return `ChatResponse`
4. Wraps response in `ResponseEntity` with HTTP 200 status
5. Returns to Spring MVC

**Error Handling:**
- If service throws exception → caught by try-catch
- Returns HTTP 500 with error message

### Step 6: Service Layer Processing

**Component:** `ChatService.chat()`

**Code:**
```java
public ChatResponse chat(String message) {
    // Create a prompt with the user message
    Prompt prompt = new Prompt(new UserMessage(message));
    
    // Call the AI model and get the response
    String response = chatClient.call(prompt)
        .getResult()
        .getOutput()
        .getContent();
    
    return new ChatResponse(response);
}
```

**What Happens:**
1. **Create Prompt**: Wraps user message in `UserMessage` and `Prompt`
2. **Call AI Model**: Invokes `chatClient.call(prompt)`
3. **Extract Content**: Gets text response from AI
4. **Create DTO**: Wraps response in `ChatResponse`
5. **Return**: Returns to controller

**Prompt Creation:**
```java
UserMessage userMsg = new UserMessage("What is Spring Boot?");
Prompt prompt = new Prompt(userMsg);
```

### Step 7: Spring AI ChatClient Processing

**Component:** `ChatClient` (Spring AI abstraction)

**What Happens:**
1. **Reads Configuration**: Gets API key and model from `application.yml`
2. **Creates HTTP Request**: Builds request to OpenAI API
3. **Sends Request**: HTTP POST to `https://api.openai.com/v1/chat/completions`
4. **Waits for Response**: Receives JSON response from OpenAI
5. **Parses Response**: Extracts AI-generated text
6. **Returns ChatResponse**: Wraps in Spring AI response object

**Configuration Used:**
```yaml
spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
      chat:
        options:
          model: gpt-3.5-turbo
          temperature: 0.7
```

**HTTP Request to OpenAI:**
```http
POST https://api.openai.com/v1/chat/completions
Authorization: Bearer sk-...
Content-Type: application/json

{
  "model": "gpt-3.5-turbo",
  "messages": [
    {"role": "user", "content": "What is Spring Boot?"}
  ],
  "temperature": 0.7
}
```

**OpenAI Response:**
```json
{
  "choices": [{
    "message": {
      "content": "Spring Boot is a framework..."
    }
  }]
}
```

### Step 8: Response Extraction

**Component:** Spring AI Response Parsing

**What Happens:**
```java
ChatResponse chatResponse = chatClient.call(prompt);
// chatResponse structure:
//   getResult() → ChatGeneration
//     getOutput() → AssistantMessage
//       getContent() → String
```

**Extraction Chain:**
```
ChatResponse
  ↓ getResult()
ChatGeneration
  ↓ getOutput()
AssistantMessage
  ↓ getContent()
String ("Spring Boot is a framework...")
```

### Step 9: Response Flows Back Through Layers

**Flow:**
```
ChatService
  ↓ returns ChatResponse
ChatController
  ↓ returns ResponseEntity<ChatResponse>
Spring MVC
  ↓ serializes to JSON
HTTP Response
```

**What Happens:**
1. `ChatService` returns `ChatResponse("Spring Boot is a framework...")`
2. `ChatController` wraps in `ResponseEntity.ok(response)`
3. Spring MVC serializes `ChatResponse` to JSON
4. HTTP response sent to client

### Step 10: Client Receives Response

**HTTP Response:**
```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "response": "Spring Boot is a framework that simplifies the development of Spring applications..."
}
```

## Error Flow Examples

### Example 1: Validation Error

```
Request: {"message": ""}
    ↓
Validation fails (@NotBlank)
    ↓
MethodArgumentNotValidException thrown
    ↓
GlobalExceptionHandler.handleValidationException()
    ↓
Response: 400 Bad Request
{
  "response": "Validation error: Message cannot be blank"
}
```

### Example 2: Missing API Key

```
Request: {"message": "Hello"}
    ↓
ChatService.chat()
    ↓
ChatClient.call()
    ↓
OpenAI API call fails (401 Unauthorized)
    ↓
Exception thrown
    ↓
Controller try-catch or GlobalExceptionHandler
    ↓
Response: 500 Internal Server Error
{
  "response": "Error processing request: Unauthorized"
}
```

### Example 3: Network Error

```
Request: {"message": "Hello"}
    ↓
ChatService.chat()
    ↓
ChatClient.call()
    ↓
Network timeout
    ↓
Exception thrown
    ↓
GlobalExceptionHandler.handleGenericException()
    ↓
Response: 500 Internal Server Error
{
  "response": "An error occurred: Connection timeout"
}
```

## Data Transformation Flow

### Request Transformation

```
JSON String
  ↓ @RequestBody
ChatRequest DTO
  ↓ .message()
String
  ↓
UserMessage
  ↓
Prompt
```

### Response Transformation

```
String (from AI)
  ↓
ChatResponse DTO
  ↓ @ResponseBody
JSON String
```

## Threading Model

**Synchronous Flow:**
- Each request handled in its own thread
- Thread blocks while waiting for OpenAI API
- Response sent when AI call completes

**Thread Pool:**
- Spring Boot uses embedded Tomcat thread pool
- Default: 200 threads
- Each HTTP request gets a thread

**Future Enhancement (Async):**
```java
@Async
public CompletableFuture<ChatResponse> chat(String message) {
    // Non-blocking AI call
}
```

## Performance Considerations

### Timing Breakdown (Example)

```
Total Request Time: ~2-5 seconds
├── HTTP Processing: ~1ms
├── Validation: ~1ms
├── Service Logic: ~1ms
├── OpenAI API Call: ~2-5 seconds (network + AI processing)
└── Response Serialization: ~1ms
```

### Bottlenecks

1. **OpenAI API Call**: 99% of request time
   - Network latency
   - AI model processing time
   - Solution: Async processing (future)

2. **Serialization**: Minimal impact
   - JSON parsing/generation is fast
   - DTOs are lightweight

## Logging Flow

**Current Logging:**
```yaml
logging:
  level:
    com.example: DEBUG
    org.springframework.ai: DEBUG
```

**What Gets Logged:**
- HTTP request received
- Controller method called
- Service method called
- OpenAI API request/response
- Errors and exceptions

## Summary

1. **Client** → HTTP POST with JSON
2. **Server** → Receives on port 8080
3. **DispatcherServlet** → Routes to controller
4. **Validation** → Checks request validity
5. **Controller** → Extracts message, calls service
6. **Service** → Creates prompt, calls ChatClient
7. **ChatClient** → Sends to OpenAI API
8. **OpenAI** → Processes, returns response
9. **ChatClient** → Parses response
10. **Service** → Extracts content, creates DTO
11. **Controller** → Wraps in ResponseEntity
12. **Spring MVC** → Serializes to JSON
13. **Client** → Receives HTTP 200 with JSON

This flow ensures:
- ✅ Type safety at each layer
- ✅ Proper error handling
- ✅ Clean separation of concerns
- ✅ Testable components

