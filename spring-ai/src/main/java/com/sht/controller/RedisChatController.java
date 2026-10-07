package com.sht.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/redis-chat")
public class RedisChatController {

    private final ChatClient chatClient;
    private final ChatMemory chatMemory;

    public RedisChatController(ChatClient.Builder builder, ChatMemory chatMemory) {
        this.chatMemory = chatMemory;
        this.chatClient = builder
                .defaultSystem("你是一个 Java 技术助手")
                .build();
    }

    @GetMapping
    // todo advisors 又是干什么的 MessageChatMemoryAdvisor 这个又是怎么用的
    public String chat(@RequestParam String message, @RequestParam String conversationId) {
        return chatClient.prompt()
                .user(message)
                .advisors(MessageChatMemoryAdvisor.builder(chatMemory)
                        .conversationId(conversationId)
                        .build())
                .call()
                .content();
    }

    @DeleteMapping("/{conversationId}")
    public void clearHistory(@PathVariable String conversationId) {
        chatMemory.clear(conversationId);
    }

    @GetMapping(value = "/sse",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> sseChat(@RequestParam String message, @RequestParam String conversationId) {
        return chatClient.prompt()
                .user(message)
                .advisors(MessageChatMemoryAdvisor.builder(chatMemory)
                        .conversationId(conversationId)
                        .build())
                .stream()
                .content();
    }
}