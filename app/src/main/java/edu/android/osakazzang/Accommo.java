package edu.android.osakazzang;

import java.io.Serializable;

/**
 * Created by YuJung on 2017-09-21.
 */

public class Accommo implements Serializable{ //숙박 Dummy Data Model Class
    private int aPhoto;
    private String aName;
    private String aPhone;
    private String aAddress;
    private int aPrice;
    private double aLat;
    private double aLng;

    public Accommo(int aPhoto, String aName, String aPhone, String aAddress, int aPrice, double aLat, double aLng) {
        this.aPhoto = aPhoto;
        this.aName = aName;
        this.aPhone = aPhone;
        this.aAddress = aAddress;
        this.aPrice = aPrice;
        this.aLat = aLat;
        this.aLng = aLng;
    }

    public int getaPhoto() {
        return aPhoto;
    }

    public String getaName() {
        return aName;
    }

    public String getaPhone() {
        return aPhone;
    }

    public String getaAddress() {
        return aAddress;
    }

    public int getaPrice() {
        return aPrice;
    }

    public double getaLat() {
        return aLat;
    }

    public double getaLng() {
        return aLng;
    }
}
