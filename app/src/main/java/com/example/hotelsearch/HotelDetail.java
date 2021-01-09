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
        if (getIntent().hasExtra("hotelName") && getIntent().hasExtra("hotelLocation") /*&& getIntent().hasExtra("mail")*/
                && getIntent().hasExtra("imageUri") /*&& getIntent().hasExtra("phone")*/ && getIntent().hasExtra("hotelRating")&& getIntent().hasExtra("hotelListTag")) {

            mhotelLocation = getIntent().getStringExtra("hotelLocation");
            mhotelNames = getIntent().getStringExtra("hotelName");
            //mtvHotelEmail = getIntent().getStringExtra("mail");
            mhotelImage = getIntent().getStringExtra("imageUri");
           // mtvHotelPhone = getIntent().getStringExtra("phone");
            mtagsList = getIntent().getStringExtra("hotelListTag");
            mratings = getIntent().getStringExtra("hotelRating");

            provision(mratings,mtvHotelEmail,mtvHotelPhone,mhotelLocation,
                    mhotelNames,mtagsList, mhotelImage);
        }
    }

    private void provision(String mratings, String mtvHotelEmail, String mtvHotelPhone, String mhotelLocation, String mhotelNames, String mtagsList, String mhotelImage) {
        ratings.setText(mratings);
        //tvHotelEmail.setText(mtvHotelEmail);
        //tvHotelPhone.setText(mtvHotelPhone);
        hotelLocation.setText(mhotelLocation);
        hotelNames.setText(mhotelNames);
        tagsList.setText(mtagsList);
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