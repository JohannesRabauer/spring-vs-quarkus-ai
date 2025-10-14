package dev.rabauer.ai.demo.backend.quarkus;

import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestStreamElementType;

@Path("/")
public class MainResource {

    @Inject
    ConversionService conversionService;

    @Inject
    TranslatingService translatingService;

    @POST
    @Path("/chat")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Multi<String> chat(String request) {
        return conversionService.chatSimple(request);
    }

    @POST
    @Path("/translate/{targetLanguage}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Multi<String> translate(@PathParam("targetLanguage") String targetLanguage, String textToTranslate) {
        return translatingService.translate(targetLanguage, textToTranslate);
    }
}
