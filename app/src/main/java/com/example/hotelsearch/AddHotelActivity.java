package com.example.hotelsearch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    EditText etLocation, etHotelName, etRating, etTagList, etPrice;
    Button btnSave;
    TextView tvUpload;
    Uri image_uri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private static final String TAG = "AddHotelActivity";

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;


    private StorageTask mUploadTask;

    long maxid = 0;

    String downloadUrl1;
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


        ivBack = findViewById(R.id.ivBack);
        hotelImage = findViewById(R.id.hotelImage);
        etLocation = findViewById(R.id.etLocation);
        etHotelName = findViewById(R.id.etHotelName);
        etRating = findViewById(R.id.etRating);
        etTagList = findViewById(R.id.etTagList);
        etPrice = findViewById(R.id.etPrice);
        btnSave = findViewById(R.id.btnSave);
        tvUpload = findViewById(R.id.tvUpload);


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
                        uploadFile();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }

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


    private void uploadFile() {

        if (image_uri != null) {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(image_uri));

            /*uploadProgressBar.setVisibility(View.VISIBLE);
            uploadProgressBar.setIndeterminate(true);*/

            mUploadTask = fileReference.putFile(image_uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    /*uploadProgressBar.setVisibility(View.VISIBLE);
                                    uploadProgressBar.setIndeterminate(false);
                                    uploadProgressBar.setProgress(0);*/
                                }
                            }, 500);

                            Toast.makeText(AddHotelActivity.this, "Teacher Upload successful", Toast.LENGTH_LONG).show();
                            Hotel upload = new Hotel(etLocation.getText().toString().trim(),
                                    etHotelName.getText ().toString ().trim(),
                                   taskSnapshot.getUploadSessionUri().toString(),
                                   // taskSnapshot.getMetadata().getReference().getDownloadUrl().toString(),
                                   // taskSnapshot.getDownloadUrl().toString(),
                                    etRating.getText ().toString ().trim(),
                                    etTagList.getText ().toString ().trim(),
                                    etPrice.getText ().toString ().trim());


                            String uploadId = databaseReference.push().getKey();
                            databaseReference.child(uploadId).setValue(upload);

                            /* uploadProgressBar.setVisibility(View.INVISIBLE);*/
                         //   openImagesActivity();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                          /*  uploadProgressBar.setVisibility(View.INVISIBLE);*/
                            Toast.makeText(AddHotelActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                           /* uploadProgressBar.setProgress((int) progress);*/
                        }
                    });
        }


    }

    /*private void openImagesActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }*/
}