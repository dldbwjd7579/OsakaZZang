package edu.android.osakazzang;

/**
 * Created by SUT on 2017-09-22.
 */

public class Day {

    private int month;
    private int day;
    private int imageId;

    public Day () {

    }

    public Day(int month, int day, int imageId) {
        this.month = month;
        this.day = day;
        this.imageId = imageId;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(month + "월 ").append(day + "일");

        return builder.toString();

    }
}