package edu.android.osakazzang;

import java.io.Serializable;

/**
 * Created by itwill on 2017-10-11.
 */

public class Food implements Serializable{
    private int fPhoto;
    private String fName;
    private String fAddress;
    private String fPhone;
    private int fPrice;
    private int fOpen;
    private int fClose;
    private double fLat;
    private double fLng;

    private boolean selected3;

    public Food(){}

    public Food(String fName, String fAddress, String fPhone, double fLat, double fLng) {
        this.fName = fName;
        this.fAddress = fAddress;
        this.fPhone = fPhone;
        this.fLat = fLat;
        this.fLng = fLng;
    }

    public Food(int fPhoto, String fName, String fAddress, String fPhone, int fPrice, double fLat, double fLng) {
        this.fPhoto = fPhoto;
        this.fName = fName;
        this.fAddress = fAddress;
        this.fPhone = fPhone;
        this.fPrice = fPrice;
        this.fLat = fLat;
        this.fLng = fLng;
    }

    public boolean isSelected3() {
        return selected3;
    }

    public void setSelected3(boolean selected3) {
        this.selected3 = selected3;
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
                + "fPhone:" + fAddress + ", "
                + "fAddress:" + fPhone + ", "
                + "fLat:" + fLat + ", "
                + "fLng:" + fLng
                + "}";

        return s;
    }
}
