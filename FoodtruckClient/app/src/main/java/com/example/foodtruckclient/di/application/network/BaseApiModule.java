package com.example.foodtruckclient.di.application.network;

import com.example.foodtruckclient.BuildConfig;
import com.example.foodtruckclient.network.NetworkConstants;
import com.facebook.stetho.okhttp3.StethoInterceptor;

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
        if (BuildConfig.DEBUG) {
            return new HttpLoggingInterceptor(message -> Timber.tag(NetworkConstants.OKHTTP_TAG).i(message))
                    .setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        return null;
    }

    @Provides
    @Named(NetworkConstants.NAMED_STETHO_INTERCEPTOR)
    Interceptor getStethoInterceptor() {
        return BuildConfig.DEBUG ? new StethoInterceptor() : null;
    }
}
