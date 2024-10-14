package com.josethcodedev.agenda_pro;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
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

public class Reservas extends AppCompatActivity {

    ListView reservasListView;
    ArrayList<String> reservasList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);

        reservasListView = findViewById(R.id.reservas_lista);
        reservasList = new ArrayList<>();

        // Inicializar el ArrayAdapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reservasList);
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

                    // Procesar la respuesta JSON
                    procesarReservas(response.toString());

                } catch (Exception e) {
                    Log.e("ReservasError", e.getMessage());
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
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject reserva = jsonArray.getJSONObject(i);
                String direccionInicio = reserva.getString("direccionInicio");
                String direccionDestino = reserva.getString("direccionDestino");
                String fecha = reserva.getString("fecha");
                String horario = reserva.getString("horario");
                String pago = reserva.getString("metodoPago");

                // Agregar los datos al ArrayList
                reservasList.add("Inicio: " + direccionInicio + "\nDestino: " + direccionDestino +
                        "\nFecha: " + fecha + "\nHorario: " + horario +
                        "\nMétodo de pago: " + pago);
            }

            // Actualizar el ListView en la UI principal
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Reservas.this, "Error al procesar los datos", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
