package edu.android.osakazzang;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import static edu.android.osakazzang.SecondActivity.*;

public class schedule2Activity extends AppCompatActivity {

    private static final String TAG = "tag";
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private TravelPagerAdapter adapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

//    public static final String KEY_EXTRA_CONTACT_INDEX = "extra_contact_index";
    private List<Day> days;
    private int dateIndex;

//    private TravelLab travelLab = TravelLab.getInstance();


    // FIXME: find() 매개변수를 5로 강제로 넣고 있음. 왜냐하면 TravelFragment.newInstance(5);라고 호출했기 때문에
    // 수정해야 함
    List<Travel> travelList = TravelLab.getInstance().find(5);

    List<Stay> stayList = StayLab.getInstance().getList();

    // FIXME: 왜 find() 매개변수로 4를 주고 있을까?
    List<Restaurant> restaurantList = RestaurantLab.getInstance().find(4);


    private String trafficType;


    // 관광지,식당 뒤로갈때 false로 처리
    @Override
    public void onBackPressed() {
        for (Travel t : travelList) {
            t.setSelected(false);
        }

        for (Restaurant r : restaurantList) {
            r.setSelected3(false);
        }

        super.onBackPressed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_main1);



        ////////////////////
        Intent intent = getIntent();
        dateIndex = intent.getIntExtra(schedule1Activity.KEY_EXTRA_CONTACT_INDEX, 0);
        Log.i("mytag", "***** dateIndex=" + dateIndex);

        trafficType = intent.getStringExtra(schedule1Activity.KEY_TRAFFIC_TYPE);


        // 스케줄1에서 스케줄2로 날짜정보(DataIndex)를 보낸것을 여기서 받음
        // > TotalFragment에서 dateIndex 정보를 사용해서 DayLab에서 꺼냄

//        DayLab dayLab = DayLab.getInstance();
//        List<Day> dayList = dayLab.getDayList();
//        selectedDay = dayList.get(dateIndex);
//        Log.i("mytag", "***** " + selectedDay.getMonth() + "/" + selectedDay.getDay());
        ////////////////////


        // 툴바 설정
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new TravelPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // FloatingActionButton을 여기서 처리함
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "스케줄 닫기", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Log.i("mytag", "***** schedule2Activity:: dateIndex=" + dateIndex);
                TotalFragment dlg = TotalFragment.newInstance(dateIndex, trafficType);

                Log.i("logTag2", "dlg = " + dlg);
                List<Food> dataList = new ArrayList<>();

                TravelLab travelLab = TravelLab.getInstance(); // 관광지 Instance를 가져옴
                List<Travel> list = travelLab.getList();
                for (int i = 0; i< list.size(); i++) {
                    Travel travel = list.get(i);
                    if (travel.isSelected()) {
                        Food f = new Food(0, travel.getName(), travel.getPhone(), travel.getAdress(), 10, 0, 0);
                        dataList.add(f);
                    }
                }

                StayLab stayLab = StayLab.getInstance(); // 호텔 Instance를 가져옴
                List<Stay> list2 = stayLab.getList();
                for (int i = 0; i < list2.size(); i++){
                    Stay stay = list2.get(i);
                    if (stay.isSelected2()){
                        Food f = new Food(0, stay.getName(), stay.getPhone(), stay.getLocation(), 10, 0, 0);
                        dataList.add(f);
                    }
                }

                RestaurantLab restaurantLab = RestaurantLab.getInstance(); // 맛집 Instance를 가져옴
                List<Restaurant> list3 = restaurantLab.getInstance().find(4); //find는 도착시간을 의미
                for (int i = 0; i < list3.size(); i++){
                    Restaurant restaurant = list3.get(i);
                    if (restaurant.isSelected3()){
                        Food f = new Food(0, restaurant.getName(), restaurant.getPhone(), restaurant.getAddress(), 10, 0, 0);
                        dataList.add(f);
                    }
                }

                // 다이얼로그를 보여줌
                Log.i("logTag2", "dataList : " + dataList);
                dlg.setData(dataList);
                dlg.show(getSupportFragmentManager(), "Total_dlg");

            }
        });

    }


    // 옵션메뉴
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * TapActivity활용해서 관광지, 숙박, 맛집 탭을 생성
     * 관광지,맛집은 도착시간을 매개변수로 받고
     * 호텔은 포지션값을 받음
     */

    public class TravelPagerAdapter extends FragmentPagerAdapter {

        public TravelPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    // TODO: 매개변수: 도착 시간
                    return TravelFragment.newInstance(5);
                case 1:
                    // TODO: 숙박 프래그먼트 리턴
                    return StayFragment.newInstance(position);
                case 2:
                    // TODO: 맛집 프래그먼트 리턴
                    // FIXME: 왜 newInstance() 매개변수가 4일까?
                    return RestaurantFragment.newInstance(4);
                default:
                    return TravelFragment.newInstance(00);
            }

        }


        // 탭의 개수를 3개로 정함
        @Override
        public int getCount() {

            return 3;
        }

        // 관광지,숙박,맛집 타이틀이름을 결정함

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "관광지";
                case 1:
                    return "숙박";
                case 2:
                    return "맛집";
                default: // 예외값
                    return "NULL";
            }
        }
    }
}
