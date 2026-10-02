package com.sht.controller;

import com.sht.service.PromptService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prompt")
public class PromptServiceController {
    private final PromptService promptService;

    public PromptServiceController(PromptService promptService) {
        this.promptService = promptService;
    }

    @GetMapping
    public String codeReview(@RequestParam String language, @RequestParam Integer years, @RequestParam String code) {
        return promptService.codeReview(language, years, code);
    }
}
