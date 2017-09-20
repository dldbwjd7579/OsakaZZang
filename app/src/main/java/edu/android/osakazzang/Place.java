package edu.android.osakazzang;

/**
 * Created by yujung on 2017-09-18.
 */

public class Place {
    private int pPhoto;
    private String pName;
    private String pPhone;
    private String pAddress;
    private String pPrice;
    private double pLat;
    private double pLon;

    public Place() {
    }
    public Place(int pPhoto, String pName, String pPhone, String pAddress, String pPrice, double pLat, double pLon) {
        this.pPhoto = pPhoto;
        this.pName = pName;
        this.pPhone = pPhone;
        this.pAddress = pAddress;
        this.pPrice = pPrice;
        this.pLat = pLat;
        this.pLon = pLon;
    }

    public int getpPhoto() {
        return pPhoto;
    }

    public String getpName() {
        return pName;
    }

    public String getpPhone() {
        return pPhone;
    }

    public String getpAddress() {
        return pAddress;
    }

    public String getpPrice() {
        return pPrice;
    }

    public double getpLat() {
        return pLat;
    }

    public double getpLon() {
        return pLon;
    }

    @Override
    public String toString() {
        return pName + "(" + pPhone + ") : " + pPrice +"원\n" + pAddress;
    }
}
