package edu.android.osakazzang;

/**
 * Created by SUT on 2017-09-19.
 */

public class Travel {

    private String name;
    private String adress;
    private String phone;
    private int imageId;
    private int open;
    private int close;
    private boolean selected;

    public Travel(String name, String adress, String phone, int imageId, int open, int close) {
        this.name = name;
        this.adress = adress;
        this.phone = phone;
        this.imageId = imageId;
        this.open = open;
        this.close = close;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name).append("\n").append(adress).append("\n").append(phone).append("\n").append(open).append("\n").append(close);
        return builder.toString();
    }
}
