package com.example.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class lreport extends AppCompatActivity {

    private ArrayList<bookings> bl;
    private EditText ET;
    private Button search;
    private TextView t;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.MakeB) {
            Intent intent = new Intent();
            intent.setClass(lreport.this, MainActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.bookingD) {
            Intent intent = new Intent();
            intent.setClass(lreport.this, dreport.class);
            intent.putParcelableArrayListExtra("bookings", bl);
            intent.setClass(lreport.this, dreport.class);
            startActivity(intent);
        } else {
            return super.onOptionsItemSelected(item);
        }


        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lreport);

        t = findViewById(R.id.tv);

        Intent i = getIntent();
        bl = i.getParcelableArrayListExtra("bookings");

        ET = findViewById(R.id.tLicence);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        search = findViewById(R.id.button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String DR = ET.getText().toString();
                if(DR.equals("")){
                    ET.setError("Please enter a driver licence.");
                }
                else {
                    t.setText("");
                    String K = GetText(DR, bl);
                    K = K.replace("/n", System.getProperty("line.separator"));
                    t.setText(K);
                    ET.setText("");
                }

            }


        });

    }

    //get all the bookings details whose driver licence are "licence" from arraylist parent
    public String GetText(String licence, ArrayList<bookings> parent) {
        ArrayList<bookings> books = new ArrayList<>();
        String s = "";
        if (!parent.isEmpty() && !licence.equals("")) {
            Log.i("getBookings", "if");
                for (bookings b : parent) {
                    String l = b.getDriverLicence();
                    if (l.equals(licence)) {
                        books.add(b);
                    }
                }
                // Log.i("BOOKINGK",String.valueOf(bookings.size()));
                if (!books.isEmpty()) {
                    for (bookings b : books) {
                        String L = "";
                        String dl = b.getDriverLicence();
                        if (dl.equals(licence)) {
                            L = dl + "   " + b.getDate() + "   " + String.valueOf(b.getTime()) + ":00" + "/n";
                        }
                        s = s + L;
                        //Log.i("s", s);
                    }
                }
        }
        return s;
    }

    }
