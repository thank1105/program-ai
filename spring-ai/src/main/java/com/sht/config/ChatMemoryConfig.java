package com.sht.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sht.repository.RedisChatMemoryRepository;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class ChatMemoryConfig {

    @Bean
    // todo MessageWindowChatMemory 和 ChatMemory 是干什么的？这几个方法有什么作用
    public ChatMemory chatMemory(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        RedisChatMemoryRepository memoryRepository = new RedisChatMemoryRepository(redisTemplate, objectMapper);
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(memoryRepository)
                .maxMessages(20)
                .build();
    }
}