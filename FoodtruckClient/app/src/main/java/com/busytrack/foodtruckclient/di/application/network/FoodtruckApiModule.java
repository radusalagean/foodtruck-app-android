package com.busytrack.foodtruckclient.di.application.network;

import androidx.annotation.Nullable;

import com.busytrack.foodtruckclient.di.application.ApplicationScope;
import com.busytrack.foodtruckclient.network.NetworkConstants;
import com.busytrack.foodtruckclient.network.foodtruckapi.FoodtruckApiService;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = BaseApiModule.class)
public class FoodtruckApiModule implements APIModuleContract<FoodtruckApiService> {

    @Override
    @Provides
    @ApplicationScope
    @Named(NetworkConstants.NAMED_BASE_URL)
    public String getBaseUrl() {
        return NetworkConstants.FOODTRUCK_API_BASE_URL;
    }

    @Override
    @Provides
    @ApplicationScope
    public OkHttpClient provideClient(@Named(NetworkConstants.NAMED_LOGGING_INTERCEPTOR) @Nullable Interceptor loggingInterceptor,
                                      @Named(NetworkConstants.NAMED_STETHO_INTERCEPTOR) @Nullable Interceptor stethoInterceptor,
                                      @Named(NetworkConstants.NAMED_HEADER_INTERCEPTOR) Interceptor headerInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (loggingInterceptor != null) {
            builder.addInterceptor(loggingInterceptor);
        }
        if (stethoInterceptor != null) {
            builder.addNetworkInterceptor(stethoInterceptor);
        }
        builder.addInterceptor(headerInterceptor);
        return builder.build();
    }

    @Override
    @Provides
    @ApplicationScope
    public Retrofit provideRetrofit(@Named(NetworkConstants.NAMED_BASE_URL) String baseUrl, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    @Provides
    @ApplicationScope
    public FoodtruckApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(FoodtruckApiService.class);
    }
}
