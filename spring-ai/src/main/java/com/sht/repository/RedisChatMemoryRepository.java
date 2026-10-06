package com.sht.repository;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
// todo ChatMemoryRepository 纯存储层，负责读写所有消息，不做裁剪
public class RedisChatMemoryRepository implements ChatMemoryRepository {

    private static final String KEY_PREFIX = "chat:memory:";
    private static final int TTL_DAYS = 7;

    private final RedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public RedisChatMemoryRepository(RedisTemplate redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public record MessageRecord(String role, String content) {}

    @Override
    public List<String> findConversationIds() {
        Set<String> keys = redisTemplate.keys(KEY_PREFIX + "*");
        return keys.stream()
                .map(key -> key.substring(KEY_PREFIX.length()))
                .toList();
    }

    @Override
    public List<Message> findByConversationId(String conversationId) {
        String key = KEY_PREFIX + conversationId;
        List<String> redisMessages = redisTemplate.opsForList().range(key, 0, -1);
        List<Message> messages = new ArrayList<>();
        if (redisMessages == null) return messages;
        for (String raw : redisMessages) {
            try {
                MessageRecord messageRecord = objectMapper.readValue(raw, MessageRecord.class);
                if ("USER".equals(messageRecord.role())) {
                    messages.add(new UserMessage(messageRecord.content()));
                } else if ("ASSISTANT".equals(messageRecord.role())) {
                    messages.add(new AssistantMessage(messageRecord.content()));
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return messages;
    }

    @Override
    public void saveAll(String conversationId, List<Message> messages) {
        String key = KEY_PREFIX + conversationId;
        // 先删除旧数据
        redisTemplate.delete(key);
        for (Message message: messages) {
            try {
                MessageRecord messageRecord = new MessageRecord(message.getMessageType().name(), message.getText());
                log.info("messageRecord----:{}",messageRecord);
                // todo objectMapper 是干什么的，可以换成其它类吗
                String writeValueAsString = objectMapper.writeValueAsString(messageRecord);
                log.info("writeValueAsString---{}",writeValueAsString);
                redisTemplate.opsForList().rightPush(key, writeValueAsString);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        redisTemplate.expire(key,TTL_DAYS, TimeUnit.DAYS);
    }

    @Override
    public void deleteByConversationId(String conversationId) {
        String key = KEY_PREFIX + conversationId;
        redisTemplate.delete(key);
    }
}
