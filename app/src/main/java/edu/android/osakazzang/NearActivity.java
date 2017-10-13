package edu.android.osakazzang;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class NearActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks{

    private static final int REQ_PERM_LOCATION = 1;
    private static final long UPDATE_INTERVAL = 2 * 1000;
    private static final long FASTEST_UPDATE_INTERVAL = UPDATE_INTERVAL / 2;
    private LocationRequest locationRequest;
    private GoogleApiClient apiClient;
    private FusedLocationProviderClient locationClient;
    private Location location;
    private LocationCallback locationCallback;


    private double lastLat;
    private double lastLon;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private PlacePagerAdapter placePagerAdapter;
    private List<Accommo> accommoList;
    private List<Place> placeList;
    private List<Food> foodList;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near);

        Intent intent = getIntent();
        accommoList = (List<Accommo>) intent.getSerializableExtra("accommoList");
        placeList = (List<Place>) intent.getSerializableExtra("placeList");
        foodList = (List<Food>) intent.getSerializableExtra("foodList");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        placePagerAdapter = new PlacePagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(placePagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        checkGooglePlayService();
    }

    private void checkGooglePlayService() {
        GoogleApiAvailability avail = GoogleApiAvailability.getInstance();
        int result = avail.isGooglePlayServicesAvailable(this);
        if(result == ConnectionResult.SUCCESS){
            buildGoogleApiClient();

        }else{
            Toast.makeText(this, "구글 플레이 서비스를 사용할 수 없어서 앱을 종료", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void buildGoogleApiClient() {
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
        builder.addConnectionCallbacks(this);
        builder.addApi(LocationServices.API);
        apiClient = builder.build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //현재위치
        if(apiClient != null) {
            apiClient.connect();
            Log.i("logTag", "connect()????");
        }

        if(!apiClient.isConnected()){
            Log.i("logTag", "connect가 안됨");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(apiClient != null && apiClient.isConnected()){
//            locationClient.removeLocationUpdates(locationCallback);
        }

        //현재위치
        if(apiClient != null) {
            apiClient.disconnect();
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        checkLocationPermission();
    }

    private void checkLocationPermission() {
        Log.i("logTag", "onConnected - checkLocationPermission");
        int check = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(check == PackageManager.PERMISSION_GRANTED){
            //위치 권한이 허용된 경우
            Log.i("logTag", "위치 권한 허용");
//            getLastLocation();
//            locationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }else{
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, permissions, REQ_PERM_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQ_PERM_LOCATION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.i("logTag", "사용자가 권한 허가");
                    //디바이스에 저장된 가장 최근 위치 (LastLocation)정보 가져옴
//                    getLastLocation();

                }else {
                    Log.i("logTag", "사용자가 권한 허가 안함");
                    Toast.makeText(this, "위치 권한이 없어서 앱을 종료합니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("logTag","api client 연결 지연되고 있음");
    }

    //////////////////////////////////////
    public class PlacePagerAdapter extends FragmentPagerAdapter{
        public PlacePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.i("logTag", "getItem(position:" + position + ")");
            NearTabFragment fragment = NearTabFragment.newInstance(position, accommoList, placeList, foodList);
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String[] pageName = {
                    "관광지", //Place
                    "숙소", //Accommo
                    "맛집" //Food
            };
            return pageName[position];
        }

    }
    ////////////////////////////////////



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_near, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
