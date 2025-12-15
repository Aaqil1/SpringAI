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

For the complete architecture documentation, please see the full content in the repository. This includes detailed explanations of:
- Component responsibilities
- Design patterns used
- Dependency injection strategy
- RAG implementation guide
- Agent implementation guide
- Testing strategies
- Security considerations

