package com.hugn.quizmobile.domain.repository;
import androidx.lifecycle.LiveData;

import com.hugn.quizmobile.domain.model.User;

public interface AuthRepository {
    LiveData<String> login(String email, String password);
    LiveData<User> auth();
    LiveData<String> signGoogle(String token);
}
