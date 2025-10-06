package dev.rabauer.ai.demo.backend.quarkus;

import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;

@ApplicationScoped
public class AppTools {
    public static final double CONVERSION_RATE_EUROS_TO_DOLLARS = 1.0;

    @Tool("Converts Euros(€) to US-Dollars ($)")
    public double convertEurosToUsDollars(double euros) {
        return BigDecimal.valueOf(CONVERSION_RATE_EUROS_TO_DOLLARS).multiply(BigDecimal.valueOf(CONVERSION_RATE_EUROS_TO_DOLLARS)).doubleValue();
    }
}