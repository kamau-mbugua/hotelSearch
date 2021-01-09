package com.example.hotelsearch;

public class Hotel {
    public  String hotelLocation;
    public String hotelName;
    public String imageUri;
    public  String hotelRating;
    public String hotelListTag;
    public String hotelPricePerHour;
    private String key;
    private String ID;
    private String email;
    private String phone;
    private String mapUrl;
    private String websiteUrl;

    public Hotel() {
    }

    public Hotel(String hotelLocation, String hotelName, String imageUri, String hotelRating, String hotelListTag, String hotelPricePerHour, String key, String ID, String email, String phone, String mapUrl, String websiteUrl) {
        this.hotelLocation = hotelLocation;
        this.hotelName = hotelName;
        this.imageUri = imageUri;
        this.hotelRating = hotelRating;
        this.hotelListTag = hotelListTag;
        this.hotelPricePerHour = hotelPricePerHour;
        this.key = key;
        this.ID = ID;
        this.email = email;
        this.phone = phone;
        this.mapUrl = mapUrl;
        this.websiteUrl = websiteUrl;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMapUrl() {
        return mapUrl;
    }

    public void setMapUrl(String mapUrl) {
        this.mapUrl = mapUrl;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    /*public Hotel() {

    }

    public Hotel(String hotelLocation, String hotelName, String imageUri, String hotelRating, String hotelListTag, String hotelPricePerHour) {
        this.hotelLocation = hotelLocation;
        this.hotelName = hotelName;
        this.imageUri = imageUri;
        this.hotelRating = hotelRating;
        this.hotelListTag = hotelListTag;
        this.hotelPricePerHour = hotelPricePerHour;

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }*/
}
