package edu.android.osakazzang;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SUT on 2017-09-19.
 */

public class StayLab {

    private static StayLab instance;
    private List<Accommo> list;

    private StayLab() {
        list = new ArrayList<>();
        /*Stay s1 = new Stay("이름1" , "위치1", "전화번호1", "홈페이지1", "가격1", R.drawable.n1);
        list.add(s1);
        Stay s2 = new Stay("이름2" , "위치2", "전화번호2", "홈페이지2", "가격2", R.drawable.n2);
        list.add(s2);
        Stay s3 = new Stay("이름3" , "위치3", "전화번호3", "홈페이지3", "가격3", R.drawable.n3);
        list.add(s3);
        Stay s4 = new Stay("이름4" , "위치4", "전화번호4", "홈페이지4", "가격4", R.drawable.n3);
        list.add(s3);
        Stay s5 = new Stay("이름5" , "위치5", "전화번호5", "홈페이지5", "가격5", R.drawable.n3);
        list.add(s3);
        Stay s6 = new Stay("이름6" , "위치6", "전화번호6", "홈페이지6", "가격6", R.drawable.n3);
        list.add(s3);*/


    }

    public static StayLab getInstance() {
        if (instance == null) {
            instance = new StayLab();
        }

        return instance;
    }

    public List<Accommo> getList() {
        return  list;
    }
}
