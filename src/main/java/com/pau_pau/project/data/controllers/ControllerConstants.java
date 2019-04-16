package com.pau_pau.project.data.controllers;

public class ControllerConstants {
    public static final String FILMS_URL = "/api/films";
    public static final String FILM_URL = "/film/";
    public static final String FILM_PATH_ID = "id";
    public static final String FILM_URL_BY_ID = FILM_URL + "{" + FILM_PATH_ID + "}";


    public static final String DIRECTORS_URL = "/api/directors";
    public static final String DIRECTOR_URL = "/director/";
    public static final String DIRECTOR_PATH_ID = "id";
    public static final String DIRECTOR_URL_BY_ID = DIRECTOR_URL + "{" + DIRECTOR_PATH_ID + "}";


    public static final String ACCOUNT_URL = "/api/account";
    public static final String ACCOUNT_USERNAME = "/{username}";
    public static final String CHANGE_ROLE = "/role/{username}";

    public static final String WISHLIST_WITH_AUTHENTICATION = "/wishlist";

    public static final String HISTORY_WITH_AUTHENTICATION = "/history";

    public static final int MAX_HISTORY_SIZE = 15;
}
