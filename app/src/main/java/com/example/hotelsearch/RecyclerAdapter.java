package com.example.hotelsearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public  class RecyclerAdapter{}

/*public  class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>{
    private Context mContext;
    private List<Hotel> hotel;
    private RecyclerView.OnItemClickListener mListener;

    public RecyclerView(Context context, List<Hotel> uploads) {
        mContext = context;
        hotel = uploads;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_layout_home, parent, false);
        return new RecyclerView.RecyclerViewHolder(v);
    }


    @Override
    public void onBindViewHolder(RecyclerView.RecyclerViewHolder holder, int position) {
        Hotel currentTeacher = hotel.get(position);
        holder.tvRatings.setText(currentTeacher.getHotelRating());
        holder.tvLocation.setText(currentTeacher.getHotelLocation());
        holder.tvhotelName.setText(currentTeacher.getHotelName());
        holder.tvtagsList.setText(currentTeacher.getHotelListTag());
        Picasso.with(mContext)
                .load(currentTeacher.getImageUri())
                .placeholder(R.drawable.placeholder)
                .fit()
                .centerCrop()
                .into(holder.ivhotelImage);
    }

    @Override
    public int getItemCount() {
        return hotel.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        TextView tvRatings, tvLocation, tvhotelName, tvtagsList;
        ImageView  ivhotelImage;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            *//*nameTextView*//*
            tvRatings = itemView.findViewById(R.id.ratings);

            *//*descriptionTextView*//*
            tvLocation = itemView.findViewById(R.id.hotelCardView);

            *//*dateTextView*//*
            tvhotelName = itemView.findViewById(R.id.hotelName);

            *//*teacherImageView*//*
            tvtagsList = itemView.findViewById(R.id.tagsList);
            ivhotelImage = itemView.findViewById(R.id.hotelImage);


            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onShowItemClick(int position);
        void onDeleteItemClick(int position);
    }
}*/


