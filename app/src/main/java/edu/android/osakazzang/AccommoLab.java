package edu.android.osakazzang;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YuJung on 2017-09-21.
 */

public class AccommoLab {//숙박 Dummy Data Lab Class
    private List<Accommo> accommoList;
    private static AccommoLab instance;
    private AccommoLab(){
        accommoList = new ArrayList<>();

        Accommo a1 = new Accommo(R.drawable.osaka_image, "숙소1", "010-01", "주소1", 1000,34.68384990000001,135.50333469999998);
        accommoList.add(a1);
        Accommo a2 = new Accommo(R.drawable.caracter, "숙소2", "010-02", "주소2", 2000, 34.6865358,135.50792260000003);
        accommoList.add(a2);
        Accommo a3 = new Accommo(R.drawable.carrier, "숙소3", "010-03","주소3", 3000, 34.6758141,135.50379799999996);
        accommoList.add(a3);
    }
    public static AccommoLab getInstance(){
        if(instance == null){
            instance = new AccommoLab();
        }
        return  instance;
    }
    public List<Accommo> getAccommoList(){
        return accommoList;
    }
}
