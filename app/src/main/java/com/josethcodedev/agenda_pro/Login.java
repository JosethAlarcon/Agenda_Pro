package com.josethcodedev.agenda_pro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {

    // Crear Variables
    public EditText CorreoTXT, ContrasenaTXT;
    public Button RegistrarUsuarioBTN;

    Button login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        CorreoTXT = findViewById(R.id.CorreoTXT);
        ContrasenaTXT = findViewById(R.id.ContrasenaTXT);
        login_btn = findViewById(R.id.Login_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Obtener los datos ingresados por el usuario

                String correo = CorreoTXT.getText().toString().trim();
                String password = ContrasenaTXT.getText().toString().trim();

                // Llamar método para realizar el registro
                if (!correo.isEmpty() && !password.isEmpty()) {
                    LoginUsuario();
                } else {
                    Toast.makeText(Login.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Menu_act.class));
            }
        });
    }
    private void LoginUsuario(){

    }
}