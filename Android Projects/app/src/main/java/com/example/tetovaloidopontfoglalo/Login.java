package com.example.tetovaloidopontfoglalo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private static final String LOG_TAG = Login.class.getName();
    EditText usernameET, passwordET;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth= FirebaseAuth.getInstance();
        usernameET= findViewById(R.id.editTextUserName);
        passwordET= findViewById(R.id.editTextPassword);
    }

    public void Logging(View view) {
        String user= usernameET.getText().toString();
        String pass= passwordET.getText().toString();

        mAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Toast.makeText(Login.this, "Sikeres bejelentkezés!",Toast.LENGTH_LONG).show();
                    JumptoDate();
                }else{
                    Toast.makeText(Login.this, "Hiba!\n"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void Cancel(View view) {
        finish();
    }

    public void JumptoDate() {
        Intent intent = new Intent(this,Datumvalaszto.class);
        startActivity(intent);
    }
}