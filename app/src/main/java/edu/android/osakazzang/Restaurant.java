package edu.android.osakazzang;

/**
 * Created by SUT on 2017-09-20.
 */

public class Restaurant {

    private String name;
    private String address;
    private String phone;
    private int open;
    private int close;
    private int imageId;
    private boolean selected3;

    public Restaurant(String name, String address, String phone, int open, int close, int imageId) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.open = open;
        this.close = close;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }

    public int getClose() {
        return close;
    }

    public void setClose(int close) {
        this.close = close;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public boolean isSelected3() {
        return selected3;
    }

    public void setSelected3(boolean selected3) {
        this.selected3 = selected3;
    }
}