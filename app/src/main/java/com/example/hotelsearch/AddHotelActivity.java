package com.example.hotelsearch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

public class AddHotelActivity extends AppCompatActivity {

    ImageView ivBack, hotelImage;
    EditText etLocation, etHotelName, etRating, etTagList, etPrice, etPhone,etWeb,etMapUrl,etEmail;
    Button btnSave;
    TextView tvUpload;
    Uri image_uri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private static final String TAG = "AddHotelActivity";

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;


    private StorageTask mUploadTask;
    private ProgressBar mProgress;

    Hotel hotel;

    long maxid = 0;

    String mhotelLocation, mhotelName, mhotelRating,mhotelPricePerHour,email,phone,mapUrl,webUrl, mhotelTagList;
   /* String etLocation1 = etLocation.getText().toString();
    String etHotelName1 = etHotelName.getText().toString();
    String etRating1 = etRating.getText().toString();
    String etTagList1 = etTagList.getText().toString();
    String etPrice1 = etPrice.getText().toString();*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hotel);

        storageReference = FirebaseStorage.getInstance().getReference("hotelProducts");
        databaseReference = FirebaseDatabase.getInstance().getReference("hotelProducts");

//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                if (snapshot.exists()){
//                    maxid=(snapshot.getChildrenCount());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        etEmail = findViewById(R.id.etEmail);
        ivBack = findViewById(R.id.ivBack);
        hotelImage = findViewById(R.id.hotelImage);
        etLocation = findViewById(R.id.etLocation);
        etHotelName = findViewById(R.id.etHotelName);
        etRating = findViewById(R.id.etRating);
        etTagList = findViewById(R.id.etTagList);
        etPrice = findViewById(R.id.etPrice);
        btnSave = findViewById(R.id.btnSave);
        tvUpload = findViewById(R.id.tvUpload);
        mProgress = findViewById(R.id.progressBar);
        etPhone = findViewById(R.id.etphone);
        etWeb = findViewById(R.id.etweb);
        etMapUrl = findViewById(R.id.etmapUrl);

        hotel = new Hotel();


        tvUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(AddHotelActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_CODE_STORAGE_PERMISSION);
                    } else {

                        selectImage();

                    }

                } else {
                    selectImage();
                }


            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receiveEntries();

                /*if (etLocation.getText().toString().isEmpty()) {
                    etLocation.setError("Location of The hotel is required.");
                } else if (etHotelName.getText().toString().isEmpty()) {
                    etHotelName.setError("Name of The hotel is required.");
                } else if (etRating.getText().toString().isEmpty()) {
                    etRating.setError("Rating of The hotel is required.");
                } else if (etTagList.getText().toString().isEmpty()) {
                    etTagList.setError("Tag List of The hotel is required.");
                } else if (etPrice.getText().toString().isEmpty()) {
                    etPrice.setError("Price per Hour of The hotel is required.");
                } else {

                    if (mUploadTask != null && mUploadTask.isInProgress()) {
                        Toast.makeText(AddHotelActivity.this, "An Upload is Still in Progress", Toast.LENGTH_SHORT).show();
                    } else {
                        uploadFile();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }*/

            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();

            }
        });
    }

    private void uploadFile() {

        if (image_uri != null) {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(image_uri));

            /*uploadProgressBar.setVisibility(View.VISIBLE);
            uploadProgressBar.setIndeterminate(true);*/

            UploadTask uploadTask = fileReference.putFile(image_uri);
            Toast.makeText(this, "UP " + uploadTask, Toast.LENGTH_SHORT).show();

            // Register observers to listen for when the download is done or if it fails

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddHotelActivity.this, "Fail...", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AddHotelActivity.this, "Success...", Toast.LENGTH_LONG).show();

                    if (taskSnapshot.getMetadata() != null)
                        if (taskSnapshot.getMetadata().getReference() != null) {
                            Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Toast.makeText(AddHotelActivity.this, "Proceed...", Toast.LENGTH_LONG).show();
                                    String sImage = uri.toString();

                                    mProgress.setVisibility(View.VISIBLE);
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mProgress.setProgress(0);
                                        }
                                    }, 500);
                                    Toast.makeText(AddHotelActivity.this, "Upload Successful..." + sImage, Toast.LENGTH_SHORT).show();

                                    hotel = new Hotel(mhotelLocation, mhotelName, mhotelRating,mhotelPricePerHour,email,phone,mapUrl,webUrl, mhotelTagList, sImage);
                                    String key = databaseReference.push().getKey();
                                    hotel.setID(key);
                                    databaseReference.child(key).setValue(hotel);

                                    Toast.makeText(AddHotelActivity.this, "Success Key retention...", Toast.LENGTH_LONG).show();
                                    mProgress.setVisibility(View.INVISIBLE);
                                    backToProfile(mhotelLocation, mhotelName, mhotelRating, mhotelTagList, mhotelPricePerHour, sImage);
                                    etHotelName.setText("");
                                    etLocation.setText("");
                                    etPrice.setText("");
                                    etTagList.setText("");
                                    Picasso.get().load("null").placeholder(R.drawable.placeholder).into(hotelImage);
                                }
                            });
                            result.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    mProgress.setVisibility(View.INVISIBLE);
                                    Toast.makeText(AddHotelActivity.this, "Database Fail...", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                }
            });


        }
        else {
            Toast.makeText(this, "Image Url Missing", Toast.LENGTH_SHORT).show();
        }


    }

    private void backToProfile(String mhotelLocation, String mhotelName, String mhotelRating, String mhotelTagList, String mhotelPricePerHour, String sImage) {

    }


    private void receiveEntries() {

        mhotelLocation = etLocation.getText().toString().trim();
        mhotelName = etHotelName.getText().toString().trim();
        mhotelRating= etRating.getText().toString().trim();
        mhotelPricePerHour= etPrice.getText().toString().trim();
        email= etEmail.getText().toString().trim();
        phone= etPhone.getText().toString().trim();
        mapUrl= etMapUrl.getText().toString().trim();
        webUrl= etWeb.getText().toString().trim();
        mhotelTagList=etTagList.getText().toString().trim();

        checkFields();
    }

    private void checkFields() {

        if (etLocation.getText().toString().isEmpty()) {
                    etLocation.setError("Location of The hotel is required.");
                } else if (etHotelName.getText().toString().isEmpty()) {
                    etHotelName.setError("Name of The hotel is required.");
                } else if (etRating.getText().toString().isEmpty()) {
                    etRating.setError("Rating of The hotel is required.");
                } else if (etTagList.getText().toString().isEmpty()) {
                    etTagList.setError("Tag List of The hotel is required.");
                } else if (etPrice.getText().toString().isEmpty()) {
                    etPrice.setError("Price per Hour of The hotel is required.");

                } else {

                    if (mUploadTask != null && mUploadTask.isInProgress()) {
                        Toast.makeText(AddHotelActivity.this, "An Upload is Still in Progress", Toast.LENGTH_SHORT).show();
                    } else {

                        if (isNetworkConnected()) {
                            uploadFile();


                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                        else {

                            Toast.makeText(AddHotelActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }


                    }
                }

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();

    }

    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage();
            } else {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            image_uri = data.getData();

            Picasso.get().load(image_uri).into(hotelImage);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }




    /*private void openImagesActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }*/
}