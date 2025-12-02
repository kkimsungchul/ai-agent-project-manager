package com.ktds.aiagentprojectmanager.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

/**
 * Controller responsible for handling API endpoints that process data.
 * This controller interacts with the ChatClient to generate AI responses.
 */
@RestController
@RequestMapping("/api")
public class ApiController {
    private final ChatClient chatClient;
    private final ResourceLoader resourceLoader;

    @Autowired
    public ApiController(ChatClient.Builder chatClientBuilder, ResourceLoader resourceLoader) {
        this.chatClient = chatClientBuilder.build();
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/test")
    String generation(String userInput) {
        return this.chatClient.prompt()
                .user(userInput)
                .call()
                .content();
    }

    /**
     * Reads the content of the prompt file from the resources directory
     * @return The content of the prompt file as a string
     * @throws IOException If the file cannot be read
     */
    private String readPromptFile() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:prompt/prompt_1.txt");
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        }
    }

    /**
     * Endpoint that uses the predefined prompt from the file
     * @param userInput The user input to be processed with the prompt
     * @return The response from the AI
     */
    @PostMapping("/prompt")
    String promptGeneration(@RequestParam("userInput") String userInput) {
        try {
            String promptTemplate = readPromptFile();
            return this.chatClient.prompt()
                    .user(promptTemplate + "\n\n" + userInput)
                    .call()
                    .content();
        } catch (IOException e) {
            return "Error reading prompt file: " + e.getMessage();
        }
    }
}