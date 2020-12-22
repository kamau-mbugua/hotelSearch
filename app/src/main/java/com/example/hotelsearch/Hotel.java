package com.example.hotelsearch;

public class Hotel {
    public  String hotelLocation;
    public String hotelName;
    public String imageUri;
    public  String hotelRating;
    public String hotelListTag;
    public String hotelPricePerHour;


    public Hotel() {

    }

    public Hotel(String hotelLocation, String hotelName, String imageUri, String hotelRating, String hotelListTag, String hotelPricePerHour) {
        this.hotelLocation = hotelLocation;
        this.hotelName = hotelName;
        this.imageUri = imageUri;
        this.hotelRating = hotelRating;
        this.hotelListTag = hotelListTag;
        this.hotelPricePerHour = hotelPricePerHour;

    }



    public String getHotelLocation() {
        return hotelLocation;
    }

    public void setHotelLocation(String hotelLocation) {
        this.hotelLocation = hotelLocation;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getHotelRating() {
        return hotelRating;
    }

    public void setHotelRating(String hotelRating) {
        this.hotelRating = hotelRating;
    }

    public String getHotelListTag() {
        return hotelListTag;
    }

    public void setHotelListTag(String hotelListTag) {
        this.hotelListTag = hotelListTag;
    }

    public String getHotelPricePerHour() {
        return hotelPricePerHour;
    }

    public void setHotelPricePerHour(String hotelPricePerHour) {
        this.hotelPricePerHour = hotelPricePerHour;
    }
}
