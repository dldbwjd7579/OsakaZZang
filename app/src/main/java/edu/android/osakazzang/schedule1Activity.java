package edu.android.osakazzang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    public static final String KEY_EXTRA_CONTACT_INDEX = "extra_contact_index";
    private List<Day> days;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_main2);

        DayLab lab = DayLab.getInstance();
//        days = lab.getDays();

        Calendar start = new GregorianCalendar(2017, 1, 27);
        Date startDate = start.getTime();
        Calendar end = new GregorianCalendar(2017, 2, 1);
        Date endDate = end.getTime();
        days = lab.make(startDate, endDate);



        // recyclerview를 찾고 adapter설정
        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler_view);

        recycler.setLayoutManager(new LinearLayoutManager(this));

        DayAdapter adapter = new DayAdapter();
        recycler.setAdapter(adapter);


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
