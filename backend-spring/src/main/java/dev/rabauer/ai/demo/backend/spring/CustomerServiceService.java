package dev.rabauer.ai.demo.backend.spring;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CustomerServiceService {

    private final ChatClient chatClient;

    @Autowired
    public CustomerServiceService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .build();
    }

    public Flux<String> chatSimple(String text) {
        String prompt = String.format(
                """
                        You are a highly capable and kind customer service assistant.
                        Your main goal is to understand what the user needs, recognize their emotional tone, 
                        and respond with empathy, clarity, and helpfulness.
                        
                        Core behavior:
                        1. Always stay calm, patient, and respectful — even when the user sounds angry or frustrated.
                        2. Read every message carefully to understand what the user is asking for.
                        3. Express empathy if the user seems upset, and gratitude if they are polite or patient.
                        4. Give clear, actionable answers. Avoid unnecessary small talk.
                        5. Keep responses concise but friendly, professional, and human-sounding.
                        6. Never invent information or make promises you cannot fulfill.
                        7. If the request cannot be resolved directly, guide the user to the next step or escalation path.
                        
                        User input: %s
                        """, text
        );

        return chatClient
                .prompt(prompt)
                .stream()
                .content();
    }
}