package com.example.tetovaloidopontfoglalo.Model;

public class Idopont {

    private String Intent, Bovebben, Date, Time;
    private String useremail;

    public Idopont(){}

    public Idopont(String date) {
        Date = date;
    }

    public Idopont(String intent, String bovebben, String date,String time) {
        Intent = intent;
        Bovebben = bovebben;
        Date = date;
        Time =time;
    }

    public Idopont(String intent, String bovebben, String date, String time, String useremail) {
        Intent = intent;
        Bovebben = bovebben;
        Date = date;
        Time = time;
        this.useremail = useremail;
    }


    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getIntent() {
        return Intent;
    }

    public void setIntent(String intent) {
        Intent = intent;
    }

    public String getBovebben() {
        return Bovebben;
    }

    public void setBovebben(String bovebben) {
        Bovebben = bovebben;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
