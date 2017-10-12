package edu.android.osakazzang;

import android.provider.BaseColumns;

import java.util.Date;

/**
 * Created by SUT on 2017-10-11.
 */

public class Osaka {
    private int _id; //
    private String SIGHTNAME; // 관광지
    private String SIGHTADDRESS; // 관광지 주소
    private String SIGHTPHONE; // 관광지 전화번호
    private String HOMEPAGE;
    private int SIGHTOPEN; // 관광지 여는시간
    private int SIGHTCLOSE;  // 관광지 닫는시간
    private double SIGHTLAT; // 위도
    private double SIGHTLNG; // 경도
    private long date;

    public Osaka() {}

    public Osaka(int _id, String SIGHTNAME, String SIGHTADDRESS, String SIGHTPHONE, String HOMEPAGE, int SIGHTOPEN, int SIGHTCLOSE, double SIGHTLAT, double SIGHTLNG, long date) {
        this._id = _id;
        this.SIGHTNAME = SIGHTNAME;
        this.SIGHTADDRESS = SIGHTADDRESS;
        this.SIGHTPHONE = SIGHTPHONE;
        this.HOMEPAGE = HOMEPAGE;
        this.SIGHTOPEN = SIGHTOPEN;
        this.SIGHTCLOSE = SIGHTCLOSE;
        this.SIGHTLAT = SIGHTLAT;
        this.SIGHTLNG = SIGHTLNG;
        this.date = date;
    }


    public String getHOMEPAGE() {
        return HOMEPAGE;
    }

    public void setHOMEPAGE(String HOMEPAGE) {
        this.HOMEPAGE = HOMEPAGE;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getSIGHTNAME() {
        return SIGHTNAME;
    }

    public void setSIGHTNAME(String SIGHTNAME) {
        this.SIGHTNAME = SIGHTNAME;
    }

    public String getSIGHTADDRESS() {
        return SIGHTADDRESS;
    }

    public void setSIGHTADDRESS(String SIGHTADDRESS) {
        this.SIGHTADDRESS = SIGHTADDRESS;
    }

    public String getSIGHTPHONE() {
        return SIGHTPHONE;
    }

    public void setSIGHTPHONE(String SIGHTPHONE) {
        this.SIGHTPHONE = SIGHTPHONE;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getSIGHTOPEN() {
        return SIGHTOPEN;
    }

    public void setSIGHTOPEN(int SIGHTOPEN) {
        this.SIGHTOPEN = SIGHTOPEN;
    }

    public int getSIGHTCLOSE() {
        return SIGHTCLOSE;
    }

    public void setSIGHTCLOSE(int SIGHTCLOSE) {
        this.SIGHTCLOSE = SIGHTCLOSE;
    }

    public double getSIGHTLAT() {
        return SIGHTLAT;
    }

    public void setSIGHTLAT(double SIGHTLAT) {
        this.SIGHTLAT = SIGHTLAT;
    }

    public double getSIGHTLNG() {
        return SIGHTLNG;
    }

    public void setSIGHTLNG(double SIGHTLNG) {
        this.SIGHTLNG = SIGHTLNG;
    }

    // 데이터베이스 테이블의 이름, 컬럼의 이름들을 정의
    public static abstract class OsakaScheduleEntry implements BaseColumns{

        public static final String TABLE_NAME = "tbl_Tourist";
        // BaseColumns가 _ID라는 컬럼 이름 상수를 정의하고 있음
        // -> 따로 상수를 정의할 필요가 없음

        public static final String COL_Osaka_SIGHTNAME = "SIGHTNAME"; // 컬럼 이름
        public static final String COL_Osaka_SIGHTADDRESS = "SIGHTADDRESS";
        public static final String COL_Osaka_SIGHTPHONE = "SIGHTPHONE";
        public static final String COL_Osaka_HOMEAPGE = "HOMEPAGE";
        public static final String COL_Osaka_SIGHTOPEN = "SIGHTOPEN";
        public static final String COL_Osaka_SIGHTCLOSE = "SIGHTCLOSE";
        public static final String COL_Osaka_SIGHTLAT = "SIGHTLAT";
        public static final String COL_Osaka_SIGHTLNG =  "SIGHTLNG";
        public static final String COL_Osaka_SIGHTDATE = "DATE";

    }

    @Override
    public String toString() {
        return "Osaka{" +
                "_id=" + _id +
                ", SIGHTNAME='" + SIGHTNAME + '\'' +
                ", SIGHTADDRESS='" + SIGHTADDRESS + '\'' +
                ", SIGHTPHONE='" + SIGHTPHONE + '\'' +
                ", HOMEPAGE='" + HOMEPAGE + '\'' +
                ", SIGHTOPEN='" + SIGHTOPEN + '\'' +
                ", SIGHTCLOSE='" + SIGHTCLOSE + '\'' +
                ", SIGHTLAT='" + SIGHTLAT + '\'' +
                ", SIGHTLNG='" + SIGHTLNG + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
