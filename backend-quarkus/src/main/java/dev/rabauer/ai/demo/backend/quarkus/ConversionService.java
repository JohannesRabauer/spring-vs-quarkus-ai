package dev.rabauer.ai.demo.backend.quarkus;

import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService(tools = ConversionTools.class)
public interface ConversionService {
    @UserMessage("Chat: {prompt}")
    String chatSimple(String prompt);
}