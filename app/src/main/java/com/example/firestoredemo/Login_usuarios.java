package com.example.firestoredemo;

import static com.example.firestoredemo.R.id.loginDNI;
import static com.example.firestoredemo.R.id.passw;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Login_usuarios extends AppCompatActivity {


    EditText  passwLogin;
    EditText dniLogin;
    Button login;
    TextView muestraLogin;
    private FirebaseFirestore myBBDD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_usuarios);
        FirebaseApp.initializeApp(this);
        this.setTitle("Login Usuario");

        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                passwLogin = findViewById(R.id.passwLogin);
                dniLogin = findViewById(R.id.loginDNI);
                muestraLogin = findViewById(R.id.muestraLogin);
                muestraLogin.setText("");
                String dni = dniLogin.getText().toString().toUpperCase();
                String contra=passwLogin.getText().toString();

                myBBDD = FirebaseFirestore.getInstance();
                Task<DocumentSnapshot> datos = myBBDD.collection("Usuarios").document(dni).get();

                datos.addOnSuccessListener(result -> {
                            // now do something with result
                            if (datos.getResult().getData() != null){
                                if(datos.getResult().getData().get("Contraseña").equals(passwLogin.getText().toString())) {
                                    muestraLogin.setText("Hola, " + datos.getResult().getData().get("Nombre").toString());
                                }
                                else {
                                    muestraLogin.setText("Contraseña incorrecta");
                                }
                            }
                            else {
                                muestraLogin.setText("Registrate para poder iniciar sesion");
                            }
                        })
                        .addOnFailureListener(e -> {
                            // now do something with the exception
                            muestraLogin.setText("hay que esperar");
                        });

            }
        });

    }

}