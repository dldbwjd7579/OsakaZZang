package edu.android.osakazzang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * 앱 사용법에 대한 내용의 Activity
 * 사실상 코드는 없고 Layout만 사용
 * 이거 지워도 되나?
 */

public class HelperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper);
    }
}
