package org.conversor.models;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class CurrencyDTO {
    @SerializedName("conversion_rates")
    private Map<String, Double> conversionRates; // Mapa de tasas de conversión

    public Map<String, Double> getConversionRates() {
        return conversionRates;
    }

    public void setConversionRates(Map<String, Double> conversionRates) {
        this.conversionRates = conversionRates;
    }
}
