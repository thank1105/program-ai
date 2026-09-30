package com.sht.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient loveClient(ChatClient.Builder chatClinet) {
        return chatClinet.defaultSystem("你是一个会写关于爱情短语的高手，请根据用户的相关词写出对应的爱情短句。").build();
    }

    @Bean
    public ChatClient laughClient(ChatClient.Builder chatClinet) {
        return chatClinet.defaultSystem("你是一个搞笑专家，根据用户的关键词说出一个搞笑的段子，100字左右。").build();
    }
}
