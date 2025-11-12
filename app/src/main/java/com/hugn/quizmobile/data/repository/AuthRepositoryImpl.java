package com.hugn.quizmobile.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.hugn.quizmobile.data.remote.api.ApiClient;
import com.hugn.quizmobile.data.remote.api.ApiService;
import com.hugn.quizmobile.data.remote.request.LoginGoogleRequest;
import com.hugn.quizmobile.data.remote.response.LoginResponse;
import com.hugn.quizmobile.data.remote.request.LoginRequest;
import com.hugn.quizmobile.domain.model.User;
import com.hugn.quizmobile.domain.repository.AuthRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepositoryImpl implements AuthRepository {
    private final ApiService apiService;

    public AuthRepositoryImpl(ApiService apiService) {
        this.apiService = apiService;
    }
    @Override
    public LiveData<String> login(String email, String password) {
        MutableLiveData<String> tokenLiveData = new MutableLiveData<>();

        apiService.login(new LoginRequest(email, password))
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if(response.isSuccessful() && response.body() != null) {
                            tokenLiveData.setValue(response.body().getToken());
                        } else {
                            tokenLiveData.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        tokenLiveData.setValue(null);
                    }
                });

        return tokenLiveData;
    }

    @Override
    public LiveData<User> auth() {
        MutableLiveData<User> user = new MutableLiveData<>();
        apiService.auth()
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.d("API_RESPONSE", new Gson().toJson(response.body()));

                        if(response.isSuccessful() && response.body() != null) {
                            user.setValue(response.body());
                        } else {
                            user.setValue(null);
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        user.setValue(null);
                    }
                });
        return user;
    }

    @Override
    public LiveData<String> signGoogle(String token) {
        MutableLiveData<String> tokenLiveData = new MutableLiveData<>();
        Log.d("GoogleSignIn", "Tên:ádsad " + token);
        apiService.signGoogle(new LoginGoogleRequest(token))
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                        if(response.isSuccessful() && response.body() != null) {
                            tokenLiveData.setValue(response.body().getToken());
                        } else {
                            tokenLiveData.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        tokenLiveData.setValue(null);
                    }
                });

        return tokenLiveData;
    }


}
