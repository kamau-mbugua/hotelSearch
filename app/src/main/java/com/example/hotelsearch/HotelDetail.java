package com.example.hotelsearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class HotelDetail extends AppCompatActivity {

    private static final int CALL_PERMISSION = 30;

    ImageView hotelImage;
    TextView ratings,tvHotelEmail,tvHotelPhone,hotelLocation,
    hotelNames,tagsList, tvHotelPrice,tvMapUrlLoaccation, tvHotelWebsite;

    String mratings,mtvHotelEmail,mtvHotelPhone,mhotelLocation,
            mhotelNames,mtagsList, mhotelImage, mhotelPrice, mhotelMapUrl,mhotelWebsite;
    WebView tvHotelDirection;



    private void initializeWidgets(){
        hotelImage = findViewById(R.id.hotelImage);
        ratings = findViewById(R.id.ratings);
        tvHotelEmail = findViewById(R.id.tvHotelEmail);
        tvHotelPhone = findViewById(R.id.tvHotelPhone);
        tvHotelDirection = findViewById(R.id.googleMapView);
        hotelLocation = findViewById(R.id.hotelLocation);
        hotelNames = findViewById(R.id.hotelName);
        tagsList = findViewById(R.id.tvHotelTagList);
        tvHotelPrice = findViewById(R.id.tvHotelPrice);
        tvMapUrlLoaccation = findViewById(R.id.tvMapUrlLoaccation);
        tvHotelWebsite = findViewById(R.id.tvHotelWebsite);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);
        initializeWidgets();
        recieveIntents();
        tvHotelEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mailing();

            }
        });

        tvHotelPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoning();

            }
        });

    }

    private void phoning() {
        if (Build.VERSION.SDK_INT >= 23){
            if (checkedPermission()){
//                Permission Already Granted
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
                phoneIntent.setData(Uri.parse("tel:" + mtvHotelPhone));
                startActivity(phoneIntent);
            }
            else {
                requestPermission();
            }
        }
        if (checkedPermission()){
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
            phoneIntent.setData(Uri.parse("tel:" + mtvHotelPhone));
            startActivity(phoneIntent);
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(HotelDetail.this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION);
    }

    private boolean checkedPermission() {
        int callPermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE);

        return callPermission == PackageManager.PERMISSION_GRANTED;
    }

    private void mailing() {
        Intent mailIntent = new Intent(Intent.ACTION_SEND);
        mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {mtvHotelEmail});
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, "REQUESTING FOR MECHANIC ASSISTANCE");
        mailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(mailIntent, "Choose Mail Client"));
    }



    private void recieveIntents() {
        if (getIntent().hasExtra("hotelLocation") && getIntent().hasExtra("hotelName") && getIntent().hasExtra("hotelRating")
                && getIntent().hasExtra("hotelListTag") && getIntent().hasExtra("imageUri") && getIntent().hasExtra("email")
                && getIntent().hasExtra("phone")  && getIntent().hasExtra("mapUrl") && getIntent().hasExtra("websiteUrl")
                && getIntent().hasExtra("hotelPricePerHour")) {

            mhotelLocation = getIntent().getStringExtra("hotelLocation");
            mhotelNames = getIntent().getStringExtra("hotelName");
            mtvHotelEmail = getIntent().getStringExtra("hotelRating");
            mhotelImage = getIntent().getStringExtra("hotelListTag");
            mtvHotelPhone = getIntent().getStringExtra("imageUri");
            mtagsList = getIntent().getStringExtra("email");
            mratings = getIntent().getStringExtra("phone");
            mhotelPrice = getIntent().getStringExtra("mapUrl");
            mhotelMapUrl = getIntent().getStringExtra("websiteUrl");
            mhotelWebsite = getIntent().getStringExtra("hotelPricePerHour");

            provision(mhotelLocation,mhotelNames,mtvHotelEmail,mhotelImage,
                    mtvHotelPhone,mtagsList, mratings, mhotelPrice,  mhotelMapUrl,mhotelWebsite);
        }
    }

    private void provision(String mratings, String mtvHotelEmail, String mtvHotelPhone, String mhotelLocation, String mhotelNames, String mtagsList, String mhotelImage, String mhotelPrice, String mhotelMapUrl,String mhotelWebsite) {
        hotelLocation.setText(mhotelLocation);
        hotelNames.setText(mhotelNames);
        tvHotelEmail.setText(mtvHotelEmail);
        tvHotelPhone.setText(mtvHotelPhone);
        tagsList.setText(mtagsList);
        ratings.setText(mratings);
        tvHotelPrice.setText(mhotelPrice);
        tvMapUrlLoaccation.setText(mhotelMapUrl);
        tvHotelWebsite.setText(mhotelWebsite);
        //hotelLocation.setText(hotelLocation);

        Picasso.get().load(mhotelImage).placeholder(R.drawable.placeholder)
                .into(hotelImage);

        Toast.makeText(this, "Url : " + mhotelImage, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CALL_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission For Calling has been Accepted...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission For Calling has been Denied...", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}