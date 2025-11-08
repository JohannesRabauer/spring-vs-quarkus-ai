package dev.rabauer.ai.demo.backend.spring;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/")
public class MainController {

    private final ServiceDeskService serviceDeskService;
    private final CustomerServiceService customerServiceService;

    @Autowired
    public MainController(ServiceDeskService serviceDeskService, CustomerServiceService customerServiceService) {
        this.serviceDeskService = serviceDeskService;
        this.customerServiceService = customerServiceService;
    }

    @PostMapping("/chat")
    public Flux<String> chat(@RequestBody String request) {
        Thread.startVirtualThread(() ->  serviceDeskService.logComplaint(request));
        return customerServiceService.chatSimple(request);
    }
}
