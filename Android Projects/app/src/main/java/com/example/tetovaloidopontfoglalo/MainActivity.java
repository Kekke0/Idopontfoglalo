package com.example.tetovaloidopontfoglalo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private GoogleSignInClient gsign;
    private static final int RC_SIGN_IN = 6969;
    Button google, guest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        gsign = GoogleSignIn.getClient(this,gso);
        mAuth= FirebaseAuth.getInstance();

        google=(Button)findViewById(R.id.gmaillog);
        guest=(Button)findViewById(R.id.guest);
    }

    @Override
    public  void  onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                Toast.makeText(MainActivity.this, "Sikeres bejelentkezés!",Toast.LENGTH_LONG).show();
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            }catch (ApiException e){
                Toast.makeText(MainActivity.this, "A bejelentkezés meghiúsult!",Toast.LENGTH_LONG).show();
            }
        }
    }

    public  void firebaseAuthWithGoogle(String idToken){

        Animation animation= AnimationUtils.loadAnimation(this,R.anim.bounce);
        google.startAnimation(animation);
        AuthCredential cred = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(cred).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Startdate();
                }
                else{
                    Toast.makeText(MainActivity.this, "Hiba!\n"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void Login(View view) {

        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }

    public void Reg(View view) {
        Intent intent = new Intent(this,Registration.class);
        startActivity(intent);
    }

    public void Jumptodate(View view) {
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.bounce);
        guest.startAnimation(animation);
        mAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Startdate();
                }
                else{
                    Toast.makeText(MainActivity.this, "Hiba!\n"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void googlelog(View view) {

        Intent gsignint = gsign.getSignInIntent();
        startActivityForResult(gsignint, RC_SIGN_IN);
    }
    public void Startdate(){
        Intent intent = new Intent(this,Datumvalaszto.class);
        startActivity(intent);
    }
}