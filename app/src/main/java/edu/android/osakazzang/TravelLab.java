package edu.android.osakazzang;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SUT on 2017-09-19.
 */

public class TravelLab {

    private static TravelLab instance;
    private List<Travel> list;

    private TravelLab() {
        list = new ArrayList<>();
        Travel t1 = new Travel("관광지1" , "주소1", "전화번호1", R.drawable.n1, 1 , 2);
        Travel t2 = new Travel("관광지2" , "주소2", "전화번호2", R.drawable.n2, 3 , 4);
        Travel t3 = new Travel("관광지3" , "주소3", "전화번호3", R.drawable.n3, 5 , 6);
        Travel t4 = new Travel("관광지4" , "주소4", "전화번호4", R.drawable.n3, 9 , 18);
        Travel t5 = new Travel("관광지5" , "주소5", "전화번호5", R.drawable.n3, 10 , 17);
        Travel t6 = new Travel("관광지6" , "주소6", "전화번호6", R.drawable.n3, 10 , 17);
        Travel t7 = new Travel("관광지7" , "주소7", "전화번호7", R.drawable.n3, 9 , 17);
        Travel t8 = new Travel("관광지8" , "주소8", "전화번호8", R.drawable.n3, 9 , 18);
        Travel t9 = new Travel("관광지9" , "주소9", "전화번호9", R.drawable.n3, 10 , 18);
        Travel t10 = new Travel("관광지10" , "주소10", "전화번호10", R.drawable.n3, 10 , 18);
        list.add(t1);
        list.add(t2);
        list.add(t3);
        list.add(t4);
        list.add(t5);
        list.add(t6);
        list.add(t7);
        list.add(t8);
        list.add(t9);
        list.add(t10);
    }

    public static TravelLab getInstance() {
        if (instance == null){
            instance = new TravelLab();
        }
        return instance;
    }

    public List<Travel> getList() {
        return list;
    }

    public List<Travel> find(int arrivalTime) {
        List<Travel> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Travel t = list.get(i);
            if (arrivalTime <= t.getClose()) {
                result.add(t);
            }
        }

        return result;
    }



}
