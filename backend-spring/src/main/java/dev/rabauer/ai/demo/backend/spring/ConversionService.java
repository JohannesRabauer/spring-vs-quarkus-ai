package dev.rabauer.ai.demo.backend.spring;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConversionService {

    private final ChatClient chatClient;

    public ConversionService(ChatClient.Builder chatClientBuilder,
                             @Value("${spring.ai.ollama.model}") String ollamaModel) {
        this.chatClient = chatClientBuilder
                .defaultOptions(ChatOptions.builder().model(ollamaModel).build())
                .build();
    }

    public String chatSimple(String prompt) {
        String fullPrompt = String.format(
                "Be a nice, chatty conversation partner with exact knowledge of conversion rates: %s",
                prompt
        );

        ChatClient.CallResponseSpec response = chatClient
                .prompt(fullPrompt)
                .tools(new ConversionTools())
                .call();

        return response.content();
    }
}
