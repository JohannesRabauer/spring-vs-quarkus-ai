package dev.rabauer.ai.demo.backend.spring;

import dev.rabauer.ai.demo.backend.spring.db.UserComplaintRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ServiceDeskService {

    private final ChatClient chatClient;
    private final UserComplaintRepository repository;

    public ServiceDeskService(
            ChatClient.Builder chatClientBuilder,
            UserComplaintRepository repository
    ) {
        this.chatClient = chatClientBuilder.build();
        this.repository = repository;
    }

    public String logComplaint(String prompt) {
        String fullPrompt = String.format(
                "Use the provided tool to log the following complaint: %s",
                prompt
        );

        ChatClient.CallResponseSpec response = chatClient
                .prompt(fullPrompt)
                .tools(new LoggingTool(repository))
                .call();

        return response.content();
    }
}
