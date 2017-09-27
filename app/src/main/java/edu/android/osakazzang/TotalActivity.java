package edu.android.osakazzang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TotalActivity extends AppCompatActivity {

    private int totalIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);

        Intent intent = getIntent();
        totalIndex = intent.getIntExtra(schedule1Activity.KEY_EXTRA_CONTACT_INDEX, 0);
    }
}
