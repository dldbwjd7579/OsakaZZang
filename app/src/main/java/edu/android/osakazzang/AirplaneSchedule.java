package edu.android.osakazzang;

import java.util.Date;

/**
 * Created by STU on 2017-09-25.
 */

public class AirplaneSchedule {
    private String departCity;
    private String destCity;
    private String flightNo;
    private Date departTime;
    private Date arrivalTime;

    public AirplaneSchedule(String departCity, String destCity, String flightNo, Date departTime, Date arrivalTime) {
        this.departCity = departCity;
        this.destCity = destCity;
        this.flightNo = flightNo;
        this.departTime = departTime;
        this.arrivalTime = arrivalTime;
    }

    public String getDepartCity() {
        return departCity;
    }

    public void setDepartCity(String departCity) {
        this.departCity = departCity;
    }

    public String getDestCity() {
        return destCity;
    }

    public void setDestCity(String destCity) {
        this.destCity = destCity;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public Date getDepartTime() {
        return departTime;
    }

    public void setDepartTime(Date departTime) {
        this.departTime = departTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("출발지: ").append(departCity).append("\n")
                .append("도착지:").append(destCity).append("\n")
                .append("출발시간:").append(departTime.toString()).append("\n")
                .append("도착시간:").append(arrivalTime.toString());
//                .append("출발시간:").append(departTime).append("\n")
//                .append("도착시간:").append(arrivalTime);

        return buffer.toString();
    }
}
