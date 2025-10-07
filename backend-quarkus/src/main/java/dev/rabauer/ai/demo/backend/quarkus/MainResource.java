package dev.rabauer.ai.demo.backend.quarkus;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/")
public class MainResource {

    @Inject
    ConversionService conversionService;

    @Inject
    TranslatingService translatingService;

    @POST
    @Path("/chat")
    public String chat(String request) {
        return conversionService.chatSimple(request);
    }

    @POST
    @Path("/translate/{targetLanguage}")
    public String translate(@PathParam("targetLanguage") String targetLanguage, String textToTranslate) {
        return translatingService.translate(targetLanguage, textToTranslate);
    }
}
