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
                passIntent.putExtra("location", currentHotel.getHotelLocation());
                passIntent.putExtra("name", currentHotel.getHotelName());
                passIntent.putExtra("ratings", currentHotel.getHotelRating());
                passIntent.putExtra("tagList", currentHotel.getHotelListTag());
                passIntent.putExtra("image", currentHotel.getImageUri());


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

        public TextView ratings, hotelLocation, hotelName, tagsList;
        public ImageView hotelImage;
        CardView clickedLayout;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ratings = itemView.findViewById(R.id.ratings);
            hotelLocation = itemView.findViewById(R.id.hotelLocation);
            hotelLocation = itemView.findViewById(R.id.hotelLocation);
            hotelName = itemView.findViewById(R.id.hotelName);
            tagsList = itemView.findViewById(R.id.tagsList);
            hotelImage = itemView.findViewById(R.id.hotelImage);
            clickedLayout = itemView.findViewById(R.id.hotelCard);

            /*itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);*/
        }

       /* @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem showItem = menu.add( Menu.NONE, 1, 1, "Show");
            MenuItem deleteItem = menu.add(Menu.NONE, 2, 2, "Delete");

            showItem.setOnMenuItemClickListener(this);
            deleteItem.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    switch (item.getItemId()) {
                        case 1:
                            mListener.onShowItemClick(position);
                            return true;
                        case 2:
                            mListener.onDeleteItemClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onShowItemClick(int position);
        void onDeleteItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
*/
    }
}