package com.example.foodtruckclient.di.application.network;

import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Retrofit API Module Contract
 *
 * @param <T> - API Service Type to be provided
 */
public interface APIModuleContract<T> {

    /**
     * Return the base URL of the API service
     */
    String getBaseUrl();

    /**
     * Provide the {@link OkHttpClient} instance
     */
    OkHttpClient provideClient(Interceptor loggingInterceptor,
                               Interceptor stethoInterceptor,
                               Interceptor authenticationInterceptor);

    /**
     * Provide the {@link Retrofit} instance
     */
    Retrofit provideRetrofit(String baseUrl, OkHttpClient okHttpClient);

    /**
     * Provide the API Service of type {@link T}
     */
    T provideApiService(Retrofit retrofit);
}
