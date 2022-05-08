package com.example.tetovaloidopontfoglalo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tetovaloidopontfoglalo.Model.FoglalasAdapter;
import com.example.tetovaloidopontfoglalo.Model.Idopont;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;

public class Foglalasok extends AppCompatActivity {

    private static final String LOG_TAG = Foglalasok.class.getName();
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FoglalasAdapter mAdapter;

    private RecyclerView mrecyc;
    private ArrayList<Idopont> mList;


    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foglalasok);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user==null){
            finish();
        }

        mrecyc = findViewById(R.id.recview);
        mrecyc.setLayoutManager(new GridLayoutManager(this,1));
        mList = new ArrayList<>();

        mAdapter = new FoglalasAdapter(this, mList);

        mrecyc.setAdapter(mAdapter);

        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Foglalasok");

        queryData();
    }

    private void queryData() {

        mList.clear();


        mItems.whereEqualTo("useremail",user.getEmail()).orderBy("date").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document: queryDocumentSnapshots){
                Idopont idopont = document.toObject(Idopont.class);
                mList.add(idopont);
            }

            if(mList.size() ==0)
                Toast.makeText(Foglalasok.this, "Úgytűnik nincsen foglalása a rendszerben!",Toast.LENGTH_LONG).show();
            mAdapter.notifyDataSetChanged();

        }).addOnFailureListener(e -> {
            Toast.makeText(Foglalasok.this, "Hibatörtént a lekérésben!",Toast.LENGTH_LONG).show();
        });

//        dDates[0] = "dátum";
//        dIntent[0] = "Intent";
//        dleiras[0]= "bővebben";
//        dTime[0]="ido";
//        dDates[1] = "dátum";
//        dIntent[1] = "Intent";
//        dleiras[1]= "bővebben";
//        dTime[1]="ido";
//        dDates[2] = "dátum";
//        dIntent[2] = "Intent";
//        dleiras[2]= "bővebben";
//        dTime[2]="ido";
//        dDates[3] = "dátum";
//        dIntent[3] = "Intent";
//        dleiras[3]= "bővebben";
//        dTime[3]="ido";

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.basicmenubar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.back) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}