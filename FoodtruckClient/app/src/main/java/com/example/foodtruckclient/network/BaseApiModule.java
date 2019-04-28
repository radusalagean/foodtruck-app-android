package com.example.foodtruckclient.network;

import com.example.foodtruckclient.BuildConfig;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/**
 * Base class that contains common methods for providing an API Service
 */
@Module
public class BaseApiModule {

    @Provides
    @Named(NetworkConstants.NAMED_LOGGING_INTERCEPTOR)
    Interceptor getLoggingInterceptor() {
        return new HttpLoggingInterceptor(message -> Timber.tag(NetworkConstants.OKHTTP_TAG).d(message))
                .setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY :
                        HttpLoggingInterceptor.Level.NONE);
    }
}
