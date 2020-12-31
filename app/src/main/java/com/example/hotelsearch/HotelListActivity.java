package com.example.hotelsearch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class HotelListActivity extends AppCompatActivity implements RecyclerAdapter.OnItemClickListener{

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private ProgressBar mProgressBar;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private List<Hotel> mHotel;

    private void openDetailActivity(String[] data){
        Intent intent = new Intent(this, HotelDetail.class);
        intent.putExtra("NAME_KEY",data[0]);
        intent.putExtra("LOCATION_KEY",data[1]);
        intent.putExtra("RATINGS_KEY",data[2]);
        intent.putExtra("TAG_LIST_KEY",data[3]);
        intent.putExtra("IMAGE_KEY",data[4]);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_hotel_list );

        mRecyclerView = findViewById(R.id.recyclerVw);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

      /*  mProgressBar = findViewById(R.id.myDataLoaderProgressBar);
        mProgressBar.setVisibility(View.VISIBLE);*/

        mHotel = new ArrayList<>();
        mAdapter = new RecyclerAdapter (HotelListActivity.this, mHotel);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(HotelListActivity.this);

        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("hotelProducts");

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mHotel.clear();

                for (DataSnapshot hotelSnapshot : dataSnapshot.getChildren()){
                    Hotel upload = hotelSnapshot.getValue(Hotel.class);
                    upload.setKey(hotelSnapshot.getKey());
                    mHotel.add(upload);
                }
                mAdapter.notifyDataSetChanged();
              // mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HotelListActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                //mProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }
    public void onItemClick(int position) {
        Hotel clickedHotel=mHotel.get(position);
        String[] hotelData={clickedHotel.getHotelLocation(),clickedHotel.getHotelName(),clickedHotel.getHotelListTag(),clickedHotel.getHotelRating(),clickedHotel.getImageUri()};
        openDetailActivity(hotelData);
    }

    @Override
    public void onShowItemClick(int position) {
        Hotel clickedHotel=mHotel.get(position);
        String[] hotelData={clickedHotel.getHotelLocation(),clickedHotel.getHotelName(),clickedHotel.getHotelListTag(),clickedHotel.getHotelRating(),clickedHotel.getImageUri()};
        openDetailActivity(hotelData);
    }

    @Override
    public void onDeleteItemClick(int position) {
        Hotel selectedItem = mHotel.get(position);
        final String selectedKey = selectedItem.getKey();

        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageUri());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(HotelListActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });

    }
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }

}
