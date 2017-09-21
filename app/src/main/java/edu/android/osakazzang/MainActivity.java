package edu.android.osakazzang;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //위치 정보 권한 허가를 위한 변수들/////////////////////
    //private LocationRequest locationRequest;
    private GoogleApiClient apiClient;
    private FusedLocationProviderClient locationClient;
    private Location location;
    private LocationCallback locationCallback;
    public static final int REQ_CODE = 1;
    ////////////////////////////////////////////////////

    // 멤버변수
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // 로그인한 사람의 아이디를 가져오는 intent
        Intent intent = getIntent();
        String Id = intent.getStringExtra(LoginActivity.USERIDLOG);
        // NavigationeView 의 headerView는 일반적인 setText 로는 바뀌지않음
        // 아래의 코드를 써줘야 바뀜
        View header = navigationView.getHeaderView(0);
        textView = (TextView) header.findViewById(R.id.userId_header_main);
        // 로그인한 아이디를 네비게이션 상단에 표시
        textView.setText(Id);

        Button btnStartTour = (Button) findViewById(R.id.btn_start_tour);
        btnStartTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // SecondActivity 를 띄움
                Intent intent = new Intent(view.getContext(), SecondActivity.class);
                startActivity(intent);
            }
        });

        //btnNearTour : 현재 위치 기반 관광지 추천하는 페이지 연결되는 버튼
        Button btnNearTour = (Button) findViewById(R.id.btn_near_sights);
        btnNearTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkLocation = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
                if (checkLocation != PackageManager.PERMISSION_GRANTED) {
                    String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
                    ActivityCompat.requestPermissions(MainActivity.this, permissions, REQ_CODE);
                } else {
                    Intent intent = new Intent(MainActivity.this, NearActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQ_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //사용자가 권한 허가
                    Intent intent = new Intent(MainActivity.this, NearActivity.class);
                    startActivity(intent);
                } else {
                    //사용자가 권한 허가하지 않음
                    Toast.makeText(MainActivity.this, "권한을 허가해 주세요", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private CloseFragment cf = null;


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            // 안드로이드내 기본 다이얼로그를 이용한 종료메시지창
//            super.onBackPressed();
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("종료확인");
//            builder.setMessage("정말 종료하시겠습니까?");
//            builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    finish();
//                }
//            });
//
//            builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.cancel();
//                }
//            });
//            AlertDialog dlg = builder.create();
//            dlg.show();
            // 원래는 위에 코드인데 아래코드만 써도 똑같은 결과를 보여줌

//            builder.show();

            // 커스텀 다이얼로그를 이용한 종료메시지창
            cf = new CloseFragment();
            cf.show(getSupportFragmentManager(), "close");

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.travel_route) {
            // Handle the camera action
            Intent intent = new Intent(this, RouteResultActivity.class);
            startActivity(intent);

        } else if (id == R.id.company_introduce) {
            Intent intent = new Intent(this, IntroduceActivity.class);
            startActivity(intent);


        } else if (id == R.id.helper) {
            Intent intent = new Intent(this, HelperActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
