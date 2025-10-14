package dev.rabauer.ai.demo.backend.quarkus;

import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.smallrye.mutiny.Multi;

@RegisterAiService(tools = ConversionTools.class)
public interface ConversionService {
    @UserMessage("Be a nice chatty conversation partner with exact knowledge of conversion rates: {prompt}")
    Multi<String> chatSimple(String prompt);
}