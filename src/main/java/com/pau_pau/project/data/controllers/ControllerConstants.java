package com.pau_pau.project.data.controllers;

public class ControllerConstants {
    public static final String FILMS_URL = "/api/films";
    public static final String FILM_PATH_ID = "id";
    public static final String FILM_BY_ID = "/film/{" + FILM_PATH_ID + "}";


    public static final String DIRECTORS_URL = "/api/directors";
    public static final String DIRECTOR_PATH_ID = "id";
    public static final String DIRECTOR_BY_ID = "/director/{" + DIRECTOR_PATH_ID + "}";


    public static final String ACCOUNT_URL = "/api/account";
    public static final String ACCOUNT_USERNAME = "/{username}";
    public static final String CHANGE_ROLE = "/role/{username}";
}
