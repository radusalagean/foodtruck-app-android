package com.busytrack.foodtruckclient.di.application.network;

import androidx.annotation.Nullable;

import com.busytrack.foodtruckclient.BuildConfig;
import com.busytrack.foodtruckclient.authentication.AuthenticationRepository;
import com.busytrack.foodtruckclient.network.NetworkConstants;
import com.busytrack.foodtruckclient.network.interceptor.HeaderInterceptor;
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
    @Nullable
    Interceptor provideLoggingInterceptor() {
        if (BuildConfig.DEBUG) {
            return new HttpLoggingInterceptor(message -> Timber.tag(NetworkConstants.OKHTTP_TAG).i(message))
                    .setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        return null;
    }

    @Provides
    @Named(NetworkConstants.NAMED_STETHO_INTERCEPTOR)
    @Nullable
    Interceptor provideStethoInterceptor() {
        return BuildConfig.DEBUG ? new StethoInterceptor() : null;
    }

    @Provides
    @Named(NetworkConstants.NAMED_HEADER_INTERCEPTOR)
    Interceptor provideHeaderInterceptor(AuthenticationRepository authenticationRepository) {
        return new HeaderInterceptor(authenticationRepository);
    }
}
