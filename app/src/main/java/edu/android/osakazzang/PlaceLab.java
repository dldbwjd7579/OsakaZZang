package edu.android.osakazzang;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by itwill on 2017-09-18.
 */

public class PlaceLab {
    private List<Place> placeList;
    private static PlaceLab instance;
    private PlaceLab(){
        placeList = new ArrayList<>();

        Place p1 = new Place(R.drawable.cappuccino, "place 01", "010-01", "address01", "1000", 34.6786894, 135.50019900000007);
        placeList.add(p1);
        Place p2 = new Place(R.drawable.filter, "place 02", "010-02", "address02", "2000", 0, 0);
        placeList.add(p2);
        Place p3 = new Place(R.drawable.latte, "place 03", "010-03", "address03", "3000", 0, 0);
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
