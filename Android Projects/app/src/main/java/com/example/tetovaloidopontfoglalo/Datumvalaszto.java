package com.example.tetovaloidopontfoglalo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class Datumvalaszto extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private DatePickerDialog datePickerDialog;
    private Button datebutton;
    private TextView Answer;
    private FirebaseUser user;
    private Spinner spinner;

    private String DATUM, IDO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datumvalaszto);
        initDatepicker();
        datebutton = findViewById(R.id.dikpicker);
        datebutton.setText(getTudayDate());
        user = FirebaseAuth.getInstance().getCurrentUser();



        spinner = findViewById(R.id.idospinner);

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.idopontok,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        DATUM = getTudayDate();



    }

    private String getTudayDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        month = month + 1;
        return makeDateString(day,month,year);
    }

    private void initDatepicker() {
        DatePickerDialog.OnDateSetListener dateSetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = makeDateString(day,month,year);
                datebutton.setText(date);
                DATUM = String.valueOf(datebutton.getText());
                //Answer.setText(datebutton.getText()+" Hát igen");
            }


        };
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_DEVICE_DEFAULT_DARK;
        datePickerDialog = new DatePickerDialog(this, style,dateSetListener,year,month,day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
    }

    private String makeDateString(int day, int month, int year) {
        return year +" "+getMonthFormat(month)+" "+day;
    }

    private String  getMonthFormat(int month) {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";
        //elvileg nem fordul elő
        return "JAN";
    }

    public void openDatepicker(View view) {
        datePickerDialog.show();
    }

    public void Cancel(View view) {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menubar, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logut:
                FirebaseAuth.getInstance().signOut();
                finish();
                return true;
            case R.id.idopontok:
                StartFoglalt();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void StartFoglalt(){
        Intent intent = new Intent(this,Foglalasok.class);
        startActivity(intent);
    }

    public void Add(View view) {
        Intent intent = new Intent(this,Intentteller.class);
        intent.putExtra("Datum", DATUM);
        intent.putExtra("Time", IDO);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        IDO = parent.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        IDO ="8:00-9:00";
    }
}