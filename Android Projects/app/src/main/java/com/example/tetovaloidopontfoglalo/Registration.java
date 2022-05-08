package com.example.tetovaloidopontfoglalo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {
    EditText usernameET, passwordET, emailET, passconfET, phonenumET;
    TextView valasz;
    private SharedPreferences preferences;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        usernameET= findViewById(R.id.username);
        emailET= findViewById(R.id.email);
        passwordET= findViewById(R.id.Pass);
        passconfET= findViewById(R.id.Pass2);
        phonenumET = findViewById(R.id.mobil);
        valasz = findViewById(R.id.valasz);
        auth = FirebaseAuth.getInstance();
    }

    public void Reging(View view) {
        String username = usernameET.getText().toString();
        String email = emailET.getText().toString();
        String pass = passwordET.getText().toString();
        String passc = passconfET.getText().toString();
        String phone = phonenumET.getText().toString();

        if (!passc.equals(pass)) {
            valasz.setTextColor(Color.parseColor("#B71C1C"));
            valasz.setText("HIBA! Nem egyezik a két jelszó!");
            return;
        }
        valasz.setText("");

        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    valasz.setTextColor(Color.parseColor("#5FF402"));
                    valasz.setText("Sikeres regisztráció! \nLépjen visza a bejelentkezéshez!");
                }
                else{
                    valasz.setTextColor(Color.parseColor("#B71C1C"));
                    valasz.setText("Hiba történt!\n"+task.getException().getMessage());
                }
            }
        });


    }

    public void Cancel(View view) {
        finish();
    }
}