package com.example.doctruyenapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.doctruyenapplication.api.ApiService;
import com.example.doctruyenapplication.api.RetrofitClient;
import com.example.doctruyenapplication.object.Account;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btnRegister;
    private TextView tvgotoLogin;
    private ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.etRegisterEmail);
        etPassword = findViewById(R.id.etRegisterPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvgotoLogin = findViewById(R.id.tvGoToLogin);

        tvgotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.home_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
            }
        });

        apiService = RetrofitClient.getInstance().create(ApiService.class);

        btnRegister.setOnClickListener(v->register());

    }
    private void register(){
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(email.isEmpty() || password.isEmpty())
        {
            Toast.makeText(this,"Please enter email and password",Toast.LENGTH_SHORT).show();
            return;
        }
        Account account = new Account(email,password,1);

        Call<Account> call = apiService.signup(account);
        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Register.this, "Register successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Register.this,Login.class);
                    startActivity(intent);

                } else {
                    try {
                        String errorMessage = response.errorBody().string(); // Đọc thông báo lỗi từ phản hồi
                        Log.d("RegisterActivity", "Error: " + errorMessage);
                        Log.e(errorMessage,"Register failed: " + errorMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(Register.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}