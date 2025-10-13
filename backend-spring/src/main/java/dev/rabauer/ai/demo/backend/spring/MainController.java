package dev.rabauer.ai.demo.backend.spring;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class MainController {

    private final ConversionService conversionService;
    private final TranslatingService translatingService;

    @Autowired
    public MainController(ConversionService conversionService, TranslatingService translatingService) {
        this.conversionService = conversionService;
        this.translatingService = translatingService;
    }

    @PostMapping("/chat")
    public String chat(@RequestBody String request) {
        return conversionService.chatSimple(request);
    }

    @PostMapping("/translate/{targetLanguage}")
    public String translate(@PathVariable String targetLanguage, @RequestBody String textToTranslate) {
        return translatingService.translate(targetLanguage, textToTranslate);
    }
}
