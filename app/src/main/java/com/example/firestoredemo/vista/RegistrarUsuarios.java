package com.example.firestoredemo.vista;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firestoredemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrarUsuarios extends AppCompatActivity {
    private FirebaseFirestore myBBDD;
    private FirebaseAuth mAuth;
    Button registrarse;
    EditText nombre;
    EditText passw;
    EditText email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuarios);
        FirebaseApp.initializeApp(this);
        this.setTitle("Registrar Usuario");
        mAuth = FirebaseAuth.getInstance();


        nombre = findViewById(R.id.nombre);
        passw = findViewById(R.id.passw);
        email = findViewById(R.id.email);
        registrarse = findViewById(R.id.registrarse);

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombreUser = nombre.getText().toString();
                String passwUser = passw.getText().toString();
                String emailUser = email.getText().toString();

                if (nombreUser.isEmpty() || passwUser.isEmpty() || emailUser.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Por Favor, rellena todos los campos.", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(emailUser, passwUser)
                            .addOnCompleteListener(RegistrarUsuarios.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(getApplicationContext(), "Registro existoso.", Toast.LENGTH_SHORT).show();
                                        finish();
                                        //FirebaseUser user = mAuth.getCurrentUser();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(RegistrarUsuarios.this, "Este Usario ya esta en uso.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    /*
    private void enviarUsuario(String nombreUser, String passwUser, String dniUser) {
    // Todo esto esta comentado porque es interesante lo de obtener datos de firebase

        Map<String, Object> datos = new HashMap<>();
        datos.put("Nombre", nombreUser);
        datos.put("Contraseña", passwUser);
        myBBDD = FirebaseFirestore.getInstance();
        myBBDD.collection("Usuarios").document(dniUser).set(datos)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

    }
*/
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

}