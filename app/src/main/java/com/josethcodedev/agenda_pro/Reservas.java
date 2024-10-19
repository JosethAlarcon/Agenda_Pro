package com.josethcodedev.agenda_pro;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import models.Reserva;
import models.ReservasAdapter;

public class Reservas extends AppCompatActivity {

    ListView reservasListView;
    ArrayList<Reserva> reservasList; // Cambiado a ArrayList de Reservas
    ReservasAdapter adapter; // Usar el adaptador personalizado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);

        reservasListView = findViewById(R.id.reservas_lista);
        reservasList = new ArrayList<>();

        // Inicializar el adaptador personalizado
        adapter = new ReservasAdapter(this, reservasList);
        reservasListView.setAdapter(adapter);

        // Llamar al método para obtener las reservas
        obtenerReservas();
    }

    private void obtenerReservas() {
        // Crear un nuevo hilo para la petición HTTP
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.100.32/agenda_mysql/reservas.php");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");

                    // Leer la respuesta del servidor
                    BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    in.close();
                    httpURLConnection.disconnect();

                    // Verificar la respuesta del servidor
                    Log.d("Reservas", "Respuesta del servidor: " + response.toString());

                    // Procesar la respuesta JSON
                    procesarReservas(response.toString());

                } catch (Exception e) {
                    Log.e("ReservasError", "Error en la conexión: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Reservas.this, "Error al obtener las reservas", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    private void procesarReservas(String response) {
        try {
            // Verificar si la respuesta es vacía
            if (response == null || response.isEmpty()) {
                Log.e("ReservasError", "La respuesta es vacía");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Reservas.this, "No se recibieron datos", Toast.LENGTH_SHORT).show();
                    }
                });
                return;
            }

            // Procesar el JSON
            JSONArray jsonArray = new JSONArray(response);
            Log.d("Reservas", "Número de reservas: " + jsonArray.length());
            Log.d("Reservas", "Respuesta del servidor: " + response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject reserva = jsonArray.getJSONObject(i);

                // Verificar que cada campo exista en el JSON
                String direccionInicio = reserva.optString("direccionInicio", "N/A");
                String direccionDestino = reserva.optString("direccionDestino", "N/A");
                String fecha = reserva.optString("fecha", "N/A");
                String horario = reserva.optString("horario", "N/A");
                String pago = reserva.optString("metodoPago", "N/A");

                // Agregar los datos al ArrayList de tipo Reservas
                reservasList.add(new Reserva("Inicio: " + direccionInicio,
                        "Destino: " + direccionDestino,
                        "Fecha: " + fecha,
                        "Horario: " + horario,
                        "Metodo de Pago: " + pago));
            }

            // Actualizar el ListView en la UI principal
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!reservasList.isEmpty()) {
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(Reservas.this, "No hay reservas disponibles", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } catch (JSONException e) {
            Log.e("ReservasError", "Error al procesar JSON: " + e.getMessage());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Reservas.this, "Error al procesar los datos", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}