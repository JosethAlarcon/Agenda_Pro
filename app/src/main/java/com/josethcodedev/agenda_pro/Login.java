package com.josethcodedev.agenda_pro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import models.ApiService;
import models.LoginRequest;
import models.LoginResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    private EditText CorreoTXT, ContrasenaTXT;
    private Button login_btn;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        CorreoTXT = findViewById(R.id.CorreoTXT);
        ContrasenaTXT = findViewById(R.id.ContrasenaTXT);
        login_btn = findViewById(R.id.Login_btn);

        // Inicializar Retrofit con interceptor para ver logs
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.32/agenda_mysql/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        // Configurar el botón de login
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = CorreoTXT.getText().toString().trim();
                String password = ContrasenaTXT.getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty()) {
                    realizarLogin(email, password);
                } else {
                    Toast.makeText(Login.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void realizarLogin(String email, String password) {
        // Crear el objeto de solicitud
        LoginRequest loginRequest = new LoginRequest(email, password);

        // Llamada a la API de login
        Call<LoginResponse> call = apiService.login(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    if ("Login exitoso".equals(loginResponse.getMessage())) {
                        Toast.makeText(Login.this, "Login exitoso", Toast.LENGTH_SHORT).show();
                        // Navegar al menú principal
                        startActivity(new Intent(Login.this, Menu_act.class));
                    } else {
                        Toast.makeText(Login.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("LoginError", "Error: ", t);
                Toast.makeText(Login.this, "Fallo en la conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

