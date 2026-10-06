package com.sht.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/issue")
public class IssueController {

    public enum Priority { LOW, MEDIUM, HIGH, CRITICAL }
    public enum Category { BUG, FEATURE, IMPROVEMENT, DOCUMENTATION }

    public record Issue (
        String title,
        Category category,
        Priority priority,
        String assignTo,
        String reason
    ) {}

    public record IssueRequest(String description) {}

    private final ChatClient chatClient;

    public IssueController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    @PatchMapping("/classify")
    public Issue classify(@RequestBody IssueRequest issueRequest) {
        return chatClient.prompt()
                .system("你是项目经理，负责对 Issue 进行分类和优先级评估。")
                .user("请你对这个 Issue 进行分类" + issueRequest.description)
                .call()
                .entity(Issue.class);
    }

}