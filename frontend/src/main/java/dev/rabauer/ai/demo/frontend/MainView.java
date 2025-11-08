package dev.rabauer.ai.demo.frontend;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import dev.rabauer.ai.demo.frontend.db.UserComplaint;
import dev.rabauer.ai.demo.frontend.db.UserComplaintRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Route("")
public class MainView extends VerticalLayout {

    @Autowired
    UserComplaintRepository userComplaintRepository;

    @Value("${backend.url}")
    private String backendUrl;

    private ScheduledExecutorService executor;
    private ScheduledFuture<?> task;
    private Grid<UserComplaint> userComplaintGrid = new Grid<>(UserComplaint.class, false);
    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public MainView() {
        setSizeFull();

        // Create SplitLayout (movable divider)
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        splitLayout.setSplitterPosition(50); // initial 50/50 split

        // Add both layouts to the SplitLayout
        splitLayout.addToPrimary(getChatLayout());
        splitLayout.addToSecondary(getLogLayout());

        // Add SplitLayout to main view
        add(splitLayout);

        addAttachListener(e -> startUpdating());
        addDetachListener(e -> stopUpdating());
    }

    @NotNull
    private VerticalLayout getChatLayout() {
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
        return chatLayout;
    }

    @NotNull
    private VerticalLayout getLogLayout() {
        VerticalLayout logLayout = new VerticalLayout();
        logLayout.setSizeFull();
        logLayout.setPadding(true);
        logLayout.setSpacing(true);

        H2 logTitle = new H2("Logged Entries");
        userComplaintGrid.addColumn(MainView::formatTime)
                .setHeader("Timestamp")
                .setAutoWidth(true)
                .setResizable(true)
                .setFlexGrow(0);

        userComplaintGrid.addColumn(UserComplaint::getUsername)
                .setHeader("Username")
                .setAutoWidth(true)
                .setResizable(true)
                .setFlexGrow(0);

        userComplaintGrid.addColumn(UserComplaint::getRequest)
                .setHeader("Request")
                .setResizable(true)
                .setAutoWidth(true)
                .setFlexGrow(1);

        userComplaintGrid.addColumn(new ComponentRenderer<>(complaint -> {
                    int mood = Math.max(0, Math.min(100, complaint.getMood()));
                    String color = "hsl(" + (120 - (mood * 1.2)) + ", 80%, 45%)"; // green to red
                    String bar = """
                                <div style='width:100px;height:10px;background:#eee;border-radius:4px;overflow:hidden;'>
                                  <div style='width:%d%%;height:100%%;background:%s;'></div>
                                </div>
                            """.formatted(mood, color);
                    return new Html(bar);
                }))
                .setHeader("Mood")
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setTextAlign(ColumnTextAlign.END);

        userComplaintGrid.setSizeFull();

        logLayout.add(logTitle, userComplaintGrid);
        return logLayout;
    }

    @Nullable
    private static Object formatTime(UserComplaint user) {
        return user.getTimestamp() == null
                ? null
                : DATE_TIME_FORMATTER.format(user.getTimestamp().atZone(ZoneId.systemDefault()));
    }

    private WebClient buildRestClient() {
        return WebClient
                .builder()
                .baseUrl(backendUrl)
                .build();
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

    private void startUpdating() {
        if (executor == null || executor.isShutdown()) {
            executor = Executors.newSingleThreadScheduledExecutor();
        }
        UI ui = getUI().orElse(null);
        if (ui == null) return;

        task = executor.scheduleAtFixedRate(() -> {
            ui.access(() -> {
                this.userComplaintGrid.setItems(this.userComplaintRepository.findAll());
            });
        }, 0, 2, TimeUnit.SECONDS);
    }

    private void stopUpdating() {
        if (task != null) task.cancel(true);
        if (executor != null) executor.shutdownNow();
    }
}
