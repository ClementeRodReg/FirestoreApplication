package com.example.firestoredemo.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firestoredemo.R;
import com.example.firestoredemo.metodos.MetodosEventoSeleccionado;


public class EventoSeleccionado extends AppCompatActivity {

    TextView lblEventoSeleccionado;
    ImageView imageView;
    TextView editTextDia;
    TextView editTextMes;
    TextView editTextAno;
    Button btnContinuar;
    String dia, mes, ano;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventoseleccionado);

        MetodosEventoSeleccionado metodosEventoSeleccionado = new MetodosEventoSeleccionado();

        int imagenReferencia = getIntent().getIntExtra("clave_datoImagen", 0);

        lblEventoSeleccionado = findViewById(R.id.lblEventoSeleccionado);
        imageView = findViewById(R.id.fotoSeleccionada);
        editTextDia = findViewById(R.id.editTextDia);
        editTextMes = findViewById(R.id.editTextMes);
        editTextAno = findViewById(R.id.editTextAno);
        btnContinuar = findViewById(R.id.btnContinuar);

        imageView.setImageResource(imagenReferencia);

        String nombreEvento = getIntent().getStringExtra("clave_datoNombre");
        lblEventoSeleccionado.setText(nombreEvento.toString());

        metodosEventoSeleccionado.limitadorDeEscritura2(editTextDia);
        metodosEventoSeleccionado.limitadorDeEscritura2(editTextMes);
        metodosEventoSeleccionado.limitadorDeEscritura4(editTextAno);

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dia = editTextDia.getText().toString();
                mes = editTextMes.getText().toString();
                ano = editTextAno.getText().toString();

                if(!(dia.isEmpty()) && !(mes.isEmpty()) && !(ano.isEmpty())){

                    if(dia.length() == 2 && mes.length() == 2 && ano.length() == 4){

                        String fecha = editTextDia.getText().toString() + "/" + editTextMes.getText().toString() + "/" + editTextAno.getText().toString();
                        Toast.makeText(EventoSeleccionado.this, "La fecha es " + fecha.toString(), Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(EventoSeleccionado.this, "Compruebe que ha rellenado bien todos los campos", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(EventoSeleccionado.this, "Debe rellenar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}