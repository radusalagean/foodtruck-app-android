package com.example.foodtruckclient.network.interceptor;

import com.example.foodtruckclient.authentication.AuthenticationRepository;
import com.example.foodtruckclient.network.NetworkConstants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthenticationInterceptor implements Interceptor {

    private AuthenticationRepository authenticationRepository;

    public AuthenticationInterceptor(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .addHeader(NetworkConstants.HEADER_KEY_AUTHORIZATION,
                        NetworkConstants.getAuthorizationHeader(
                                authenticationRepository.getAuthenticatedAccount().getToken()
                        ))
                .build();
        return chain.proceed(request);
    }
}
