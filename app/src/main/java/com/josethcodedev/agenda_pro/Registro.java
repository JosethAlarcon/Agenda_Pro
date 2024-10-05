package com.josethcodedev.agenda_pro;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Registro extends AppCompatActivity {

    // Crear Variables
    public EditText NombreTXT, CorreoTXT, ContrasenaTXT;
    public Button RegistrarUsuarioBTN;
    public TextView TengoCuentaTXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);

        // Inicializar variables
        NombreTXT = findViewById(R.id.NombreTXT);
        CorreoTXT = findViewById(R.id.CorreoTXT);
        ContrasenaTXT = findViewById(R.id.ContrasenaTXT);

        RegistrarUsuarioBTN = findViewById(R.id.Login_btn);

        RegistrarUsuarioBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Obtener los datos ingresados por el usuario
                String nombre = NombreTXT.getText().toString().trim();
                String correo = CorreoTXT.getText().toString().trim();
                String password = ContrasenaTXT.getText().toString().trim();

                // Llamar método para realizar el registro
                if (!nombre.isEmpty() && !correo.isEmpty() && !password.isEmpty()) {
                    registrarUsuario(nombre, correo, password);
                } else {
                    Toast.makeText(Registro.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registrarUsuario(final String nombre, final String correo, final String password) {

        // Hilo para manejar la petición HTTP en segundo plano
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.106.1/agenda_mysql/registro.php");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST"); //Enviar los adatos
                    httpURLConnection.setDoOutput(true); //Enviamos de salida

                    // Asegurarse de que el servidor interprete correctamente la solicitud POST
                    httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                    // Enviar datos al servidor
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String post_data = URLEncoder.encode("nombre", "UTF-8") + "=" + URLEncoder.encode(nombre, "UTF-8") + "&"
                            + URLEncoder.encode("correo", "UTF-8") + "=" + URLEncoder.encode(correo, "UTF-8") + "&"
                            + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    // Obtener respuesta del servidor
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    final StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        result.append(line);
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    // Mostrar respuesta en el hilo principal (UI Thread)
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Registro.this, "Respuesta del servidor: " + result.toString(), Toast.LENGTH_LONG).show();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    // Mostrar error en el hilo principal (UI Thread)
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Registro.this, "Registro Exitoso: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();
    }
}
