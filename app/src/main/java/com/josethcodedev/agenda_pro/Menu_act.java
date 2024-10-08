package com.josethcodedev.agenda_pro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Menu_act extends AppCompatActivity {

    //Crear variables para los botones
    public Button btn_pasajero, btn_mudanza, btn_encomienda;

    private ViewFlipper vf;
    private int[] image = {R.drawable.imgtaxi, R.drawable.imgmudanza, R.drawable.imgencomiendan};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);

        btn_pasajero = findViewById(R.id.btn_pasajero);
        btn_mudanza = findViewById(R.id.btn_mudanza);
        btn_encomienda = findViewById(R.id.btn_encomienda);

        vf = (ViewFlipper) findViewById(R.id.slider);

        btn_pasajero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu_act.this, TransOpciones_act.class));
            }
        });

        btn_mudanza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Menu_act.this, TransOpciones_act.class));
            }
        });

        btn_encomienda.setOnClickListener(new View.OnClickListener() {
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