package com.sht.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    private final ChatClient chatClient;
    private final ChatModel chatModel;

    // 构造器注入
    public DemoController(ChatClient.Builder chatClientBuilder, ChatModel chatModel) {
        this.chatClient = chatClientBuilder.build();
        this.chatModel = chatModel;
    }

    @GetMapping()
    public String hello(@RequestParam String content) {
        return chatClient.prompt()
                .user(content)
                .call().content();
    }

    // todo: chatmodel 和 chatclient 的区别
    public void compare() {
        // ChatModel 的原始用法——繁琐
        Prompt prompt = new Prompt(new UserMessage("你好"));
        ChatResponse response = chatModel.call(prompt);
        String content1 = response.getResult().getOutput().getText();
        // ChatClient 的用法——简洁
        String content2 = chatClient.prompt()
                .user("你好")
                .call()
                .content();
    }

}
