package com.example.huparidiary.model;

public class items {
    String name;
    String stars;
    String ratings;
    String ranks;
    String address;
    String phone;
    String status;
    String image;

    public items(String name, String stars, String ratings, String ranks, String address, String phone, String status, String image) {
        this.name = name;
        this.stars = stars;
        this.ratings = ratings;
        this.ranks = ranks;
        this.address = address;
        this.phone = phone;
        this.status = status;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getRanks() {
        return ranks;
    }

    public void setRanks(String ranks) {
        this.ranks = ranks;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
