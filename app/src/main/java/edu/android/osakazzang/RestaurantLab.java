package edu.android.osakazzang;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SUT on 2017-09-20.
 */

public class RestaurantLab {

    private static RestaurantLab instance;
    private List<Restaurant> list;

    private RestaurantLab() {
        list = new ArrayList<>();
        Restaurant r1 = new Restaurant("이름1", "주소1", "전화번호", 1, 2, R.drawable.n1);
        Restaurant r2 = new Restaurant("이름2", "주소2", "전화번호", 3, 4, R.drawable.n2);
        Restaurant r3 = new Restaurant("이름3", "주소3", "전화번호", 5, 6, R.drawable.n3);
        Restaurant r4 = new Restaurant("이름4", "주소4", "전화번호", 9, 18, R.drawable.n4);
        Restaurant r5 = new Restaurant("이름5", "주소5", "전화번호", 10, 17, R.drawable.n5);
        list.add(r1);
        list.add(r2);
        list.add(r3);
        list.add(r4);
        list.add(r5);
    }

    public static RestaurantLab getInstance() {
        if (instance == null){
            instance = new RestaurantLab();
        }
        return instance;
    }

    public List<Restaurant> getList() {
        return list;
    }

    public List<Restaurant> find(int arrivalTime){
        List<Restaurant> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++){
            Restaurant r = list.get(i);
            if (arrivalTime <= r.getClose()){
                result.add(r);
            }
        }
        return result;
    }

}
