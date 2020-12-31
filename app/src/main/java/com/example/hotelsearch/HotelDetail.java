package com.example.hotelsearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class HotelDetail extends AppCompatActivity {

    ImageView hotelImage;
    TextView ratings,tvHotelEmail,tvHotelPhone,tvHotelDirection,hotelLocation,
    hotelNames,tagsList;


    private void initializeWidgets(){
        hotelImage = findViewById(R.id.hotelImage);
        ratings = findViewById(R.id.ratings);
        tvHotelEmail = findViewById(R.id.tvHotelEmail);
        tvHotelPhone = findViewById(R.id.tvHotelPhone);
        tvHotelDirection = findViewById(R.id.tvHotelDirection);
        hotelLocation = findViewById(R.id.hotelLocation);
        hotelNames = findViewById(R.id.hotelName);
        tagsList = findViewById(R.id.tagsList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);
        initializeWidgets();

        //RECEIVE DATA FROM ITEMSACTIVITY VIA INTENT
        Intent i=this.getIntent();
        String hotelName=i.getExtras().getString("NAME_KEY");
        String hotelLocations=i.getExtras().getString("LOCATION_KEY");
        String rating = i.getExtras().getString("RATINGS_KEY");
        String taglist = i.getExtras().getString("TAG_LIST_KEY");
        String imageURL=i.getExtras().getString("IMAGE_KEY");


        //SET RECEIVED DATA TO TEXTVIEWS AND IMAGEVIEWS
        hotelNames.setText( hotelLocations);
        hotelLocation.setText(hotelName);
        ratings.setText(taglist);
        tagsList.setText(rating);
        Picasso.with(this)
                .load(imageURL)
                .placeholder(R.drawable.placeholder)
                .fit()
                .centerCrop()
                .into(hotelImage);

    }
}