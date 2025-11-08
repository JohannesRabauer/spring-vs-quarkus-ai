package dev.rabauer.ai.demo.backend.spring;

import dev.rabauer.ai.demo.backend.spring.db.UserComplaint;
import dev.rabauer.ai.demo.backend.spring.db.UserComplaintRepository;
import org.springframework.ai.tool.annotation.Tool;

import java.time.Instant;

public class LoggingTool {

    private UserComplaintRepository repository;

    public LoggingTool(UserComplaintRepository repository) {
        this.repository = repository;
    }

    @Tool(description = """
            Use this tool when you have interpreted a user's message and want to communicate your understanding
            in a structured way. Extract the following three pieces of information from the message:
            
            • username: the name, handle, or identifier mentioned by the user. 
              If none is mentioned, use "unknown".
            
            • request: a short natural-language summary (one concise phrase) describing
              what the user is asking for or trying to accomplish.
            
            • mood: an integer from 0 to 100 representing the emotional tone.
              0 means calm, polite, or happy; 100 means angry, rude, or aggressive.
              Choose numbers in between for nuanced cases (e.g. 30 = slightly impatient, 70 = frustrated).
              If no mood is provided, guess as accurate as possible.
            
            Call this tool once per user message to return your interpretation.
            Do not include any explanation or reasoning in the output—only populate the parameters.
            
            """)
    public void handleInterpretation(
            String username,
            String request,
            Integer mood
    ) {
        repository.save(new UserComplaint(Instant.now(), username, request, mood));
    }
}