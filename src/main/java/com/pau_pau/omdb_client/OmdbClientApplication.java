package com.pau_pau.omdb_client;

import com.pau_pau.omdb_client.http.Endpoints;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OmdbClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmdbClientApplication.class, args);
		System.out.println(Endpoints.OMDB_API_WITH_KEY);
	}

}
