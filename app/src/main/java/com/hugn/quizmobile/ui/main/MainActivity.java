package com.hugn.quizmobile.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.hugn.quizmobile.R;
import com.hugn.quizmobile.adapter.ProductAdapter;
import com.hugn.quizmobile.base.PrivateActivity;
import com.hugn.quizmobile.data.remote.api.ApiService;
import com.hugn.quizmobile.data.remote.request.LoginRequest;
import com.hugn.quizmobile.data.remote.response.LoginResponse;
import com.hugn.quizmobile.data.repository.AuthRepositoryImpl;
import com.hugn.quizmobile.domain.model.Product;
import com.hugn.quizmobile.data.remote.api.ApiClient;
import com.hugn.quizmobile.domain.model.User;
import com.hugn.quizmobile.ui.home.HomeFragment;
import com.hugn.quizmobile.ui.login.LoginActivity;
import com.hugn.quizmobile.ui.login.LoginViewModel;
import com.hugn.quizmobile.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends PrivateActivity {

    private ApiService apiService;
    private AuthViewModel viewModel;
    BottomNavigationView bottomNav ;

    @Override
    protected void onAuthSuccess(User authResult) {
        setContentView(R.layout.activity_main);
        bottomNav = findViewById(R.id.bottomNav);
        ImageView imgAvatar = findViewById(R.id.imgAvatar);
//        Button btnLogout = findViewById(R.id.btnLogout)
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();

            if (id == R.id.home) {
                selectedFragment = new HomeFragment();
            }
//            else if (id == R.id.search) {
//                selectedFragment = new SearchFragment();
//            } else if (id == R.id.abc) {
//                selectedFragment = new ProfileFragment();
//            }

            if (selectedFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentContainer, selectedFragment)
                        .commit();
            }

            return true;
        });

        // Load mặc định tab đầu tiên
        bottomNav.setSelectedItemId(R.id.home);
        // load avatar từ URL nếu có
        Glide.with(this).load(authResult.getAvatar())
                .placeholder(R.drawable.ic_launcher_background)
                .circleCrop()
                .into(imgAvatar);
        apiService = ApiClient.getApiService(this);
        imgAvatar.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, v);
            popupMenu.getMenuInflater().inflate(R.menu.menu_header, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.action_logout) {
                    apiService.logout().enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) { }
                    });
                    return true;
                }
                return false;
            });
            popupMenu.show();
        });
    }



}