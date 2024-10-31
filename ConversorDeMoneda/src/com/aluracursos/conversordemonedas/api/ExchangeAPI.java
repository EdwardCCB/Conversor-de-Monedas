package com.aluracursos.conversordemonedas.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ExchangeAPI {

    private static final String API_KEY = "688bb9ef1decf7e72bd009b9";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    public double getExchangeRate(String fromCurrency, String toCurrency) throws Exception {
        String urlStr = BASE_URL + API_KEY + "/pair/" + fromCurrency + "/" + toCurrency;
        URI uri = URI.create(urlStr);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

            if (jsonObject.has("conversion_rate")) {
                return jsonObject.get("conversion_rate").getAsDouble();
            } else {
                throw new RuntimeException("La respuesta no contiene el campo 'conversion_rate'");
            }
        } else {
            throw new RuntimeException("Failed to get exchange rate. HTTP error code: " + response.statusCode() +
                    ". Respuesta: " + response.body());
        }
    }
}