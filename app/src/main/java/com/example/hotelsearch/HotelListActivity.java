package com.example.hotelsearch;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class HotelListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter mAdapter;
    private FirebaseStorage storage;
    private DatabaseReference mDatabase;
    private ValueEventListener mDBListener;
    List<Hotel> mHotel;
    List<Hotel> filteredmHotel;
    LinearLayoutManager layoutManager;
    EditText searchText;
    ProgressBar circleP_bar;
    TextView defaultView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list);

        recyclerView = findViewById(R.id.recyclerVw);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        defaultView = findViewById(R.id.defaultView);

        searchText = findViewById(R.id.editTextSearch);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().isEmpty()) {

//                    productSearch(s.toString());
                    settAdapter(s.toString());
                } else {
                    mAdapter = new RecyclerAdapter(HotelListActivity.this, mHotel);
                    recyclerView.setAdapter(mAdapter);
                }

            }
        });
        circleP_bar = findViewById(R.id.progressBarCircle);
        //        Obtaining reference to the firebase database
        mDatabase = FirebaseDatabase.getInstance().getReference("hotelProducts");

        storage = FirebaseStorage.getInstance();

        mHotel = new ArrayList<>();
        filteredmHotel = new ArrayList<>();

        //       Initializing and Setting up of layout Manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

//        Initializing and Setting up of adapter
        mAdapter  = new RecyclerAdapter(HotelListActivity.this, mHotel);
        recyclerView.setAdapter(mAdapter);

        if (isNetworkConnected()) {
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    mHotel.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Hotel model = postSnapshot.getValue(Hotel.class);
//                        model.setID(postSnapshot.getKey());

//                        if (Integer.valueOf(receivedProduct.getCapacity()) > 0) {

                        mHotel.add(model);
//                        } else {
//                            StorageReference storeRef = storage.getReferenceFromUrl(receivedProduct.getImage());

//                            storeRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    mDatabase.child(receivedProduct.getID()).removeValue();
//                                }
//                            })
//                                    .addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Toast.makeText(Products_view.this, "Error in Deletion", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                        }
                    }

                    if (mHotel.isEmpty()) {
                        defaultView.setVisibility(View.VISIBLE);
                        circleP_bar.setVisibility(View.INVISIBLE);
                    }

                    mAdapter.notifyDataSetChanged();
                    circleP_bar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Toast.makeText(HotelListActivity.this, "Permission Denied...", Toast.LENGTH_SHORT).show();
                    Toast.makeText(HotelListActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    circleP_bar.setVisibility(View.INVISIBLE);
                }
            });
        } else {
            circleP_bar.setVisibility(View.INVISIBLE);
            defaultView.setVisibility(View.VISIBLE);
            defaultView.setText(R.string.No_network);
            Toast.makeText(this, " Check out", Toast.LENGTH_LONG).show();
        }


    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();

    }

    private void settAdapter(String toString) {

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                filteredmHotel.clear();
                recyclerView.removeAllViews();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey();
                    String hotelLocation = snapshot.child("hotelLocation").getValue(String.class);
                    String hotelName = snapshot.child("hotelName").getValue(String.class);
                    String tagList = snapshot.child("hotelListTag").getValue(String.class);

//                    Products filteredProduct = snapshot.getValue(Products.class);
                    Hotel modelFiltered = snapshot.getValue(Hotel.class);

                    if (hotelLocation.equals(null)) {
                        Toast.makeText(HotelListActivity.this, "Location is null", Toast.LENGTH_SHORT).show();
                    } else {
                        if (hotelLocation.toLowerCase().contains(toString.toLowerCase())) {
//                            nameList.add(pName);
                            filteredmHotel.add(modelFiltered);
                        }

                        if (hotelName.equals(null)) {
                            Toast.makeText(HotelListActivity.this, "Hotel Name is Null", Toast.LENGTH_SHORT).show();
                        } else {
                            if (hotelName.toLowerCase().contains(toString.toLowerCase())) {
                                filteredmHotel.add(modelFiltered);
                            }

                            if (tagList.equals(null)) {
                                Toast.makeText(HotelListActivity.this, "Tag List is null", Toast.LENGTH_SHORT).show();
                            } else {
                                if (tagList.toLowerCase().contains(toString.toLowerCase())) {
                                    filteredmHotel.add(modelFiltered);
                                }
                            }
                        }
                    }
                }

                if (!filteredmHotel.isEmpty()) {
                    defaultView.setVisibility(View.INVISIBLE);
                    mAdapter = new RecyclerAdapter(HotelListActivity.this, filteredmHotel);
                    recyclerView.setAdapter(mAdapter);
                } else {
                    defaultView.setVisibility(View.VISIBLE);
                    defaultView.setText("No Products based on Your Search Criteria... Try Again");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
