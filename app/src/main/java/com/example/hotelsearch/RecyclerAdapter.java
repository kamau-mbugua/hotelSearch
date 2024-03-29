package com.example.hotelsearch;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
public  class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    private static final String TAG = "hotelAdapter";
    private Context mContext;
    private List<Hotel> hotels;
    // private OnItemClickListener mListener;

    public RecyclerAdapter(Context context, List<Hotel> uploads) {
        mContext = context;
        hotels = uploads;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder:new View ...");
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_layout_home, parent, false);
        RecyclerViewHolder hotelView = new RecyclerViewHolder(v);

        Log.d(TAG, "onCreateViewHolder: view created...");
        return hotelView;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: align to recycler...");
        final Hotel currentHotel = hotels.get(position);

        holder.hotelLocation.setText("Location :" + currentHotel.getHotelLocation());
        holder.hotelName.setText("Hotel Name :" + currentHotel.getHotelName());
        holder.ratings.setText("Ratings :" + currentHotel.getHotelRating());
        holder.tagsList.setText("TagList :" + currentHotel.getHotelListTag());
        holder.tvPrice.setText("Price per Day: Ksh."+" "+currentHotel.getHotelPricePerHour());
        holder.tvMapUrl.setText(currentHotel.getMapUrl());
        holder.tvPhone.setText(currentHotel.getPhone());
        holder.tvemail.setText(currentHotel.getEmail());
        holder.tvwebsite.setText(currentHotel.getWebsiteUrl());


        Picasso.get()
                .load(currentHotel.getImageUri())
                .placeholder(R.drawable.placeholder)
                .fit()
                .centerCrop()
                .into(holder.hotelImage);


        holder.clickedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passIntent = new Intent(mContext, HotelDetail.class);
                passIntent.putExtra("hotelLocation1", currentHotel.getHotelLocation());
                passIntent.putExtra("hotelName1", currentHotel.getHotelName());
                passIntent.putExtra("hotelRating1", currentHotel.getHotelRating());
                passIntent.putExtra("hotelListTag1", currentHotel.getHotelListTag());
                passIntent.putExtra("imageUri1", currentHotel.getImageUri());
                passIntent.putExtra("email1", currentHotel.getEmail());
                passIntent.putExtra("phone1", currentHotel.getPhone());
                passIntent.putExtra("mapUrl1", currentHotel.getMapUrl());
                passIntent.putExtra("websiteUrl1", currentHotel.getWebsiteUrl());
                passIntent.putExtra("hotelPricePerHour1", currentHotel.getHotelPricePerHour());


                mContext.startActivity(passIntent);
                Log.d(TAG, "onClick: detail view...");
            }
        });
        Log.d(TAG, "onBindViewHolder: done binding....");
    }

    @Override
    public int getItemCount() {
        //return hotels.size();

        Log.d(TAG, "getItemCount: Counting method...");
        if (hotels != null) {
            Log.d(TAG, "getItemCount: Done counting for list...");
            return hotels.size();
        }
        Log.d(TAG, "getItemCount: Done counting for non-list...");
        return 0;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView ratings, hotelLocation, hotelName, tagsList, tvPhone, tvemail,tvwebsite,tvMapUrl,tvPrice;
        public ImageView hotelImage;
        CardView clickedLayout;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ratings = itemView.findViewById(R.id.ratings);
            hotelLocation = itemView.findViewById(R.id.hotelLocation);
            hotelName = itemView.findViewById(R.id.hotelName);
            tagsList = itemView.findViewById(R.id.tagsList);
            hotelImage = itemView.findViewById(R.id.hotelImage);
            clickedLayout = itemView.findViewById(R.id.hotelCard);
            tvMapUrl = itemView.findViewById(R.id.tvMapUrl);
            tvPhone= itemView.findViewById(R.id.tvPhone);
            tvemail=itemView.findViewById(R.id.tvemail);
            tvwebsite = itemView.findViewById(R.id.tvwebsite);
            tvPrice = itemView.findViewById(R.id.tvPrice);



        }
    }
}