package com.example.assignment1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class dreport extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private Button bc;
    private Date sD;
    private ArrayList<bookings> bl;
    private  ArrayList<bookings> WeekBookings;
    private int n, flg = 0;
    private String date = "";
    private  Calendar C;
    private ArrayList<String> WeekD;
    private TableLayout Table;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater ();
        inflater.inflate ( R.menu.main,menu );

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.MakeB){
            Intent intent = new Intent();
            intent.setClass(dreport.this, MainActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.bookingsL){
            Intent intent = new Intent();
            intent.setClass(dreport.this, lreport.class);
            intent.putParcelableArrayListExtra("bookings", bl);
            intent.setClass(dreport.this, lreport.class);
            startActivity(intent);
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }


        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dreport);
        Intent i = getIntent();
        bl = i.getParcelableArrayListExtra("bookings");
       // Log.i("mainArray", String.valueOf(bl.size()));
        //Log.i("TAG", "TRUE");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Table = findViewById(R.id.table);

        bc = findViewById(R.id.btnca);
        bc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogFragment dateppicker = new datepickerF();
                dateppicker.show(getSupportFragmentManager(), "select date");
            }


        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        C = Calendar.getInstance();
        C.set(Calendar.YEAR, year);
        C.set(Calendar.MONTH, month);
        C.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        sD = C.getTime();
        date = DateFormat.getDateInstance(DateFormat.FULL).format(sD);
        bc.setText(date);
        n =  C.get(C.DAY_OF_WEEK);
        //Log.i("weekDay", String.valueOf(n));
        if(n == 1 || n == 7){
            alert_calender_select al = new alert_calender_select();
            al.show(getSupportFragmentManager(), " ");
            bc.setText("Calender");
        }
        else{
            if(!bl.isEmpty()){
                WeekD = week_of_day();
                if(!WeekD.isEmpty()){
                    WeekBookings = WD(bl, WeekD);
                    //Log.i("Mon", WeekBookings.get(0).getDate());
                    fillTabel(Table,WeekD, WeekBookings,5,8);
            }
        }
        }

    }

    //get the dates of the other week days of the selected date
    public ArrayList<String> week_of_day(){
        ArrayList<String> week = new ArrayList<>();
        switch (n){
            //selected date is Mon
            case 2:
                for(int m = 0; m<5; m++){
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(C.get(Calendar.YEAR), C.get(Calendar.MONTH), C.get(Calendar.DAY_OF_MONTH));
                    calendar.add(Calendar.DATE, m);
                    Date weekDay = calendar.getTime();
                    String day = DateFormat.getDateInstance(DateFormat.FULL).format(weekDay);
                    week.add(day);
                }
                break;
            //selected date is Tue
            case 3:
                for(int m = -1; m<4; m++){
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(C.get(Calendar.YEAR), C.get(Calendar.MONTH), C.get(Calendar.DAY_OF_MONTH));
                    calendar.add(Calendar.DATE, m);
                    Date weekDay = calendar.getTime();
                    String day = DateFormat.getDateInstance(DateFormat.FULL).format(weekDay);
                    week.add(day);
                }
                break;
            //selected date is Wed
            case 4:
                for(int m = -2; m<3; m++){
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(C.get(Calendar.YEAR), C.get(Calendar.MONTH), C.get(Calendar.DAY_OF_MONTH));
                    calendar.add(Calendar.DATE, m);
                    Date weekDay = calendar.getTime();
                    String day = DateFormat.getDateInstance(DateFormat.FULL).format(weekDay);
                    week.add(day);
                }
                break;
            //selected date is Thu
            case 5:
                for(int m = -3; m<2; m++){
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(C.get(Calendar.YEAR), C.get(Calendar.MONTH), C.get(Calendar.DAY_OF_MONTH));
                    calendar.add(Calendar.DATE, m);
                    Date weekDay = calendar.getTime();
                    String day = DateFormat.getDateInstance(DateFormat.FULL).format(weekDay);
                    week.add(day);
                }
                break;
            //selected date is Fri
            case 6:
                for(int m = -4; m<1; m++){
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(C.get(Calendar.YEAR), C.get(Calendar.MONTH), C.get(Calendar.DAY_OF_MONTH));
                    calendar.add(Calendar.DATE, m);
                    Date weekDay = calendar.getTime();
                    String day = DateFormat.getDateInstance(DateFormat.FULL).format(weekDay);
                    week.add(day);
                }
                break;

        }

        return week;
    }

    //get the bookings,which the dates are the selected date or the other weekdays of the selected date, from all the bookings
    //arraylist Parent is the arraylist that we want to get the bookings from
    //arraylist Child stores the dates of the bookings we want to get
    public ArrayList<bookings> WD (ArrayList<bookings> Parent, ArrayList<String> Child){
        ArrayList<bookings> WDS = new ArrayList<>();
        if(!Parent.isEmpty()){
            for (int i = 0; i<Child.size(); i++){
                for (int n = 0; n < Parent.size(); n++){
                    bookings b = Parent.get(n);
                    String DATE = b.getDate();
                    //Log.i("Date", DATE);
                    //Log.i("Child date", Child.get(i));
                    if(Child.get(i).equals(DATE)){
                        WDS.add(b);
                    }
                }
            }
        }
        //Log.i("CHILD", WDS.get(0).getDate());
        return WDS;
    }

    //using a 2D arry to store the amount of each timeslot from different date
    //arraylist weekD is the dates we've got from the selected date
    //arraylist weekbookings is the all the bookings whose Date are in weekD
    public  int[][] fillTabelR(ArrayList<String>weekD, ArrayList<bookings> weekBookings, int row, int column){
        int[][] tab = new int[row][column];
        if(!weekBookings.isEmpty()){
            for(int i = 0; i<row; i++){
                int m = 9;
                for(int l = 0; l<column; l++){
                    int count = 0;
                    for(bookings b:weekBookings){
                        String bookingd = b.getDate();
                        int ti = b.getTime();
                        if(bookingd.equals(weekD.get(i)) && ti == m) {
                               count++;
                    }
                }
                    m++;
                    tab[i][l] = count;
                }

            }
        }
       // Log.i("FRI:10:", String.valueOf(tab[4][1]));
       return tab;
    }

    //using the 2D array created before to fill out the table
    // Tablelayout T is the table we want to filled
    //arraylist weekD is the dates we've got from the selected date
    //arraylist weekbookings is the all the bookings whose Date are in weekD
    public void fillTabel(TableLayout T,ArrayList<String>weekD, ArrayList<bookings> weekBookings,int TableRowMinus1, int TableColumnMinus1){
        int[][] A = fillTabelR(weekD,weekBookings, TableRowMinus1, TableColumnMinus1);
        for (int i = 1; i < T.getChildCount(); i++){
            TableRow tr = (TableRow)T.getChildAt(i);
            for(int f = 1; f<tr.getChildCount();f++){
                TextView TV = (TextView)tr.getChildAt(f);
                if (A[i-1][f-1]==10) {
                    //change the background color to red if the amount of bookings is 10
                    TV.setBackgroundColor(Color.RED);
                }
                else if(A[i-1][f-1]<10 && A[i-1][f-1]>5){
                    //change the background color to yellow if the amount of bookings is lower the 10 and lager than 5
                    TV.setBackgroundColor(Color.YELLOW);
                }
                TV.setText(String.valueOf(A[i-1][f-1]));
            }
        }
    }
}
