package com.sht.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/entity")
public class EntityController {

    record BookSummary(String title, String author, String oneLinerSummary) {}

    private final ChatClient chatClient;

    public EntityController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    // todo 结构化输出用的是泛型擦除？new ParameterizedTypeReference<>() {}
    @GetMapping
    public List<BookSummary> getBookSummary() {
        return chatClient.prompt()
                .user("列举 5 本经典的 Java 相关的书籍")
                .call()
                .entity(new ParameterizedTypeReference<>() {});
    }

}