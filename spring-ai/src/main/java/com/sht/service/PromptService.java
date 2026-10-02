package com.sht.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class PromptService {
    private final ChatClient chatClient;

    public PromptService(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    public String codeReview(String language, Integer years, String code) {
        PromptTemplate template = new PromptTemplate(new ClassPathResource("prompts/code-review.st"));
        Prompt prompt = template.create(Map.of("language", language, "years", years, "code", code));
        log.info("prompt==={}",prompt);
        return chatClient.prompt(prompt).call().content();
    }
}
