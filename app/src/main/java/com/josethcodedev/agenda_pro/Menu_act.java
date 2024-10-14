package com.josethcodedev.agenda_pro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Menu_act extends AppCompatActivity {

    //Crear variables para los botones
    private Button btn_seleccionar;

    private Spinner spinner1;
    private TextView txt;

    private String[] opciones = {"Transporte de Pasajeros", "Transporte para Mudanza", "Transporte de Encomienda"};

    private ViewFlipper vf;

    private int[] image = {R.drawable.auto_sf, R.drawable.camion_sf, R.drawable.furgon_sf};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);

        btn_seleccionar = findViewById(R.id.btn_seleccionar);

        txt = findViewById(R.id.textoOpcion);

        spinner1 = (Spinner) findViewById(R.id.spinner);

        vf = (ViewFlipper) findViewById(R.id.slider);

        // Crear un ArrayAdapter usando el array de opciones y un layout predeterminado
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, opciones);

        // Aplicar el adaptador al Spinner
        spinner1.setAdapter(adapter);

        // Configurar un Listener para el Spinner
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOpciones = parent.getItemAtPosition(position).toString();

                // Usando switch para manejar la selección
                switch (selectedOpciones) {
                    case "Transporte de Pasajeros":
                        txt.setText("Has seleccionado: Transporte de Pasajeros.");
                        break;
                    case "Transporte para Mudanza":
                        txt.setText("Has seleccionado: Transporte para Mudanza");
                        break;
                    case "Transporte de Encomienda":
                        txt.setText("Has seleccionado: Transporte para Encomienda");
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                txt.setText("Por favor Selecciona una opcion"); // Puedes agregar un mensaje por defecto
            }
        });

        btn_seleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu_act.this, TransOpciones_act.class));
            }
        });

        for(int i=0; i<image.length; i++)
        {
            flip_image(image[i]);
        }

    }
    public void flip_image(int i)
    {
        ImageView view = new ImageView(this);
        view.setBackgroundResource(i);
        vf.addView(view);
        vf.setFlipInterval(2800);
        vf.setAutoStart(true);

        vf.setInAnimation(this, android.R.anim.slide_in_left);
        vf.setOutAnimation(this, android.R.anim.slide_out_right);
    }

}