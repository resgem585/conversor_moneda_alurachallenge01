package org.conversor;

import org.conversor.models.CurrencyDTO;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) {
        // Instanciar y cargar las propiedades del proyecto
        ProjectProperties properties = new ProjectProperties();
        properties.loadProperties();

        // Crear un lector de entrada
        Scanner inputReader = new Scanner(System.in);

        // Mostrar mensaje al usuario para seleccionar la moneda base
        System.out.println("Selecciona la moneda base:");
        System.out.println("1. Pesos Mexicanos (MXN)");
        System.out.println("2. Soles Peruanos (PEN)");
        System.out.println("3. Pesos Colombianos (COP)");
        System.out.println("4. Euros (EUR)");
        System.out.println("5. Dólares Estadounidenses (USD)");

        // Leer la opción de la moneda base del usuario
        int baseOption = inputReader.nextInt();

        String baseCurrency;
        switch (baseOption) {
            case 1:
                baseCurrency = "MXN"; // Pesos Mexicanos
                break;
            case 2:
                baseCurrency = "PEN"; // Soles Peruanos
                break;
            case 3:
                baseCurrency = "COP"; // Pesos Colombianos
                break;
            case 4:
                baseCurrency = "EUR"; // Euros
                break;
            case 5:
                baseCurrency = "USD"; // Dólares Estadounidenses
                break;
            default:
                System.out.println("Opción inválida. Seleccionando Dólares Estadounidenses (USD) como moneda base por defecto.");
                baseCurrency = "USD"; // Valor por defecto en caso de opción inválida
                break;
        }

        // Mostrar mensaje al usuario para seleccionar la moneda de destino
        System.out.println("Selecciona la moneda de destino:");
        System.out.println("1. Pesos Mexicanos (MXN)");
        System.out.println("2. Soles Peruanos (PEN)");
        System.out.println("3. Pesos Colombianos (COP)");
        System.out.println("4. Euros (EUR)");
        System.out.println("5. Dólares Estadounidenses (USD)");

        // Leer la opción de la moneda de destino del usuario
        int targetOption = inputReader.nextInt();

        String targetCurrency;
        switch (targetOption) {
            case 1:
                targetCurrency = "MXN"; // Pesos Mexicanos
                break;
            case 2:
                targetCurrency = "PEN"; // Soles Peruanos
                break;
            case 3:
                targetCurrency = "COP"; // Pesos Colombianos
                break;
            case 4:
                targetCurrency = "EUR"; // Euros
                break;
            case 5:
                targetCurrency = "USD"; // Dólares Estadounidenses
                break;
            default:
                System.out.println("Opción inválida. Seleccionando Dólares Estadounidenses (USD) como moneda de destino por defecto.");
                targetCurrency = "USD"; // Valor por defecto en caso de opción inválida
                break;
        }

        // Solicitar al usuario que ingrese el monto a convertir
        System.out.println("Ingresa el monto a convertir:");
        double amount = inputReader.nextDouble();

        // Construir la URL de la API
        String address = "https://v6.exchangerate-api.com/v6/" + properties.getApiKey() + "/latest/" + baseCurrency;

        // Crear un cliente HTTP y realizar la solicitud GET
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(address))
                .build();

        try {
            // Realizar la solicitud HTTP y obtener la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Convertir la respuesta JSON en un objeto CurrencyDTO utilizando Gson
            Gson gson = new Gson();
            CurrencyDTO currency = gson.fromJson(response.body(), CurrencyDTO.class);

            // Obtener el mapa de tasas de conversión de CurrencyDTO
            Map<String, Double> conversionRates = currency.getConversionRates();

            // Obtener la tasa de conversión de la moneda de destino
            Double conversionRate = conversionRates.get(targetCurrency);

            // Calcular el monto convertido
            double convertedAmount = amount * conversionRate;

            // Mostrar el resultado al usuario
            System.out.println(amount + " " + baseCurrency + " equivale a " + convertedAmount + " " + targetCurrency);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
