package dev.rabauer.ai.demo.backend.quarkus;

import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService(tools = AppTools.class)
public interface AssistantService {

    @UserMessage("You are a translator: translate the following text to {targetLanguage}: {text}")
    String translate(String targetLanguage, String text);

    @UserMessage("Chat: {prompt}")
    String chatSimple(String prompt);
}