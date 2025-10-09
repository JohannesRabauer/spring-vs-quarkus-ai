package dev.rabauer.ai.demo.backend.quarkus;

import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface TranslatingService {
    @UserMessage("You are a translator: translate the following text to {targetLanguage}: {text}")
    String translate(String targetLanguage, String text);
}