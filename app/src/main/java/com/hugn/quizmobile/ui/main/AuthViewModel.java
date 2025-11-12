package com.hugn.quizmobile.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hugn.quizmobile.data.remote.api.ApiClient;
import com.hugn.quizmobile.data.remote.api.ApiService;
import com.hugn.quizmobile.data.repository.AuthRepositoryImpl;
import com.hugn.quizmobile.domain.model.User;

public class AuthViewModel extends AndroidViewModel {
    private ApiService apiService;
    private AuthRepositoryImpl repository;
    public AuthViewModel(@NonNull Application application) {
        super(application);
        // Truyền Application context cho Repository
        apiService =  ApiClient.getApiService(application );
        repository = new AuthRepositoryImpl(apiService);
    }

    public LiveData<User> auth() {
        return repository.auth();
    }

}
