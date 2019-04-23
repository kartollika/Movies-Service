package com.pau_pau.omdb_client.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class OmdbClient<T> {

    @Value("${omdb.apiKey}")
    private String apiKey;

    private WebClient webClient;

    OmdbClient() {
        webClient = WebClient.create(Endpoints.OMDB_API);
    }


    public WebClient.ResponseSpec send(LinkedMultiValueMap<String, String> args) {
        LinkedMultiValueMap<String, String> argsWithApiKey = new LinkedMultiValueMap<>();
        argsWithApiKey.add("apikey", apiKey);
        argsWithApiKey.putAll(args);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParams(argsWithApiKey).build())
                .retrieve();
    }
}

