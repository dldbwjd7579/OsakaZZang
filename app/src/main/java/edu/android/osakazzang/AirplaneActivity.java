package edu.android.osakazzang;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class AirplaneActivity extends AppCompatActivity
        implements DataFragment.DataSelectListener,
        DataEndFragment.DataSelectListenerTwo{

    public static final String TAG = "Main_index";
    public static final String KEY_EXTRA_AIRPLAIN_INDEX = "key_extra_airplain_index";


    private boolean departDaySelected, arrivalDaySelected;

    private int viewHolderCount; // 카운터 수
    private TextView textView_DataStart;
    private TextView textView_DataEnd;
    private RecyclerView recycler;
    private AirPlaneAdatper adatper;
    private Button btn_next;

    private int departYear, departMonth, departDay, arrivalYear, arrivalMonth, arrivalDay;


    private static final String URL_AIRPLANE_INFO_1 =
            "http://openapi.airport.co.kr/service/rest/FlightScheduleList/getIflightScheduleList?"
                    + "ServiceK" +
                    "ey="
                    + "8hIeG6ga31T9L%2FYytDvxB49ASzbFxRAF47Jze%2B08Op8cRJBQPHTY8heGRhea%2B6A%2BfKt7NGg2440qf3qCv23d9w%3D%3D"
                    + "&schDate=";

    private String AIRPLANE_DATE;

    private static final String URL_AIRPLANE_INFO_2 =
                    "&schDeptCityCode=GMP"
                    + "&schArrvCityCode=KIX";
    private static final String URL_schDate= "schDate=20170920";
    private static final String URL_schDate2="schDate=20170920";
    private List<AirplaneInfo> airPlaneLists;




    private AirplaneScheduleLab lab = AirplaneScheduleLab.getInstance();
    private List<AirplaneSchedule> airplaneSchedules;

    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airplane);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");


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
        airPlaneLists = new ArrayList<>();
        adatper = new AirPlaneAdatper();
        recycler.setAdapter(adatper);

    }

    @Override
    protected void onStart() {
        super.onStart();
//        startNetWorkTask();
    }

    private void startNetWorkTask() {
        // 사용가능한 네트워크가 있는지 검사
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connMgr.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isAvailable()) {
            // TODO: AsyncTask를 사용해서 URL 주소(Open API)로 요청을 보내고
            // 응답(지하철 역 주소/전화번호 데이터)을 받음
            Log.i("logTag", "netInfo isAvailable()");

            AIRPLANE_DATE = "20170912";

            GetAirplaneInfoTask task = new GetAirplaneInfoTask();
            task.execute(URL_AIRPLANE_INFO_1 + AIRPLANE_DATE + URL_AIRPLANE_INFO_2);


        }


    }






    public class GetAirplaneInfoTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            Log.i("logTag", "doInBackground()");

            URL url = null;
            HttpURLConnection conn = null;
            InputStream in = null;
            InputStreamReader reader = null;
            BufferedReader br = null;
            StringBuffer buffer = new StringBuffer();
            try {
                url = new URL(params[0]);
                conn = (HttpURLConnection) url.openConnection();

                conn.setConnectTimeout(5 * 1000);
                conn.setReadTimeout(5 * 1000);
                conn.setRequestMethod("GET"); // 생략 불가!

                int resCode = conn.getResponseCode();
                if (resCode == HttpURLConnection.HTTP_OK) {
                    Log.i("logTag", "HttpURLConnction OK");


                    in = conn.getInputStream();
                    reader = new InputStreamReader(in);
                    br = new BufferedReader(reader);

                    while (true) {
                        String line = br.readLine();
                        if (line == null) {
                            break;
                        }

                        buffer.append(line);
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    br.close();
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("logTag", "onPostExecute()");
            Log.i("logTag", s);

            try {
                AirplaneInfoXmlParser parser = new AirplaneInfoXmlParser();


                List<AirplaneInfo> list = parser.parse(s);

                Log.i("logTag", "list size : " + list.size());

               // StringBuffer buffer = new StringBuffer();
//                for (AirplaneInfo airplaneInfo : list) {
//                    Log.i("logTag", "airplaneInfo : \n" + airplaneInfo.toString());
//
//                    buffer.append(airplaneInfo.toString())
//                            .append("\n\n");
//                }


                airPlaneLists = list;
                Log.i(TAG, "***** size=" + airPlaneLists.size());
                adatper.notifyDataSetChanged();

            } catch (Exception e) {
                Log.e("logTag", e.getMessage());
                e.printStackTrace();
            }
        }
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
            Log.i("logTag", "onBindViewHolder");

            if(airPlaneLists != null) {
                // 재활용 틀 viewHolder에 아이템 내용을 채워줄 때 호출
                holder.textView.setText(airPlaneLists.get(position).toString());
            }else{
                Toast.makeText(AirplaneActivity.this, "NULL", Toast.LENGTH_SHORT).show();
                holder.textView.setText("");
            }



        }

        @Override
        public int getItemCount() {
            // 아이템 갯수 리턴
            Log.i(TAG, "***** item count=" + airPlaneLists.size());
//            return airplaneSchedules.size();
            return airPlaneLists.size();
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
    public void dataSelected(int year, int month, int dayOfMonth) {
        departDaySelected = true;
        if (departDaySelected && arrivalDaySelected) {
            btn_next.setEnabled(true);
        }

        departYear = year;
        departMonth = month;
        departDay = dayOfMonth;

        String text = String.format("%02d.%02d.%02d" , year, month+1, dayOfMonth);
//        String  text = year + "." + (mouth+1) + "." + dayOfMouth;
        textView_DataStart.setText(text);

        ////////////////////////
        //TODO: URL만들어서 Task에 적용
        startNetWorkTask();


       Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
        Date searchDepartTime = cal.getTime();
        airplaneSchedules = lab.getListByDepartDate(searchDepartTime);

        // notifyDataSetChanged : adapter 에게 요청를 하면 RecyclerView 다시 그려주는 메소드
        adatper.notifyDataSetChanged();



    }


    @Override
    public void dateSelectedTwo(int year, int month, int dayOfMonth) {
        arrivalDaySelected = true;
        if (departDaySelected && departDaySelected) {
            btn_next.setEnabled(true);
        }

        arrivalYear = year;
        arrivalMonth = month;
        arrivalDay = dayOfMonth;

        String text = String.format("%02d.%02d.%02d" , year, month+1, dayOfMonth);
//        String  text = yearTwo + "." + (mouthTwo+1) + "." + dayOfMouth;
        textView_DataEnd.setText(text);


        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
        Date searchDepartTime = cal.getTime();
        airplaneSchedules = lab.getListByDepartDate(searchDepartTime);

        // notifyDataSetChanged : adapteer에게 요청를 하면 RcyclerView 다시 그려주는 메소드
        adatper.notifyDataSetChanged();


    }



    public void next(View view) {

        Intent intent = new Intent(AirplaneActivity.this, schedule1Activity.class);
        intent.putExtra(schedule1Activity.KEY_DEPART_YEAR, departYear);
        intent.putExtra(schedule1Activity.KEY_DEPART_MONTH, departMonth);
        intent.putExtra(schedule1Activity.KEY_DEPART_DAY, departDay);
        intent.putExtra(schedule1Activity.KEY_ARRIVAL_YEAR, arrivalYear);
        intent.putExtra(schedule1Activity.KEY_ARRIAVAL_MONTH, arrivalMonth);
        intent.putExtra(schedule1Activity.KEY_ARRIVAL_DAY, arrivalDay);
        intent.putExtra(schedule1Activity.KEY_TRAFFIC_TYPE, "osakaAirplane");
        intent.putExtra("id", id);

        startActivity(intent);
        finish();
    }
}
