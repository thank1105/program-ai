package com.sht.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/multi")
public class MultiChatController {

    private final ChatClient loveClient;
    private final ChatClient laughClient;

    // todo: 不同业务场景可以注册不同的 ChatClient Bean
    public MultiChatController(@Qualifier("loveClient") ChatClient loveClient, @Qualifier("laughClient") ChatClient laughClient) {
        this.loveClient = loveClient;
        this.laughClient = laughClient;
    }

    @GetMapping("/love")
    public String loveClient(@RequestParam String content) {
        return loveClient.prompt()
                .user(content).call().content();
    }

    @GetMapping("/laugh")
    public String laughClient(@RequestParam String content) {
        return laughClient.prompt()
                .user(content).call().content();
    }
}



