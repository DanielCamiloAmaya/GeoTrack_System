package com.PPOOII.Proyecto.ApisGoogleMaps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Component
public class Geocoder {


    private static final String GEOCODING_RESOURCE =
        "https://maps.googleapis.com/maps/api/place/findplacefromtext/json"
        + "?inputtype=textquery"
        + "&fields=formatted_address,geometry"
        + "&key=";

    // Pon aquí tu API key de Google Maps
    private static final String API_KEY = "AIzaSyBt7SBw_rUCg6MHb3rqrxopHSMJwMIdvoA";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public Geocoder() {
        this.httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Hace la petición síncrona a la API de Google y devuelve el JSON como String.
     */
    public String geocodeSync(String query) throws IOException, InterruptedException {
        String encoded = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String requestUri = GEOCODING_RESOURCE
                        + API_KEY
                        + "&input=" + encoded;

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(requestUri))
            .timeout(Duration.ofSeconds(3))
            .GET()
            .build();

        HttpResponse<String> response = httpClient.send(request,
            HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    /**
     * Parsea el JSON obtenido de geocodeSync y extrae "lat,lng".
     * @return "latitud,longitud" o null si no hay resultados.
     */
    public String getLatLng(String query) throws IOException, InterruptedException {
        String json = geocodeSync(query);
        JsonNode root = objectMapper.readTree(json);
        JsonNode candidates = root.path("candidates");
        if (!candidates.isArray() || candidates.size() == 0) {
            return null;
        }
        JsonNode loc = candidates.get(0)
                                .path("geometry")
                                .path("location");
        String lat = loc.path("lat").asText();
        String lng = loc.path("lng").asText();
        return lat + "," + lng;
    }
}

