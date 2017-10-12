package edu.android.osakazzang;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import static edu.android.osakazzang.schedule1Activity.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class TotalFragment extends DialogFragment {

    private static final String TAG = "edu.android";
    private TravelLab travelLab = TravelLab.getInstance();
    private StayLab stayLab = StayLab.getInstance();
    private RestaurantLab restaurantLab = RestaurantLab.getInstance();
    private DayLab dayLab = DayLab.getInstance();
    private List<Day> day2;
    private int datePosition;

    public static TotalFragment newInstance(int datePosition) {
        TotalFragment frag = new TotalFragment();

        // Bundle 객체 생성 -> Bundle 객체에 datePosition 저장
        // frag의 setArguments() 메소드 호출해서 Bundle 객체를 프래그먼트 아규먼트로 저장
        Bundle args = new Bundle();
        args.putInt("key_date_position", datePosition);
        frag.setArguments(args);

        return frag;
    }

    private TotalListner totalListner;

    public interface TotalListner {
        void dateSelect();
    }

    private TextView text_Traffic;
    private TextView text_TrafficDetail;
    private TextView text_Day;
    private TextView text_Content1;
    private TextView text_Content2;
    private TextView text_Content3;
    private TextView text_Total;

    private Button insert;

    private TotalListner listner;

    private ListView listView;
    private List<Food> dataList;
    private String dayy;
    private String monthy;

    public void setData(List<Food> list){
        dataList = list;
        Log.i("logTag2", "fragment에 전달된 list : " + list);
    }
    public void setDayy(String dayy, String monthy){
        this.dayy = dayy;
        this.monthy = monthy;
    }


    public TotalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TotalListner){
            listner = (TotalListner) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listner = null;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_total, null);

        listView = (ListView) view.findViewById(R.id.listView);
        ArrayAdapter<Food> adapter = new TotalArrayAdapter(getContext(), -1, dataList);
        listView.setAdapter(adapter);

        text_Traffic = view.findViewById(R.id.text_Traffic); // 선택한 교통편
        text_TrafficDetail = view.findViewById(R.id.text_TrafficDetail); // 교통편 detail
        text_Day = view.findViewById(R.id.text_Day); //날짜
        insert =  (Button)view.findViewById(R.id.btn_total);

        List<Day> list4 =  dayLab.getDayList();
        StringBuffer dayBuffer = new StringBuffer();
        // TODO: getArguments() 메소드 호출
        // 프래그먼트 아규먼트에 저장된 날짜 인덱스를 꺼내서 멤버변수 datePosition에 저장
        // list4.get(datePosition) 호출해서 날짜 정보 읽어서 텍스트뷰에 씀
        Bundle args = getArguments();
        datePosition = args.getInt("key_date_position"); // Bundle에서  포지션값을 꺼냄
        Log.i(TAG, "datePosition : " + datePosition);

        // FIXME: 이 코드가 왜 있지? 이유를 모르겠음!!! >>>>>
//        Calendar start = new GregorianCalendar(dYear, dMonth, dDay);
//        Date startDate = start.getTime();
//        Calendar end = new GregorianCalendar(aYear, aMonth, aDay);
//        Date endDate = end.getTime();
//        List<Day> dayList = dayLab.make(startDate, endDate);
//        Day day = dayList.get(datePosition);

//        Log.i("tag", day.getMonth() + "/" + day.getDay());

//        text_Day.setText(dYear+ "년" + (dMonth+1) + "월 " + dDay + "일");
        // FIXME: <<<<< 여기까지

        // DayLab에서 Instance를 가져옴
        DayLab dayLab = DayLab.getInstance();
        List<Day> dayList = dayLab.getDayList();
        Day selectedDay = dayList.get(datePosition);
        Log.i("mytag", "***** TotalFragment:: selectedDay=" + selectedDay);
        text_Day.setText(selectedDay.toString());

        /*List<Travel> list = travelLab.getList();
        for (int i = 0; i< list.size(); i++) {
            Travel travel = list.get(i);
            if (travel.isSelected()) {
                Food f = new Food(0, travel.getName(), travel.getPhone(), travel.getAdress(), 10, 0, 0);
                dataList.add(f);
            }
        }

        List<Stay> list2 = stayLab.getList();
        for (int i = 0; i < list2.size(); i++){
            Stay stay = list2.get(i);
            if (stay.isSelected2()){
                Food f = new Food(0, stay.getName(), stay.getPhone(), stay.getLocation(), 10, 0, 0);
                dataList.add(f);
            }
        }

        // FIXME: 도착 시간 정보를 find() 매개변수로 줘야 함!!!
        List<Restaurant> list3 = restaurantLab.getInstance().find(4);
        for (int i = 0; i < list3.size(); i++){
            Restaurant restaurant = list3.get(i);
            if (restaurant.isSelected3()){
                Food f = new Food(0, restaurant.getName(), restaurant.getPhone(), restaurant.getAddress(), 10, 0, 0);
                dataList.add(f);
            }
        }*/


        builder.setView(view);
        builder.setPositiveButton("닫기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertOsaka(view);
            }
        });

        return builder.create();

    }

    // DB에 insert 해야하는 정보
    public void insertOsaka(View view){
        String traffic = text_Traffic.getText().toString();
        String trafiicDetail = text_TrafficDetail.getText().toString();
        String day = text_Day.getText().toString();

        StringBuilder sb = new StringBuilder();
        for(Food f: dataList){
            sb.append(f.getfName()).append("\n")
                    .append(f.getfPhone()).append("\n")
                    .append(f.getfAddress());
        }



    }





}
