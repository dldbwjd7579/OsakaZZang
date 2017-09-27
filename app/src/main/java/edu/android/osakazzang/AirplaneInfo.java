package edu.android.osakazzang;

/**
 * Created by STU on 2017-09-26.
 */

public class AirplaneInfo {
    private String airLine; //항공사 이름
    private String airPlane; //항공편 이름
    private String airPort; //기준 공항
    private String city; //운항 구간
    private String inOut; //입국인지 출국인지 : IN / OUT
    private int time; //계획 시간

    public AirplaneInfo(){

    }


    public AirplaneInfo(String airLine, String airPlane, String airPort, String city, String inOut, int time) {
        this.airLine = airLine;
        this.airPlane = airPlane;
        this.airPort = airPort;
        this.city = city;
        this.inOut = inOut;
        this.time = time;
    }

    public String getAirLine() {
        return airLine;
    }

    public void setAirLine(String airLine) {
        this.airLine = airLine;
    }

    public String getAirPlane() {
        return airPlane;
    }

    public void setAirPlane(String airPlane) {
        this.airPlane = airPlane;
    }

    public String getAirPort() {
        return airPort;
    }

    public void setAirPort(String airPort) {
        this.airPort = airPort;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getInOut() {
        return inOut;
    }

    public void setInOut(String inOut) {
        this.inOut = inOut;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return " << " + airPlane + " >>\n"
                + "항공사 : " + airLine + "\n"
                + "공항 : " + airPort + "\n"
                + "도시 : " + city + "\n"
                + "출입국 : " + inOut + "\n"
                + "예정 시간 : " + time;

    }


}
