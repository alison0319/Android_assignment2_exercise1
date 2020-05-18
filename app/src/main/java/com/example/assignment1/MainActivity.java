package com.example.assignment1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;




public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    private Button booking;
    private ArrayList<bookings> slots = new ArrayList<>();
    private Button bc;
    private RadioButton B9, B10, B11, B12, B13, B14, B15, B16;
    private RadioGroup rg;
    private int time = 0;
    private EditText driverL;
    private String date;
    private  String dl="";
    private int n;
    private TextView tv;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater ();
        inflater.inflate ( R.menu.main,menu );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.bookingD){
             Intent intent = new Intent(MainActivity.this, dreport.class);
             //pass all the bookings to dreport activity
             intent.putParcelableArrayListExtra("bookings", slots);
             intent.setClass(MainActivity.this, dreport.class);
             startActivity(intent);
        }
        else if(item.getItemId() == R.id.bookingsL){
            Intent intent = new Intent(MainActivity.this, lreport.class);
            //pass all the bookings to lreport activity
            intent.putParcelableArrayListExtra("bookings", slots);
            intent.setClass(MainActivity.this, lreport.class);
            startActivity(intent);

        }
        else
        {
            return super.onOptionsItemSelected(item);
        }


        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tvt);

        rg = findViewById(R.id.RG);

        bc = findViewById(R.id.btn);
        bc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogFragment dateppicker = new datepickerF();
                dateppicker.show(getSupportFragmentManager(), "select date");
            }


        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        driverL = findViewById(R.id.licence);


        B9 = findViewById(R.id.btn9);
        B10 = findViewById(R.id.btn10);
        B11 = findViewById(R.id.btn11);
        B12 = findViewById(R.id.btn12);
        B13 = findViewById(R.id.btn13);
        B14 = findViewById(R.id.btn14);
        B15 = findViewById(R.id.btn15);
        B16 = findViewById(R.id.btn16);

        B9.setOnClickListener(this);
        B10.setOnClickListener(this);
        B11.setOnClickListener(this);
        B12.setOnClickListener(this);
        B13.setOnClickListener(this);
        B14.setOnClickListener(this);
        B15.setOnClickListener(this);
        B16.setOnClickListener(this);


        booking = findViewById(R.id.btnB);

        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dl = driverL.getText().toString();
                //check if driver licence is null
                if(dl.equals("")){
                    driverL.setError("Please enter a driver licence.");
                }
                //check if date is null or weekend dates
                else if(bc.getText().toString().equals("Calendar") || n == 1 || n == 7){
                    alert_calender_select al = new alert_calender_select();
                    al.show(getSupportFragmentManager(), " ");
                    bc.setText("Calender");
                }
                //check if time is null
                else if(time == 0){
                    alert_time al = new alert_time();
                    al.show(getSupportFragmentManager(), " ");
                }
                // create a booking
                else{
                    bookings NewB = new bookings();
                    int flag = NewB.createBookint(dl, date, time, slots);

                    if(flag == 1){
                        alertDialog al = new alertDialog();
                        //Toast.makeText(this,"This slot is unavailable, please select another one", LENGTH_SHORT).show();
                        rg.clearCheck();
                        bc.setText("Calender");
                        driverL.setText("");
                        Log.i("tag:", "compare licence");
                        al.show(getSupportFragmentManager(), " ");
                    }
                    else if(flag == 2){
                        alertDialog al = new alertDialog();
                        rg.clearCheck();
                        bc.setText("Calender");
                        driverL.setText("");
                        // Log.i("tag:","bigger than 1");
                        al.show(getSupportFragmentManager(), " ");
                    }
                    else {
                        NewB.setDriverLicence(dl);
                        NewB.setDate(date);
                        NewB.setTime(time);
                        slots.add(NewB);
                        driverL.setText("");
                        bc.setText("Calender");
                        rg.clearCheck();
                        time=0;
                        //Log.i("tag:",String.valueOf(slots.size()));
                        dailog_success al = new dailog_success();
                        al.show(getSupportFragmentManager(), " ");
                    }

                }


            }
        });
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar C = Calendar.getInstance();
        C.set(Calendar.YEAR, year);
        C.set(Calendar.MONTH, month);
        C.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Date d = C.getTime();
        n = C.get(C.DAY_OF_WEEK);
        date = DateFormat.getDateInstance(DateFormat.FULL).format(d);
        bc.setText(date);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn9:
                time = 9;
                break;
            case R.id.btn10:
                time = 10;
                break;
            case R.id.btn11:
                time = 11;
                break;
            case R.id.btn12:
                time = 12;
                break;
            case R.id.btn13:
                time = 13;
                break;
            case R.id.btn14:
                time = 14;
                break;
            case R.id.btn15:
                time = 15;
                break;
            case R.id.btn16:
                time = 16;
                break;

        }
    }


}
