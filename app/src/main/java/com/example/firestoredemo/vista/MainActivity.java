package com.example.firestoredemo.vista;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.media.MediaPlayer;
import com.example.firestoredemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import org.checkerframework.checker.nullness.qual.NonNull;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private boolean sonidoYaReproducido = false;
    Button registrarboton;
    Button inicioSesion;
    Button modoInvitado;
    EditText passwLogin;
    EditText gmailLogin;
    String gmail;
    String contra;
    private FirebaseAuth mAuth;


    @Override
    protected void onStart() {
        super.onStart();
        if (!sonidoYaReproducido) {
            mediaPlayer.start();
            sonidoYaReproducido = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.startsound);
        mAuth = FirebaseAuth.getInstance();

        //Aqui empieza Shared Preferences
       // passwLogin = findViewById(R.id.passwLogin);
        gmailLogin = findViewById(R.id.loginGmail);
        passwLogin = findViewById(R.id.passwLogin);
        SharedPreferences sh = MainActivity.this.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        gmailLogin.setText(sh.getString("mail", ""));
       // passwLogin.setText(sh.getString("passw", ""));
        //Aqui termina Shared Preferences


        inicioSesion = findViewById(R.id.inicioSesion);
        registrarboton = findViewById(R.id.btn_registro);
        modoInvitado = findViewById(R.id.btn_inicioInvitado);

        // esto mira si ya estaba logeado anteriormente
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }

        registrarboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrarUsuarios.class));
            }
        });

        inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gmail = gmailLogin.getText().toString();
                contra = passwLogin.getText().toString();

                if (!(gmail.isEmpty()) && !(contra.isEmpty())) {
                    mAuth.signInWithEmailAndPassword(gmail, contra)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information

                                        //---Aqui guarda el usuario y contraseña que hace login correctamente en shared preferences---
                                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                        myEdit.putString("mail", gmail);
                                        //myEdit.putString("passw", contra);
                                        myEdit.apply();
                                        //------

                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        //

                                        Log.d("tag", gmail);
                                        Intent IrAVentanaCategorias = new Intent(MainActivity.this, Vista_categorias.class);
                                        IrAVentanaCategorias.putExtra("id_gmail", gmail);
                                        startActivity(IrAVentanaCategorias);

                                        //
                                        IrAVentanaCategorias.putExtra("currentUser", user);
                                        startActivity(IrAVentanaCategorias);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(MainActivity.this, "Usuario o Contraseña erroneos.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                } else {
                    Toast.makeText(getApplicationContext(), "Rellene con los datos necesarios para iniciar sesión.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        modoInvitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signInAnonymously();
                Intent IrAVentanaCategorias = new Intent(MainActivity.this, Vista_categorias.class);
                IrAVentanaCategorias.putExtra("id_gmail", "Modo Invitado");
                startActivity(IrAVentanaCategorias);
            }
        });
    }


}