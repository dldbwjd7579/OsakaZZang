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
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TotalFragment extends DialogFragment {

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

    public interface TotalListner {

    }

    private TextView text_Traffic;
    private TextView text_TrafficDetail;
    private TextView text_Day;
    private TextView text_Content1;
    private TextView text_Content2;
    private TextView text_Content3;
    private TextView text_Total;


    private TotalListner listner;


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

        text_Traffic = view.findViewById(R.id.text_Traffic); // 선택한 교통편
        text_TrafficDetail = view.findViewById(R.id.text_TrafficDetail); // 교통편 detail
        text_Day = view.findViewById(R.id.text_Day); //날짜
        text_Content1 = view.findViewById(R.id.text_Content1); // 체크한 관광지
        text_Content2 = view.findViewById(R.id.text_Content2); // 체크한 숙박
        text_Content3 = view.findViewById(R.id.text_Content3); // 체크한 맛집
        text_Total = view.findViewById(R.id.text_Total); //총금액

        List<Day> list4 =  dayLab.getDays();
        StringBuffer dayBuffer = new StringBuffer();
        // TODO: getArguments() 메소드 호출
        // 프래그먼트 아규먼트에 저장된 날짜 인덱스를 꺼내서 멤버변수 datePosition에 저장
        // list4.get(datePosition) 호출해서 날짜 정보 읽어서 텍스트뷰에 씀
        Bundle args = getArguments();
        datePosition = args.getInt("key_date_position");

        Calendar start = new GregorianCalendar(2017, 1, 27);
        Date startDate = start.getTime();
        Calendar end = new GregorianCalendar(2017, 2, 1);
        Date endDate = end.getTime();
        List<Day> dayList = dayLab.make(startDate, endDate);
        Day day = dayList.get(datePosition);

        Log.i("tag", day.getMonth() + "/" + day.getDay());

        text_Day.setText(day.getMonth() + "월 " + day.getDay() + "일");


        List<Travel> list = travelLab.getList();
        StringBuffer travelBuffer = new StringBuffer();
        for (int i = 0; i< list.size(); i++) {
            Travel travel = list.get(i);
            if (travel.isSelected()) {
                travelBuffer.append(travel.getName()).append("\n")
                .append(travel.getAdress()).append("\n")
                        .append("open: " + travel.getOpen() + "시").append("\n")
                        .append("close: " + travel.getClose() + "시").append("\n\n\n");
            }
        }
        text_Content1.setText(travelBuffer);

        List<Stay> list2 = stayLab.getList();
        StringBuffer stayBuffer = new StringBuffer();
        for (int i = 0; i < list2.size(); i++){
            Stay stay = list2.get(i);
            if (stay.isSelected2()){
                stayBuffer.append(stay.getName()).append("\n")
                .append(stay.getLocation()).append("\n")
                .append(stay.getPhone()).append("\n")
                .append(stay.getHttp()).append("\n")
                .append(stay.getPrice()).append("\n\n\n");
            }
        }
        text_Content2.setText(stayBuffer);

        // FIXME: 도착 시간 정보를 find() 매개변수로 줘야 함!!!
        List<Restaurant> list3 = restaurantLab.getInstance().find(4);
        Log.i("mytag", "***** TotalFragment:: datePosition=" + datePosition);
        Log.i("mytag", "***** TotalFragment:: list3 size=" + list3.size());
        StringBuffer restaurantBuffer = new StringBuffer();
        for (int i = 0; i < list3.size(); i++){
            Restaurant restaurant = list3.get(i);
            if (restaurant.isSelected3()){
                restaurantBuffer.append(restaurant.getName())
                .append(restaurant.getAddress()).append("\n")
                        .append("open: " + restaurant.getOpen() + "시").append("\n")
                        .append("close: " + restaurant.getClose() + "시").append("\n\n\n");
            }
        }
        text_Content3.setText(restaurantBuffer);



        builder.setView(view);
        builder.setPositiveButton("스케줄 확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        return builder.create();

    }
}
