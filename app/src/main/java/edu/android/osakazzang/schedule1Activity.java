package edu.android.osakazzang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class schedule1Activity extends AppCompatActivity {

    public static final String KEY_TRAFFIC_TYPE = "key_traffic_type";
    public static final String KEY_DEPART_YEAR = "key_dyear";
    public static final String KEY_DEPART_MONTH = "key_dmonth";
    public static final String KEY_DEPART_DAY = "key_dday";
    public static final String KEY_ARRIVAL_YEAR = "key_ayear";
    public static final String KEY_ARRIAVAL_MONTH = "key_amonth";
    public static final String KEY_ARRIVAL_DAY = "key_aday";


    public String departYear, departMonth, departDay, arrivalYear, arrivalMonth, arrivalDay;

    public static final String KEY_EXTRA_CONTACT_INDEX = "extra_contact_index";
    private List<Day> days;
    private int dayIndex;
    private int startIndex;
    private String trafficType;
    private List<Osaka> list ;


    private Button btnSchedule;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_main2);

        FragmentManager fm = getSupportFragmentManager();

        DayLab lab = DayLab.getInstance();
//        days = lab.getDayList();


        // AirplaneActivity가 보내준 인텐트를 얻어옴
        Intent intent = getIntent();
        dayIndex = intent.getIntExtra(schedule1Activity.KEY_EXTRA_CONTACT_INDEX, 0);
        id = intent.getStringExtra("id");

        // 년 월 일 // 출발날짜 // 도착날짜 데이터를 받음
        int dYear = intent.getIntExtra(KEY_ARRIVAL_YEAR, 0);
        int dMonth = intent.getIntExtra(KEY_DEPART_MONTH, 0);
        int dday = intent.getIntExtra(KEY_DEPART_DAY, 0);
        int aYear = intent.getIntExtra(KEY_ARRIVAL_YEAR, 0);
        int aMonth = intent.getIntExtra(KEY_ARRIAVAL_MONTH, 0);
        int aday = intent.getIntExtra(KEY_ARRIVAL_DAY, 0);

        trafficType = intent.getStringExtra(KEY_TRAFFIC_TYPE);

        // 출발날짜와 도착날짜를 day 저장하여 onBindViewHolder 에서 세팅해서 뿌려줌
        final Calendar start = new GregorianCalendar(dYear, dMonth, dday);
        Date startDate = start.getTime();
        Calendar end = new GregorianCalendar(aYear, aMonth, aday);
        Date endDate = end.getTime();
        days = lab.make(startDate, endDate);


        // recyclerview를 찾고 adapter설정
        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler_view);

        recycler.setLayoutManager(new LinearLayoutManager(this));

        DayAdapter adapter = new DayAdapter();
        recycler.setAdapter(adapter);


        //최종 스케줄 저장 버튼
        btnSchedule = (Button) findViewById(R.id.btn_scheduleCheck);
        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OsakaDbHepler helper = new OsakaDbHepler(schedule1Activity.this);
                list = helper.select();
                Log.i("edu.android", "1번째 sightName : " + list);
                for(int i = 0; i < list.size(); i++) {
                    Log.i("edu.android", "1번째 사이즈 "+ list.size());
                    makeData(i);
                }

                helper.deleteAll();

                Intent intent2 = new Intent(schedule1Activity.this, MainActivity.class);
                startActivity(intent2);
            }
        });

    }

    public void makeData(int index) {
        Log.i("logTag", "@@@@@ sight makeData()");

        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("logTag", "@@@@@@ response : " + response);
                    Toast.makeText(schedule1Activity.this, response, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        InsertRequest insertRequest = new InsertRequest(responseListener, index);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(insertRequest);
    }

    class InsertRequest extends StringRequest {
        private static final String URL = "http://audrms11061.cafe24.com/insert.php";
        private Map<String, String> parameters;
        private int index;

        public InsertRequest(Response.Listener<String> listener, int index) {
            super(Method.POST, URL, listener, null);
            this.index = index;
        }

        @Override
        protected Map<String, String> getParams() {

            OsakaDbHepler helper = new OsakaDbHepler(schedule1Activity.this);
//          list = helper.select();
            //안드로이드 db에 저장된 값들을 가져와 서버에 insert
            Log.i("edu.android", "list.size : " + list.size());

            String userId = id;
            String sightName = "";
            String choiceDate = "1";
            String saveDate = null;

            //TODO : list가 제대로 오지 않음.. size 검사 안하면 들어가는데 죽고  하면 sightName을 넣을 수 없음
            if(list.size() > 0) {
                 userId = id;

                Log.i("edu.android", "list.size : " + list.size());
                for(int i = 0; i < list.size(); i++){
                    Log.i("edu.android", "i : " + i);
                    sightName = list.get(i).getSIGHTNAME();
                    Log.i("edu.android", "2번째sightName : " + sightName);
                }
                                           choiceDate = "1";
                 saveDate = (new Date()).toString();
            } else{
                Log.i("logTag", "@@@@ list가 제대로 오지 않았음  :   " + list.size());
            }

            parameters = new HashMap<>();
            parameters.put("USERID", userId);
            parameters.put("SIGHTNAME", sightName);
            parameters.put("CHOICEDATE", choiceDate);
            parameters.put("SAVEDATE", saveDate);

            helper.close();
            return parameters;
        }

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            params.put("Content-Type", "application/x-www-form-urlencoded");

            return params;
        }
    }

    // totalActivity에 넘어가는 버튼
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

        //intent로 받은 날짜를 보여주는 ViewHolder
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

        public DayViewHolder(View itemView) { // 날짜이미지,month,day 아이템을 생성
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
                    intent.putExtra(KEY_TRAFFIC_TYPE, trafficType);
                    startActivity(intent);
                }
            });

        }


    }




}
