package com.example.hotelsearch;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ExtendedFloatingActionButton extended_fab;
    EditText editTextTextPersonName;
    Button btnSearch;


    public static List<Hotel> countryModelsList = new ArrayList<>();
    Hotel hotel;
    RecyclerAdapter myCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        extended_fab= findViewById(R.id.extended_fab);
        btnSearch = findViewById(R.id.btnSearch);
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HotelListActivity.class));
                finish();
            }
        });



        extended_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //implement the search bar of the countries
                /*editTextTextPersonName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        myCustomAdapter.getFilter().filter(s);
                        myCustomAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });*/


                startActivity(new Intent(getApplicationContext(), AddHotelActivity.class));
                finish();
                return;
            }
        });
    }
}