package edu.android.osakazzang;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


/**
첫 로딩화면을 보여주는 액티비티
 */


public class splashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);

                finish();
            }
        }, 2000);


    }
}
