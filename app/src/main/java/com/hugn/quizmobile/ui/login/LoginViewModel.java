package com.hugn.quizmobile.ui.login;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.hugn.quizmobile.data.remote.api.ApiClient;
import com.hugn.quizmobile.data.remote.api.ApiService;
import com.hugn.quizmobile.data.repository.AuthRepositoryImpl;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class LoginViewModel extends AndroidViewModel {
    private ApiService apiService;
    private  AuthRepositoryImpl repository;
    public LoginViewModel(@NonNull Application application) {
        super(application);
        // Truyền Application context cho Repository
        apiService =  ApiClient.getApiService(application );
        repository = new AuthRepositoryImpl(apiService);
    }

    public LiveData<String> login(String email, String password) {
        return repository.login(email, password);
    }
    public LiveData<String> signGoogle(String token) {
        return repository.signGoogle(token);
    }
}
