package com.example.hotelsearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class HotelDetail extends AppCompatActivity {

    ImageView hotelImage;
    TextView ratings,tvHotelEmail,tvHotelPhone,hotelLocation,
    hotelNames,tagsList;

    String mratings,mtvHotelEmail,mtvHotelPhone,mhotelLocation,
            mhotelNames,mtagsList, mhotelImage;
    WebView tvHotelDirection;



    private void initializeWidgets(){
        hotelImage = findViewById(R.id.hotelImage);
        ratings = findViewById(R.id.ratings);
        tvHotelEmail = findViewById(R.id.tvHotelEmail);
        tvHotelPhone = findViewById(R.id.tvHotelPhone);
        tvHotelDirection = findViewById(R.id.googleMapView);
        hotelLocation = findViewById(R.id.hotelLocation);
        hotelNames = findViewById(R.id.hotelName);
        tagsList = findViewById(R.id.tagsList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);
        initializeWidgets();
        recieveIntents();

        //RECEIVE DATA FROM ITEMSACTIVITY VIA INTENT
       /* Intent i=this.getIntent();
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
        Picasso.get()
                .load(imageURL)
                .placeholder(R.drawable.placeholder)
                .fit()
                .centerCrop()
                .into(hotelImage);*/

    }

    private void recieveIntents() {
        if (getIntent().hasExtra("name") && getIntent().hasExtra("location") && getIntent().hasExtra("mail")
                && getIntent().hasExtra("image") && getIntent().hasExtra("phone") && getIntent().hasExtra("speciality")) {

            mhotelLocation = getIntent().getStringExtra("name");
            mhotelNames = getIntent().getStringExtra("location");
            mtvHotelEmail = getIntent().getStringExtra("mail");
            mhotelImage = getIntent().getStringExtra("image");
            mtvHotelPhone = getIntent().getStringExtra("phone");
            mratings = getIntent().getStringExtra("speciality");

            provision(mratings,mtvHotelEmail,mtvHotelPhone,mhotelLocation,
                    mhotelNames,mtagsList, mhotelImage);
        }
    }

    private void provision(String mratings, String mtvHotelEmail, String mtvHotelPhone, String mhotelLocation, String mhotelNames, String mtagsList, String mhotelImage) {
        ratings.setText(mratings);
        tvHotelEmail.setText(mtvHotelEmail);
        tvHotelPhone.setText(mtvHotelPhone);
        hotelLocation.setText(mtvHotelPhone);
        hotelNames.setText(mhotelNames);
        tagsList.setText(mtagsList);
        //hotelLocation.setText(hotelLocation);

        Picasso.get().load(mhotelImage).placeholder(R.drawable.placeholder)
                .into(hotelImage);

        Toast.makeText(this, "Url : " + mhotelImage, Toast.LENGTH_SHORT).show();

    }
}