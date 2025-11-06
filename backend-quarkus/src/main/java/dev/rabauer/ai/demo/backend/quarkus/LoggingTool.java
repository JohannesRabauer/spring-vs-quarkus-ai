package dev.rabauer.ai.demo.backend.quarkus;

import dev.langchain4j.agent.tool.Tool;
import dev.rabauer.ai.demo.backend.quarkus.db.UserComplaint;
import dev.rabauer.ai.demo.backend.quarkus.db.UserComplaintRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.Instant;

@ApplicationScoped
public class LoggingTool {
    @Inject
    UserComplaintRepository repository;

    @Tool("""
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
            
            """
    )
    @Transactional
    public void handleInterpretation(
            String username,
            String request,
            int mood
    ) {
        this.repository.persist(new UserComplaint(Instant.now(), username, request, mood));
    }
}