package edu.android.osakazzang;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity implements
        VehicleFragment.VehicleFragmentListener{

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // VehicleFragment 를 자바코드 동적으로 끼워넣음
        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.vehicleFragment);

        if(fragment == null){ // VehicleFragment 를 검사해서 추가
            fragment = new VehicleFragment();

            FragmentTransaction transaction = fm.beginTransaction();

            transaction.add(R.id.vehicleFragment, fragment);

            transaction.commit();

        }

        Intent intent = getIntent();
        id = intent.getStringExtra("id");


    }

    @Override
    public void onVehicleClick(int vehicle) {
        switch (vehicle){
            case 1:

                Toast.makeText(this, "비행기", Toast.LENGTH_SHORT).show();

                Intent intent1 = new Intent(this, AirplaneActivity.class);
                intent1.putExtra("id", id);
                startActivity(intent1);

                break;

            case 2:

                Toast.makeText(this, "배", Toast.LENGTH_SHORT).show();

                Intent intent2 = new Intent(this, ShipActivity.class);
                intent2.putExtra("id", id);
                startActivity(intent2);

                break;

        }

        finish();

    }
}
