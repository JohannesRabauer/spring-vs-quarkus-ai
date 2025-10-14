package dev.rabauer.ai.demo.frontend;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Route("")
public class MainView extends VerticalLayout {

    @Value("${backend.url}")
    private String backendUrl;

    public MainView() {
        setSizeFull();

        // Create SplitLayout (movable divider)
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        splitLayout.setSplitterPosition(50); // initial 50/50 split

        // Left side: Chat panel
        VerticalLayout chatLayout = new VerticalLayout();
        chatLayout.setSizeFull();
        chatLayout.setPadding(true);
        chatLayout.setSpacing(true);

        H2 chatTitle = new H2("Chat");
        TextField chatInput = new TextField("Your message");
        chatInput.setWidthFull();
        TextArea chatResponse = new TextArea("Response");
        chatResponse.setWidthFull();
        chatResponse.setHeight("100%");
        chatResponse.setReadOnly(true);
        final UI ui = UI.getCurrent();
        Button chatButton = new Button("Send", e -> {
            chatResponse.clear();
            chat(chatInput.getValue())
                    .subscribe(
                            token -> ui.access(() -> chatResponse.setValue(chatResponse.getValue() + token))
                    );
        }
        );
        chatButton.setWidthFull();
        chatInput.addKeyPressListener(Key.ENTER, e -> chatButton.click());

        chatLayout.add(chatTitle, chatInput, chatButton, chatResponse);

        // Right side: Translate panel
        VerticalLayout translateLayout = new VerticalLayout();
        translateLayout.setSizeFull();
        translateLayout.setPadding(true);
        translateLayout.setSpacing(true);

        H2 translateTitle = new H2("Translate");
        TextField translateLanguage = new TextField("Language to translate to", "German", "German");
        translateLanguage.setWidthFull();
        TextField translateInput = new TextField("Text to translate");
        translateInput.setWidthFull();
        HorizontalLayout horizontalTranslateLayout = new HorizontalLayout(translateLanguage, translateInput);
        horizontalTranslateLayout.setSizeFull();
        horizontalTranslateLayout.setPadding(false);
        horizontalTranslateLayout.setSpacing(true);
        horizontalTranslateLayout.setHeight("75px");
        TextArea translateResponse = new TextArea("Translation");
        translateResponse.setWidthFull();
        translateResponse.setHeight("100%");
        translateResponse.setReadOnly(true);
        Button translateButton = new Button("Translate", e -> {
            translateResponse.clear();
            translate(translateLanguage.getValue(), translateInput.getValue())
                    .subscribe(
                            token -> ui.access(() -> translateResponse.setValue(translateResponse.getValue() + token))
                    );
        }
        );
        translateButton.setWidthFull();
        translateInput.addKeyPressListener(Key.ENTER, e -> translateButton.click());

        translateLayout.add(translateTitle, horizontalTranslateLayout, translateButton, translateResponse);

        // Add both layouts to the SplitLayout
        splitLayout.addToPrimary(chatLayout);
        splitLayout.addToSecondary(translateLayout);

        // Add SplitLayout to main view
        add(splitLayout);
    }

    private WebClient buildRestClient() {
        return WebClient
                .builder()
                .baseUrl(backendUrl)
                .build();
    }

    private Flux<String> translate(String languageToTranslateTo, String textToTranslate) {
        return buildRestClient()
                .post()
                .uri("/translate/" + languageToTranslateTo)
                .contentType(MediaType.TEXT_PLAIN)
                .bodyValue(textToTranslate)
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .bodyToFlux(String.class);
    }


    private Flux<String> chat(String chatMessage) {
        return buildRestClient()
                .post()
                .uri("/chat")
                .contentType(MediaType.TEXT_PLAIN)
                .bodyValue(chatMessage)
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .bodyToFlux(String.class);
    }
}
