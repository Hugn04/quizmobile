package com.hugn.quizmobile.data.remote.api;
import com.hugn.quizmobile.data.remote.request.LoginGoogleRequest;
import com.hugn.quizmobile.data.remote.request.LoginRequest;
import com.hugn.quizmobile.data.remote.response.LoginResponse;
import com.hugn.quizmobile.domain.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
    @POST("auth/google")
    Call<LoginResponse> signGoogle(@Body LoginGoogleRequest loginGoogleRequest);
    @GET("auth")
    Call<User> auth();
    @GET("logout")
    Call<String> logout();

}
