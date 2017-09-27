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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

public class schedule2Activity extends AppCompatActivity
        implements TotalFragment.TotalListner{

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

    public static final String KEY_EXTRA_CONTACT_INDEX = "extra_contact_index";
    private List<Day> days;
    private int dateIndex;

//    private TravelLab travelLab = TravelLab.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_main1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // 툴바 설정

        adapter = new TravelPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "스케줄 닫기", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                findViewById(R.id.fab);

                TotalFragment dlg = TotalFragment.newInstance(dateIndex);
                dlg.show(getSupportFragmentManager(), "Total_dlg");

            }
        });
        Intent intent = getIntent();
        dateIndex = intent.getIntExtra(schedule1Activity.KEY_EXTRA_CONTACT_INDEX, 0);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
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
                    return RestaurantFragment.newInstance(3);
                default:
                    return TravelFragment.newInstance(100);
            }

        }

        @Override
        public int getCount() {

            return 3;
        }

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
