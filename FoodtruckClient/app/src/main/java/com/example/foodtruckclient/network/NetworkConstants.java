package com.example.foodtruckclient.network;

public class NetworkConstants {

    // URL
    public static final String FOODTRUCK_API_BASE_URL = "https://releasetracker.app/foodtruck-api/v1/";
    public static final String FOODTRUCK_API_STATIC_BASE_URL = "https://releasetracker.app/foodtruck-api-static/";
    public static final String FOODTRUCK_API_PROFILE_IMAGES_BASE_URL = FOODTRUCK_API_STATIC_BASE_URL + "profile_images/";
    public static final String FOODTRUCK_API_PROFILE_THUMBNAILS_BASE_URL = FOODTRUCK_API_PROFILE_IMAGES_BASE_URL + "thumbnails/";
    public static final String FOODTRUCK_API_FOODTRUCK_IMAGES_BASE_URL = FOODTRUCK_API_STATIC_BASE_URL + "foodtruck_images/";
    public static final String FOODTRUCK_API_FOODTRUCK_THUMBNAILS_BASE_URL = FOODTRUCK_API_FOODTRUCK_IMAGES_BASE_URL + "thumbnails/";

    // Log Tags
    public static final String OKHTTP_TAG = "OkHttp";

    // Named Injections
    public static final String NAMED_LOGGING_INTERCEPTOR = "logging-interceptor";
    public static final String NAMED_STETHO_INTERCEPTOR = "stetho-interceptor";
    public static final String NAMED_BASE_URL = "base-url";
}
