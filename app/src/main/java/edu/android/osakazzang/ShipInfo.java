package edu.android.osakazzang;

/**
 * Created by STU on 2017-09-27.
 */

public class ShipInfo {


    private String dptTm; // 출발 시간
    private String arvTm; // 도착 시간
    private String arvnmKor; // 도착지명
    private String arvDt; // 도착 일자
    private String plyCat; // 선박종류 구분
    private String cat; // 입 출항 구분
    private String departDt; // 운항 날짜
    private String shipId; // 배 아이디
    private String shipNmKor; // 선박명


    public ShipInfo() {}

    public ShipInfo(String plyCat, String cat, String departDt, String shipId, String shipNmKor, String dptTm, String arvnmKor, String arvDt, String arvTm) {
        this.plyCat = plyCat;
        this.cat = cat;
        this.departDt = departDt;
        this.shipId = shipId;
        this.shipNmKor = shipNmKor;
        this.dptTm = dptTm;
        this.arvnmKor = arvnmKor;
        this.arvDt = arvDt;
        this.arvTm = arvTm;
    }

    public String getPlyCat() {
        return plyCat;
    }

    public void setPlyCat(String plyCat) {
        this.plyCat = plyCat;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getDepartDt() {
        return departDt;
    }

    public void setDepartDt(String departDt) {
        this.departDt = departDt;
    }

    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    public String getShipNmKor() {
        return shipNmKor;
    }

    public void setShipNmKor(String shipNmKor) {
        this.shipNmKor = shipNmKor;
    }

    public String getDptTm() {
        return dptTm;
    }

    public void setDptTm(String dptTm) {
        this.dptTm = dptTm;
    }

    public String getArvnmKor() {
        return arvnmKor;
    }

    public void setArvnmKor(String arvnmKor) {
        this.arvnmKor = arvnmKor;
    }

    public String getArvDt() {
        return arvDt;
    }

    public void setArvDt(String arvDt) {
        this.arvDt = arvDt;
    }

    public String getArvTm() {
        return arvTm;
    }

    public void setArvTm(String arvTm) {
        this.arvTm = arvTm;
    }

    @Override
    public String toString() {
        return  "선박 종류 : " + "크루즈" + "\n"
                + "운항 날짜 : " + departDt + "\n"
                + "배 아이디 : " + shipId + "\n"
                + "선박 명 : " + shipNmKor + "\n"
                + "출발 시간 : " + dptTm + "\n"
                + "도착 시간 : " + arvTm + "\n"
                + "도착 일자 : " + arvDt + "\n";

    }
}
