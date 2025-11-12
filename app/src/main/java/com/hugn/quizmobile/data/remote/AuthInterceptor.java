package com.hugn.quizmobile.data.remote;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.hugn.quizmobile.ui.login.LoginActivity;
import com.hugn.quizmobile.utils.SharedPrefManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private Context context;

    public AuthInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        String token = SharedPrefManager.getToken(context);

        Request request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .addHeader("Accept", "application/json")
                .build();

        Response response = chain.proceed(request);

        Log.d("URL", request.url().toString());

        // Token hết hạn hoặc không hợp lệ
        if (response.code() == 401) {
            // Xóa token
            SharedPrefManager.clearToken(context);

            // Lấy path của request
            String path = request.url().encodedPath();
            Log.d("API_RESPONSE", path);
            // Nếu không phải endpoint login
            if (!path.contains("/login")) {
                // Chỉ redirect nếu chưa ở LoginActivity
                if (!(context instanceof LoginActivity)) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                }
            }
        }

        return response;
    }
}