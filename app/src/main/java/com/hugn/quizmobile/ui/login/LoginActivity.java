package com.hugn.quizmobile.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.hugn.quizmobile.R;
import com.hugn.quizmobile.ui.main.MainActivity;
import com.hugn.quizmobile.utils.SharedPrefManager;


public class LoginActivity extends AppCompatActivity {
    private LoginViewModel viewModel;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("358828013623-pi5lut4a81i5vlggglsj94ih4c0deta8.apps.googleusercontent.com")
                .requestEmail()
                .build();
//        358828013623-u1jddlr3m71t25fq7rlj93tjb2tiv228.apps.googleusercontent.com
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        SignInButton btnGoogle = findViewById(R.id.btnGoogleSignIn);

        // Bấm nút → mở popup đăng nhập Google
        btnGoogle.setOnClickListener(v -> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });
        Button btnLogin = findViewById(R.id.btnLogin);
        EditText edtEmail = findViewById(R.id.edtEmail);
        EditText edtPassword = findViewById(R.id.edtPassword);

        btnLogin.setOnClickListener(v -> {
            String email = edtEmail.getText().toString();
            String password = edtPassword.getText().toString();

            viewModel.login(email, password).observe(this, token -> {
                if (token != null) {
                    // Lưu token
                    SharedPrefManager.saveToken(this, token);
                    Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                    // Chuyển sang màn hình chính
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Sai email hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                String idToken = account.getIdToken();
                viewModel.signGoogle(idToken).observe(this, token -> {
                    if (token != null) {
                        // Lưu token
                        SharedPrefManager.saveToken(this, token);
                        Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                        // Chuyển sang màn hình chính
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Sai email hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                });


            } catch (ApiException e) {
                Log.e("GoogleSignIn", "Đăng nhập thất bại, mã lỗi: " + e.getStatusCode());
            }
        }
    }
}