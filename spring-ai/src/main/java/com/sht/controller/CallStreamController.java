package com.sht.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/stream")
public class CallStreamController {
    private final ChatClient loveClient;
    private final ChatClient laughClient;

    public CallStreamController(@Qualifier("loveClient") ChatClient loveClient, @Qualifier("laughClient") ChatClient laughClient) {
        this.loveClient = loveClient;
        this.laughClient = laughClient;
    }

    // todo: 流式调用 call vs stream
    @GetMapping("/stream")
    public Flux<String> hello(@RequestParam String content) {
        return loveClient.prompt()
                .user(content)
                .stream().content();
    }
}
