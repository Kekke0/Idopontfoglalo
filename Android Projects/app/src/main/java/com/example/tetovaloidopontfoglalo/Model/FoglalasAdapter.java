package com.example.tetovaloidopontfoglalo.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tetovaloidopontfoglalo.Intentteller;
import com.example.tetovaloidopontfoglalo.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class FoglalasAdapter extends RecyclerView.Adapter<FoglalasAdapter.Viewholder> {
    private ArrayList<Idopont> Foglalasok;
    private Context mcontext;
    private int lastPosition= -1;

    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;

    public FoglalasAdapter(Context context, ArrayList<Idopont> foglalasinfo) {
        this.Foglalasok = foglalasinfo;
        this.mcontext=context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(mcontext).inflate(R.layout.list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FoglalasAdapter.Viewholder holder, int position) {

        Idopont jelenlegi=Foglalasok.get(position);
        holder.bindTo(jelenlegi);

        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Foglalasok");

        if (holder.getAdapterPosition()>lastPosition){
            Animation animation= AnimationUtils.loadAnimation(mcontext,R.anim.animation1);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return this.Foglalasok.size();
    }

    class  Viewholder extends RecyclerView.ViewHolder{
        private TextView mDate, mIntent, mBovebben;
        private String useremail, datum,ido;

        public Viewholder(@NonNull View itemView) {
            super(itemView);


            mDate= itemView.findViewById(R.id.Date);
            mIntent= itemView.findViewById(R.id.intent);
            mBovebben= itemView.findViewById(R.id.leiras);

            itemView.findViewById(R.id.lemond).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItems.whereEqualTo("useremail",useremail).whereEqualTo("date",datum).whereEqualTo("time",ido).get()
                            .addOnSuccessListener(queryDocumentSnapshots -> {
                        for (QueryDocumentSnapshot document: queryDocumentSnapshots){
                            DocumentReference ref =document.getReference();

                            ref.delete().addOnSuccessListener(succes -> {
                                Toast.makeText(view.getContext(), "A törlés sikeres.\nTöltsd újra az oldalt hogy láthasd a változást.",Toast.LENGTH_LONG).show();
                            }).addOnFailureListener(failure -> {
                                Toast.makeText(view.getContext(), "Sikertelen törlés", Toast.LENGTH_SHORT).show();
                            });
                        }
                            });
//                    for(Idopont item : mItems) {
//                        if(item.getDate().equals(spinner.getSelectedItem().toString())) {
//                            DocumentReference ref = fagylaltok.document(item._getId());
//                            ref.delete().addOnSuccessListener(succes -> {
//                                Log.d(LOG_TAG, "OTT VAN BAZDMEG " + item._getId());
//                            }).addOnFailureListener(failure -> {
//                                Toast.makeText(this, "NEM LEHET TOROLNI", Toast.LENGTH_SHORT).show();
//                            });
//                        }
//                    }
                }
            });
        }

        public void bindTo(Idopont jelenlegi) {
            useremail=jelenlegi.getUseremail();
            datum=jelenlegi.getDate();
            ido=jelenlegi.getTime();
            mDate.setText(jelenlegi.getDate()+": "+jelenlegi.getTime());
            mIntent.setText(jelenlegi.getIntent());
            mBovebben.setText(jelenlegi.getBovebben());


        }
    };
}