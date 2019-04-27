package com.pau_pau.omdb_client.http;

import com.google.gson.Gson;
import com.pau_pau.omdb_client.models.FilmFromOmdb;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class OmdbClient {

    @Value("${omdb.apiKey}")
    private String apiKey;

    @Value("${tmdb.apiKey}")
    private String tApiKey;

    private RestTemplate webClient;
    private Gson gson;

    OmdbClient() {
        webClient = new RestTemplate();
        gson = new Gson();
    }


    public FilmFromOmdb getFilmOmdb(String id) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(Endpoints.OMDB_API + "/")
                .queryParam("i", id)
                .queryParam("apikey", apiKey);

        ResponseEntity<String> exchange = webClient.exchange(builder.toUriString(), HttpMethod.GET, null, String.class);
        FilmFromOmdb film = gson.fromJson(exchange.getBody(), FilmFromOmdb.class);
        return film;
    }

    public List<String> getIds() throws InterruptedException {
        List<String> tmbdIds = new ArrayList<>();
        for (int i = 1; i < 30; ++i) {

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(Endpoints.TMDB_API + "top_rated")
                    .queryParam("page", i)
                    .queryParam("api_key", tApiKey);


            ResponseEntity<String> exchange =
                    webClient.exchange(builder.toUriString(), HttpMethod.GET, null, String.class);

            Pattern regexp = Pattern.compile("\"id\":[0-9]+");
            Matcher m = regexp.matcher(exchange.getBody());
            while (m.find()) {
                tmbdIds.add(m.group().replace("\"id\":", ""));
            }


            Thread.sleep(1000);

        }

        List<String> imbdIds = tmbdIds.stream().map((String id) -> {
            ResponseEntity<String> exchange =
                    webClient.exchange(Endpoints.TMDB_API + id + "?api_key=" + tApiKey, HttpMethod.GET, null, String.class);
            Pattern regexp = Pattern.compile("tt[0-9]+");
            Matcher m = regexp.matcher(Objects.requireNonNull(exchange.getBody()));

            //ограничения на кол-во запросов в минуту
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            m.find();
            return m.group();
        }).collect(Collectors.toList());
        return imbdIds;
    }
}

