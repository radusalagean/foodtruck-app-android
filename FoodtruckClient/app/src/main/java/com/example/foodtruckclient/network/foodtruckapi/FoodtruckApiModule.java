package com.example.foodtruckclient.network.foodtruckapi;

import com.example.foodtruckclient.network.APIModuleContract;
import com.example.foodtruckclient.network.BaseApiModule;
import com.example.foodtruckclient.network.NetworkConstants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

@Module(includes = BaseApiModule.class)
public class FoodtruckApiModule implements APIModuleContract<FoodtruckApiService> {

    @Override
    @Provides
    @Named(NetworkConstants.NAMED_BASE_URL)
    public String getBaseUrl() {
        return NetworkConstants.FOODTRUCK_API_BASE_URL;
    }

    @Override
    @Provides
    public OkHttpClient provideClient(List<Interceptor> interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        for (Interceptor i : interceptors) {
            builder.addInterceptor(i);
        }
        return builder.build();
    }

    @Override
    @Provides
    public List<Interceptor> provideInterceptors(@Named(NetworkConstants.NAMED_LOGGING_INTERCEPTOR) Interceptor loggingInterceptor) {
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(loggingInterceptor);
        // TODO Add Auth Interceptor
        return interceptors;
    }

    @Override
    @Provides
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
    @Singleton
    public FoodtruckApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(FoodtruckApiService.class);
    }
}
