package com.busytrack.foodtruckclient.network.interceptor;

import com.busytrack.foodtruckclient.authentication.AuthenticationRepository;
import com.busytrack.foodtruckclient.network.NetworkConstants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    private AuthenticationRepository authenticationRepository;

    public HeaderInterceptor(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        if (request.headers().get(NetworkConstants.HEADER_KEY_AUTHORIZATION) == null &&
                authenticationRepository.getAuthenticatedAccount() != null) {
            builder.addHeader(NetworkConstants.HEADER_KEY_AUTHORIZATION,
                            NetworkConstants.getAuthorizationHeader(
                                    authenticationRepository.getAuthenticatedAccount().getToken()
                            ));
        }
        return chain.proceed(builder.build());
    }
}
