package dev.rabauer.ai.demo.backend.quarkus;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class MainResource {

    @Inject
    AssistantService assistantService;

    @POST
    @Path("/chat")
    public String chat(String request) {
        return assistantService.chatSimple(request);
    }

    @POST
    @Path("/translate")
    @Consumes(MediaType.APPLICATION_JSON)
    public String translate(TranslateRequest request) {
        return assistantService.translate(request.targetLanguage, request.textToTranslate);
    }

    public record TranslateRequest(String targetLanguage, String textToTranslate){}
}
