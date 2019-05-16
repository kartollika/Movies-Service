package com.pau_pau.project.data.controllers;

public class ControllerConstants {
    public static final String FILMS_URL = "/api/films";
    public static final String FILM_URL = "/film/";
    public static final String FILM_PATH_ID = "id";
    public static final String FILM_URL_BY_ID = FILM_URL + "{" + FILM_PATH_ID + "}";
    public static final String FILM_PUBLISH = "/publish/{id}";
    public static final String FILM_REJECT = "/reject/{id}";
    public static final String ACCOUNT_ACTIVE_REQUESTS = "/requests";
    public static final String ADMIN_ACTIVE_REQUESTS = "/admin/requests";
    public static final String FILM_RANDOM = "/random";


    public static final String DIRECTORS_URL = "/api/directors";
    public static final String DIRECTOR_URL = "/director/";
    public static final String DIRECTOR_PATH_ID = "id";
    public static final String DIRECTOR_URL_BY_ID = DIRECTOR_URL + "{" + DIRECTOR_PATH_ID + "}";


    public static final String ACCOUNT_URL = "/api/account";
    public static final String ACCOUNT_USERNAME = "/{username}";
    public static final String ACCOUNT_ID = "id/{id}";
    public static final String CHANGE_ROLE = "/role/{username}";

    public static final String WISHLIST_WITH_AUTHENTICATION = "/wishlist";
    public static final String CONTAINS_IN_WISHLIST = WISHLIST_WITH_AUTHENTICATION + "/contains";

    // Describing availability
    public static final String AVAILABLE_EDITOR_ADMIN = "Available for Editor and Admin";
    public static final String AVAILABLE_ADMIN = "Available for Admin";
    public static final String AVAILABLE_EVERYONE = "Available for everyone";
}
