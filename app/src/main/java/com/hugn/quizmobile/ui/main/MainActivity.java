package com.hugn.quizmobile.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.hugn.quizmobile.R;
import com.hugn.quizmobile.adapter.ProductAdapter;
import com.hugn.quizmobile.data.remote.api.ApiService;
import com.hugn.quizmobile.data.remote.request.LoginRequest;
import com.hugn.quizmobile.data.remote.response.LoginResponse;
import com.hugn.quizmobile.data.repository.AuthRepositoryImpl;
import com.hugn.quizmobile.domain.model.Product;
import com.hugn.quizmobile.data.remote.api.ApiClient;
import com.hugn.quizmobile.domain.model.User;
import com.hugn.quizmobile.ui.login.LoginActivity;
import com.hugn.quizmobile.ui.login.LoginViewModel;
import com.hugn.quizmobile.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ApiService apiService;
    private AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        viewModel.auth();
        viewModel.auth().observe(this, authResult -> {
            if (authResult != null) {
                Log.d("UserInfo", authResult.getName());
                setContentView(R.layout.activity_main);
                Button btnLogout = findViewById(R.id.btnLogout);
                apiService =  ApiClient.getApiService(this );
                btnLogout.setOnClickListener(v -> {
                    apiService.logout().enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }


                    });
                });


            } else {
                finish(); // để MainActivity không còn trong back stack
            }
        });

    }



}