package dev.rabauer.ai.demo.backend.quarkus;

import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;

@RegisterAiService(tools = LoggingTool.class)
@ApplicationScoped
public interface ServiceDeskService {
    @UserMessage("Use the provided tool to log the following complaint: {prompt}")
    String logComplaint(String prompt);
}