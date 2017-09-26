package edu.android.osakazzang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class AirplaneActivity extends AppCompatActivity
        implements DataFragment.DataSelectListener,
        DataEndFragment.DataSelectListenerTwo{

    public static final String TAG = "Main_index";
    private int viewHolderCount; // 카운터 수
    private TextView textView_DataStart;
    private TextView textView_DataEnd;
    private RecyclerView recycler;
    private AirPlaneAdatper adatper;
    private Button btn_next;


    private AirplaneScheduleLab lab = AirplaneScheduleLab.getInstance();
    private List<AirplaneSchedule> airplaneSchedules;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airplane);

        textView_DataStart = (TextView) findViewById(R.id.textView_DataStart);
        textView_DataEnd =(TextView) findViewById(R.id.textView_DataEnd);
        btn_next = (Button) findViewById(R.id.btn_next);

        textView_DataStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 출발날짜 텍스트를 눌렀을때 하는 다이얼로그
                DataFragment dlg = new DataFragment();
                dlg.show(getSupportFragmentManager(),"Calendar_dlg1");
            }
        });


        textView_DataEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 도착날짜 텍스트를 눌렀을때 하는 다이얼로그
                DataEndFragment dlg = new DataEndFragment();
                dlg.show(getSupportFragmentManager(), "Calender_dlg2");
            }
        });


        // 1. RecyLerView 를 찾음
        recycler = (RecyclerView) findViewById(R.id.recyclerView);

        // 2. RecyLerView에 LayoutManger 를 설정
        recycler.setLayoutManager(new LinearLayoutManager(this));

        // 3. RecyclerView에 Adapter 를 설정
        airplaneSchedules = new ArrayList<>();
        adatper = new AirPlaneAdatper();
        recycler.setAdapter(adatper);




    }


    // 레이아웃을 만들어 주는 ResyclerView
    public class AirPlaneAdatper extends RecyclerView.Adapter<AirPlaneViewHolder>{

        @Override
        public AirPlaneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            viewHolderCount++;
            // 레이아웃(XML)을 만들수 있는 LayoutInFlater 객체를 찾음
            Log.i(TAG, "viewHolderCount:" + viewHolderCount);
            LayoutInflater inflater = LayoutInflater.from(AirplaneActivity.this);
            // Adapter 안에
            // AirPlaneActivity 주소가 와야함

            // 아이템 하나를 보여주는 View 객체를 생성
            View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false );
            // 아이템 하나의 View 를 재활용 하는 ViewHolder 객체를 생성

            AirPlaneViewHolder viewHolder = new AirPlaneViewHolder(view);

            // 생성된 ViewHolder 객체를 리턴
            return viewHolder;


        }

        @Override
        public void onBindViewHolder(AirPlaneViewHolder holder, int position) {

            // 재활용 틀 viewHolder에 아이템 내용을 채워줄 때 호출
            holder.textView.setText(airplaneSchedules.get(position).toString());

        }

        @Override
        public int getItemCount() {
            // 아이템 갯수 리턴
            return airplaneSchedules.size();
        }


    }

    public class AirPlaneViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private int index;

        public void setIndex(int index){
            this.index = index;
        }


        public AirPlaneViewHolder(View itemView) {
            super(itemView);

            textView  = (TextView) itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(AirplaneActivity.this,
                            textView.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    @Override
    public void dataSelected(int year, int mouth, int dayOfMouth) {

        String  text = year + "." + (mouth+1) + "." + dayOfMouth;
        textView_DataStart.setText(text);

       Calendar cal = new GregorianCalendar(year, mouth, dayOfMouth);
        Date searchDepartTime = cal.getTime();
        airplaneSchedules = lab.getListByDepartDate(searchDepartTime);

        // notifyDataSetChanged : adapter 에게 요청를 하면 RecyclerView 다시 그려주는 메소드
        adatper.notifyDataSetChanged();

    }


    @Override
    public void dateSelectedTwo(int yearTwo, int mouthTwo, int dayOfMouth) {

        String  text = yearTwo + "." + (mouthTwo+1) + "." + dayOfMouth;
        textView_DataEnd.setText(text);

        Calendar cal = new GregorianCalendar(yearTwo, mouthTwo, dayOfMouth);
        Date searchDepartTime = cal.getTime();
        airplaneSchedules = lab.getListByDepartDate(searchDepartTime);

        // notifyDataSetChanged : adapter 에게 요청를 하면 RecyclerView 다시 그려주는 메소드
        adatper.notifyDataSetChanged();


    }
}
