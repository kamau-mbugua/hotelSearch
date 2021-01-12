package com.example.hotelsearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    private  ImageView ivBack, ivhotelImage;
   private  TextView tvLocation, tvHotelName, tvRating, tvTagList, tvPrice, tvPhone,tvWeb,tvMapUrl,tvEmail;
    Button btnHome;
    ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvEmail = findViewById(R.id.tvEmail);
        ivBack = findViewById(R.id.ivBack);
        ivhotelImage = findViewById(R.id.hotelImage);
        tvLocation = findViewById(R.id.tvLocation);
        tvHotelName = findViewById(R.id.tvHotelName);
        tvRating = findViewById(R.id.tvRating);
        tvTagList = findViewById(R.id.tvTagList);
        tvPrice = findViewById(R.id.tvPrice);
        btnHome = findViewById(R.id.btnBackHome);

        mProgress = findViewById(R.id.progressBar);
        tvPhone = findViewById(R.id.tvphone);
        tvWeb = findViewById(R.id.tvweb);
        tvMapUrl = findViewById(R.id.tvmapUrl);

        personalProductsIntents();

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logIntent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(logIntent);
                return;
            }
        });
    }

    private void personalProductsIntents() {
        String mratings,mtvHotelEmail,mtvHotelPhone,mhotelLocation,
                mhotelNames,mtagsList, mhotelImage, mhotelPrice, mhotelMapUrl,mhotelWebsite;
        if (getIntent().hasExtra("hotelLocation1")
                && getIntent().hasExtra("hotelName1")
                && getIntent().hasExtra("hotelRating1")
                && getIntent().hasExtra("hotelListTag1")
                && getIntent().hasExtra("imageUri1")
                && getIntent().hasExtra("email1")
                && getIntent().hasExtra("phone1")
                && getIntent().hasExtra("mapUrl1")
                && getIntent().hasExtra("websiteUrl1")
                && getIntent().hasExtra("hotelPricePerHour1")) {

            mhotelLocation = getIntent().getStringExtra("hotelLocation1");
            mhotelNames = getIntent().getStringExtra("hotelName1");
            mratings = getIntent().getStringExtra("hotelRating1");
            mtagsList = getIntent().getStringExtra("hotelListTag1");
            mhotelImage = getIntent().getStringExtra("imageUri1");
            mtvHotelEmail = getIntent().getStringExtra("email1");
            mtvHotelPhone = getIntent().getStringExtra("phone1");
            mhotelMapUrl = getIntent().getStringExtra("mapUrl1");
            mhotelWebsite = getIntent().getStringExtra("websiteUrl1");
            mhotelPrice = getIntent().getStringExtra("hotelPricePerHour1");

            tvRating .setText(mratings);
            tvEmail .setText(mtvHotelEmail);
            tvPhone .setText(mtvHotelPhone);
            tvLocation.setText(mhotelLocation); ;
            tvHotelName .setText(mhotelNames);
            tvTagList .setText(mtagsList);
            tvPrice .setText("Ksh:"+mhotelPrice);
            tvMapUrl.setText(mhotelMapUrl); ;
            tvWeb.setText(mhotelWebsite);

            Picasso.get().load(mhotelImage).fit().placeholder(R.drawable.placeholder).into(ivhotelImage);

        }
    }
}