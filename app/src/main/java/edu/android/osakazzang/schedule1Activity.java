package edu.android.osakazzang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class schedule1Activity extends AppCompatActivity {

    public String departYear, departMonth, departDay, arrivalYear, arrivalMonth, arrivalDay;

    public static final String KEY_EXTRA_CONTACT_INDEX = "extra_contact_index";
    private List<Day> days;
    private int dayIndex;
    private int startIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_main2);

        DayLab lab = DayLab.getInstance();
        days = lab.getDays();

        // AirplaneActivity가 보내준 인텐트를 얻어옴
        Intent intent = getIntent();
        // 년 월 일 // 출발날짜 // 도착날짜 데이터를 받음
        int KEY_DEPART_YEAR = intent.getIntExtra(AirplaneActivity.KEY_ARRIVAL_YEAR, 0);
        int KEY_DEPART_MONTH = intent.getIntExtra(AirplaneActivity.KEY_DEPART_MONTH, 1);
        int KEY_DEPART_DAY = intent.getIntExtra(AirplaneActivity.KEY_DEPART_DAY,2);
        int KEY_ARRIVAL_YEAR = intent.getIntExtra(AirplaneActivity.KEY_ARRIVAL_YEAR,3);
        int KEY_ARRIAVAL_MONTH = intent.getIntExtra(AirplaneActivity.KEY_ARRIAVAL_MONTH,4);
        int KEY_ARRIVAL_DAY = intent.getIntExtra(AirplaneActivity.KEY_ARRIVAL_DAY,5);

        // 출발날짜와 도착날짜를 day 저장하여 onBindViewHolder 에서 세팅해서 뿌려줌
        Calendar start = new GregorianCalendar(KEY_DEPART_YEAR, KEY_DEPART_MONTH, KEY_DEPART_DAY);
        Date startDate = start.getTime();
        Calendar end = new GregorianCalendar(KEY_ARRIVAL_YEAR, KEY_ARRIAVAL_MONTH, KEY_ARRIVAL_DAY);
        Date endDate = end.getTime();
        days = lab.make(startDate, endDate);

        // recyclerview를 찾고 adapter설정
        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler_view);

        recycler.setLayoutManager(new LinearLayoutManager(this));

        DayAdapter adapter = new DayAdapter();
        recycler.setAdapter(adapter);

//        Intent intent = getIntent();
        dayIndex = intent.getIntExtra(schedule1Activity.KEY_EXTRA_CONTACT_INDEX, 0);


    }

    public void next(View view) {

        Intent intent  = new Intent(this, TotalActivity.class);
        startActivity(intent);
    }

    public class DayAdapter extends RecyclerView.Adapter<DayViewHolder>{

        @Override
        public DayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater =  LayoutInflater.from(schedule1Activity.this);
            View view = inflater.inflate(R.layout.day_item, parent, false);
            DayViewHolder viewHolder = new DayViewHolder(view);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(DayViewHolder holder, int position) {


            Day day = days.get(position);
            holder.imageView.setImageResource(day.getImageId());
            holder.day.setText(day.getDay()+"일");
            holder.month.setText(day.getMonth()+"월");

//            Toast.makeText(schedule1Activity.this, day.getDay(), Toast.LENGTH_SHORT).show();

            holder.setIndex(position);
        }

        @Override
        public int getItemCount() {
            return days.size();
        }
    }

    public class DayViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView day, month ;
        private int index;

        public void setIndex(int index) {
            this.index = index;
        }

        public DayViewHolder(View itemView) { // 이미지,텍스트 아이템을 생성
            super(itemView);

            imageView = itemView.findViewById(R.id.imageCard);
            day = itemView.findViewById(R.id.textDay);
            month =itemView.findViewById(R.id.textMonth);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(schedule1Activity.this,day.getText(), Toast.LENGTH_SHORT).show();

                    int idx = getAdapterPosition();

                    Intent intent = new Intent(schedule1Activity.this, schedule2Activity.class);
                    intent.putExtra(KEY_EXTRA_CONTACT_INDEX, idx);
                    startActivity(intent);
                }
            });

        }


    }

}