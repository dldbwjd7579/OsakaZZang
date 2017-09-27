package edu.android.osakazzang;

/**
 * Created by SUT on 2017-09-19.
 */

public class Stay {
    private String name;
    private String location;
    private String phone;
    private String Http;
    private String price;
    private int PhotoId;
    private boolean selected2;

    public Stay(String name, String location, String phone, String http, String price, int photoId) {
        this.name = name;
        this.location = location;
        this.phone = phone;
        Http = http;
        this.price = price;
        PhotoId = photoId;
    }

    public boolean isSelected2() {
        return selected2;
    }

    public void setSelected2(boolean selected2) {
        this.selected2 = selected2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHttp() {
        return Http;
    }

    public void setHttp(String http) {
        Http = http;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getPhotoId() {
        return PhotoId;
    }

    public void setPhotoId(int photoId) {
        PhotoId = photoId;
    }
}