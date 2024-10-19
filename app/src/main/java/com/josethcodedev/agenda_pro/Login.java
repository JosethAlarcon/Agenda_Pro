package com.josethcodedev.agenda_pro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import models.LoginResponse;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class Login extends AppCompatActivity {

    // Crear Variables
    private EditText CorreoTXT, ContrasenaTXT;
    private Button login_btn;

    /*
    public interface ApiService {

        @FormUrlEncoded
        @POST("http://192.168.100.32/agenda_mysql/login.php")
        Call<LoginResponse> login(
                @Field("correo") String email,
                @Field("password") String password
        );
    }


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.100.32/agenda_mysql/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ApiService apiService = retrofit.create(ApiService.class);
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        CorreoTXT = findViewById(R.id.CorreoTXT);
        ContrasenaTXT = findViewById(R.id.ContrasenaTXT);
        login_btn = findViewById(R.id.Login_btn);


        //Metodo para probar aplicacion sin hacer login con base de datos
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Menu_act.class));
            }
        });

        /*
        login_btn.setOnClickListener(view -> {
            String email = CorreoTXT.getText().toString().trim();
            String password = ContrasenaTXT.getText().toString().trim();

            if (!email.isEmpty() && !password.isEmpty()) {
                // Realizar la solicitud de login
                Call<LoginResponse> call = apiService.login(email, password);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            LoginResponse loginResponse = response.body();
                            if (loginResponse != null) {
                                if (loginResponse.getStatus().equals("success")) {
                                    Toast.makeText(Login.this, "Login exitoso", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this, Menu_act.class));
                                } else {
                                    Toast.makeText(Login.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(Login.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.e("LoginError", t.getMessage());
                        Toast.makeText(Login.this, "Fallo en la conexión", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(Login.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
         */
    }
}