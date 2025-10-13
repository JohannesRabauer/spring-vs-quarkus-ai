package dev.rabauer.ai.demo.backend.spring;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.math.BigDecimal;

public class ConversionTools {

    public static final double CONVERSION_RATE_EUROS_TO_DOLLARS = 2.0;

    @Tool(description = "Converts Euros (€) to US Dollars ($).")
    public double convertEurosToUsDollars(
            @ToolParam(description = "Amount of euros to convert to us dollars") double euros
    ) {
        return BigDecimal.valueOf(euros)
                .multiply(BigDecimal.valueOf(CONVERSION_RATE_EUROS_TO_DOLLARS))
                .doubleValue();
    }
}