package com.panickapps.parking;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final Parking A1 = new Parking("A1", ParkingStatus.FREE);
    private final Parking A2 = new Parking("A2", ParkingStatus.FREE);
    private final Parking A3 = new Parking("A3", ParkingStatus.FREE);
    private final Parking A4 = new Parking("A4", ParkingStatus.FREE);
    private final Parking A5 = new Parking("A5", ParkingStatus.FREE);
    private final Parking A6 = new Parking("A6", ParkingStatus.FREE);
    private final Parking[] parkingList = {
            A1,
            A2,
            A3,
            A4,
            A5,
            A6
    };

    private TextView parkA1;
    private TextView parkA2;
    private TextView parkA3;
    private TextView parkA4;
    private TextView parkA5;
    private TextView parkA6;
    private TextView[] textViewList = new TextView[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parkA1 = findViewById(R.id.park_A1);
        parkA2 = findViewById(R.id.park_A2);
        parkA3 = findViewById(R.id.park_A3);
        parkA4 = findViewById(R.id.park_A4);
        parkA5 = findViewById(R.id.park_A5);
        parkA6 = findViewById(R.id.park_A6);

        textViewList[0] = parkA1;
        textViewList[1] = parkA2;
        textViewList[2] = parkA3;
        textViewList[3] = parkA4;
        textViewList[4] = parkA5;
        textViewList[5] = parkA6;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        for (int i = 0; i < parkingList.length; i++) {
            final int counter = i;
            DatabaseReference reference = database.getReference(parkingList[i].getId());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        ParkingStatus status = dataSnapshot.getValue(ParkingStatus.class);
                        if (status == null) {
                            System.err.println("Status is null");
                            textViewList[counter].setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.red));
                        }
                        else {
                            System.out.println(parkingList[counter].getId() + " ==> " + status);
                            switch (status) {
                                case FREE:
                                    textViewList[counter].setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.transparent));
                                    break;
                                case TAKEN:
                                    textViewList[counter].setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.red));
                                    break;
                                default:
                                    System.err.println("Invalid status '" + status + "'.");
                                    textViewList[counter].setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.red));
                                    break;
                            }
                        }
                    }
                    catch (Exception e) {
                        System.err.println("Value is not a status!");
                        textViewList[counter].setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.red));
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    System.err.println("error fetching value!");
                }
            });
        }


//        // Read from the database
//        xxx.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d("Park", "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w("Park", "Failed to read value.", error.toException());
//            }
//        });

    }
}
