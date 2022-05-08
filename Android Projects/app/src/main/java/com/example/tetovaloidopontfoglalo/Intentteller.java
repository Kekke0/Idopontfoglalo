package com.example.tetovaloidopontfoglalo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tetovaloidopontfoglalo.Model.Idopont;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Intentteller extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    EditText ETleiras;

    private FirebaseUser user;
    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;
    public String Intent, Leiras,DATUM,IDO,EMAIL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intentteller);
        user = FirebaseAuth.getInstance().getCurrentUser();
        ETleiras =findViewById(R.id.explain);
        spinner = findViewById(R.id.intentek);

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.kategoriak,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        DATUM = getIntent().getStringExtra("Datum");
        IDO = getIntent().getStringExtra("Time");
        Toast.makeText(Intentteller.this, DATUM+": "+IDO,Toast.LENGTH_LONG).show();

        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Foglalasok");
    }

    @Override
    protected void onStop() {
//  Ha az időpontfoglalóban lefut az onStop akkor visszalépünk a Dátum választóba, hogy mikor isszalépünk nehogy olyan időpontot foglaljunk le ami már foglalva lett idő közben.
        super.onStop();
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

        Intent = parent.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void Send(View view) {
        Leiras= ETleiras.getText().toString();
        EMAIL=user.getEmail();
        mItems.add(new Idopont(Intent,
                Leiras,
                DATUM,
                IDO,
                EMAIL)).addOnCompleteListener(task -> {
                    Toast.makeText(Intentteller.this, "Foglalás létrehozva:\n"+DATUM+": "+IDO+"->"+Intent+": "+Leiras+"\nBy: "+user.getEmail(),Toast.LENGTH_LONG).show();
                    finish();
                }).addOnFailureListener(e -> {
            Toast.makeText(Intentteller.this, "Hiba történt a hozzáadásban.",Toast.LENGTH_LONG).show();

        });

    }

    public void Cancel(View view) {
        finish();
    }
}