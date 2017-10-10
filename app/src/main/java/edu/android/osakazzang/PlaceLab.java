package edu.android.osakazzang;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YuJung on 2017-09-18.
 */

public class PlaceLab { //관광지 Dummy Data Lab Class
    private List<Place> placeList;
    private static PlaceLab instance;
    private PlaceLab(){
        placeList = new ArrayList<>();

        Place p1 = new Place(R.drawable.cappuccino, "난바 신사", "010-01", "4 Chome-1-3 Bakuromachi, Chuo Ward, Osaka, Osaka Prefecture 541-0059 일본", 1000, 34.6788429,135.4999007);
        placeList.add(p1);
        Place p2 = new Place(R.drawable.filter, "나라 사슴 공원", "010-02", "일본 Nara Prefecture, Nara, 나라 공원", 2000, 34.685047,135.84301200000004);
        placeList.add(p2);
        Place p3 = new Place(R.drawable.latte, "도톤보리", "","일본 〒542-0071 오사카 부 오사카 시 주오 구 도톤보리", 3000, 34.6685155,135.50255189999996);
        placeList.add(p3);
    }
    public static PlaceLab getInstance(){
        if(instance == null){
            instance = new PlaceLab();
        }
        return  instance;
    }
    public List<Place> getPlaceList(){
        return placeList;
    }
}
