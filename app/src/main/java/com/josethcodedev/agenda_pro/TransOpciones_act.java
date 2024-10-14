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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TransOpciones_act extends AppCompatActivity {

    // Crear Variables
    public EditText DireccionInicioTXT, DireccionDestinoTXT, FechaTXT, HorarioTXT, PagoTXT;
    public Button RegistrarEnvioBTN, VerReservasBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transopciones);

        // Inicializar variables
        DireccionInicioTXT = findViewById(R.id.direccionInicioTXT);
        DireccionDestinoTXT = findViewById(R.id.direccionDestinoTXT);
        FechaTXT = findViewById(R.id.fechaTXT);
        HorarioTXT = findViewById(R.id.horarioTXT);
        PagoTXT = findViewById(R.id.pagoTXT);

        RegistrarEnvioBTN = findViewById(R.id.btn_reservar);
        VerReservasBTN = findViewById(R.id.btn_ver_reservas);

        VerReservasBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TransOpciones_act.this, Reservas.class));
            }
        });

        RegistrarEnvioBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Obtener los datos ingresados por el usuario
                String direccionInicio = DireccionInicioTXT.getText().toString().trim();
                String direccionDestino = DireccionDestinoTXT.getText().toString().trim();
                String fecha = FechaTXT.getText().toString().trim();
                String horario = HorarioTXT.getText().toString().trim();
                String pago = PagoTXT.getText().toString().trim();

                // Llamar método para realizar el registro
                if (!direccionInicio.isEmpty() && !direccionDestino.isEmpty() && !fecha.isEmpty() && !horario.isEmpty() && !pago.isEmpty()) {
                    registrarEnvio(direccionInicio, direccionDestino, fecha, horario, pago);
                } else {
                    Toast.makeText(TransOpciones_act.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registrarEnvio(final String direccionInicio, final String direccionDestino, final String fecha, final String horario, final String metodoPago) {

        // Hilo para manejar la petición HTTP en segundo plano
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.100.32/agenda_mysql/registro_envio.php");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST"); // Enviar datos por POST
                    httpURLConnection.setDoOutput(true); // Enviamos datos de salida

                    // Asegurarse de que el servidor interprete correctamente la solicitud POST
                    httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                    // Enviar datos al servidor
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String post_data = URLEncoder.encode("direccionInicio", "UTF-8") + "=" + URLEncoder.encode(direccionInicio, "UTF-8") + "&"
                            + URLEncoder.encode("direccionDestino", "UTF-8") + "=" + URLEncoder.encode(direccionDestino, "UTF-8") + "&"
                            + URLEncoder.encode("fecha", "UTF-8") + "=" + URLEncoder.encode(fecha, "UTF-8") + "&"
                            + URLEncoder.encode("horario", "UTF-8") + "=" + URLEncoder.encode(horario, "UTF-8") + "&"
                            + URLEncoder.encode("metodoPago", "UTF-8") + "=" + URLEncoder.encode(metodoPago, "UTF-8");

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
                            Toast.makeText(TransOpciones_act.this, "Respuesta del servidor: " + result.toString(), Toast.LENGTH_LONG).show();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    // Mostrar error en el hilo principal (UI Thread)
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(TransOpciones_act.this, "Error en el registro: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();
    }
}