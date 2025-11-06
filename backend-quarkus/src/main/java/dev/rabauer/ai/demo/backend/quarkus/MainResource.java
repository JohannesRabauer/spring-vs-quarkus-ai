package dev.rabauer.ai.demo.backend.quarkus;

import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class MainResource {

    @Inject
    CustomerServiceService customerServiceService;
    @Inject
    ServiceDeskService serviceDeskService;

    @POST
    @Path("/chat")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Multi<String> chat(String request) {
        Thread.startVirtualThread(() ->  serviceDeskService.logComplaint(request));
        return customerServiceService.chatSimple(request);
    }
}
