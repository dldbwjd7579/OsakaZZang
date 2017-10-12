package edu.android.osakazzang;

import java.io.Serializable;

/**
 * Created by itwill on 2017-10-11.
 */

public class Food implements Serializable{
    private int fPhoto;
    private String fName;
    private String fPhone;
    private String fAddress;
    private int fPrice;
    private int fOpen;
    private int fClose;
    private double fLat;
    private double fLng;

    public Food(){}

    public Food(String fName, String fPhone, String fAddress, double fLat, double fLng) {
        this.fName = fName;
        this.fPhone = fPhone;
        this.fAddress = fAddress;
        this.fLat = fLat;
        this.fLng = fLng;
    }

    public Food(int fPhoto, String fName, String fPhone, String fAddress, int fPrice, double fLat, double fLng) {
        this.fPhoto = fPhoto;
        this.fName = fName;
        this.fPhone = fPhone;
        this.fAddress = fAddress;
        this.fPrice = fPrice;
        this.fLat = fLat;
        this.fLng = fLng;
    }

    public int getfPhoto() {
        return fPhoto;
    }

    public String getfName() {
        return fName;
    }

    public String getfPhone() {
        return fPhone;
    }

    public String getfAddress() {
        return fAddress;
    }

    public int getfPrice() {
        return fPrice;
    }

    public double getfLat() {
        return fLat;
    }

    public double getfLng() {
        return fLng;
    }

    @Override
    public String toString() {
        String s = "{"
                + "fName:" + fName + ", "
                + "fPhone:" + fPhone + ", "
                + "fAddress:" + fAddress + ", "
                + "fOpen:" + fOpen + ", "
                + "fClose:" + fClose + ", "
                + "fLat:" + fLat + ", "
                + "fLng:" + fLng
                + "}";

        return s;
    }
}
