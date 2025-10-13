package dev.rabauer.ai.demo.backend.spring;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TranslatingService {

    private final ChatClient chatClient;

    @Autowired
    public TranslatingService(
            ChatClient.Builder chatClientBuilder,
            @Value("${spring.ai.ollama.model}") String ollamaModel
    ) {
        this.chatClient = chatClientBuilder
                .defaultOptions(ChatOptions.builder().model(ollamaModel).build())
                .build();
    }

    public String translate(String targetLanguage, String text) {
        String prompt = String.format(
                "You are a translator. Translate the following text to %s: %s",
                targetLanguage, text
        );

        ChatClient.CallResponseSpec response = chatClient
                .prompt(prompt)
                .call();

        return response.content();
    }
}