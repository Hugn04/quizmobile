package com.hugn.quizmobile.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.hugn.quizmobile.data.remote.api.ApiService;
import com.hugn.quizmobile.domain.model.User;
import com.hugn.quizmobile.ui.main.AuthViewModel;

public abstract class PrivateActivity extends AppCompatActivity {

    protected AuthViewModel viewModel;
    protected ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        viewModel.auth().observe(this, authResult -> {
            if (authResult != null) {
                onAuthSuccess(authResult); // callback để các Activity xử lý UI
            } else {
                finish(); // không auth thì đóng activity
            }
        });
    }

    // Phương thức này sẽ được các Activity override để setup layout và UI
    protected abstract void onAuthSuccess(User authResult);
}
