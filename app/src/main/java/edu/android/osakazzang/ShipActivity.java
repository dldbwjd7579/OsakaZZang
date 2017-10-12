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

public class ShipActivity extends AppCompatActivity implements
        DataFragment.DataSelectListener,DataEndFragment.DataSelectListenerTwo{

    private static final String KEY_DAY_INDEX = "key_day_index";
    private int viewHolderCount;
    private TextView textView_DataStart;
    private TextView textView_DataEnd;
    private RecyclerView recyclerView;
    private List<ShipInfo> ShipInfos;
    private ShipAdatper adapter;
    private boolean departDaySelected, arrivalDaySelected;
    private Button btn_next;

    private AirplaneScheduleLab lab = AirplaneScheduleLab.getInstance();
    private int shipdepartYear, shipdepartMonth, shipdepartDay, shiparrivalYear, shiparrivalMonth, shiparrivalDay;


    // ship 네트워크주소 URL 확인
    private static final String URL_ShipNAME_INFO_1 =

            "http://sapa.busanpa.com/OpenAPI/service/rest/PlyInfoService?" +
                    "ServiceKey=" +
                    "bz3LFDB5cyeLlvAax0OZW7%2FCdTxgxbVEjEaP9zq%2BGlFIjPHV1BnK0y2AVhfQLtwxEKgdCcV%2B2U5zD2Rj4vwyhA%3D%3D" +
                    "&plyCat=C&cat=0&numOfRows=10&pageNo=1" +
                    "&departDt=";

    private String Ship_DATE;


//    private static final String URL_ShipNAME_INFO_2 = "&shipNmKor=Incheon"
//            +"&shipNmKor=Korea";   //선박명




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship);

        // 출발날짜 텍스트를 찾음
        textView_DataStart = (TextView) findViewById(R.id.textView_DataStart);
        // 도착날짜 텍스트를 찾음
        textView_DataEnd = (TextView) findViewById(R.id.textView_DataEnd);
        btn_next= (Button) findViewById(R.id.btn_next);



        // 출발날짜 텍스트를 눌렀을 때 하는 다이얼로그
        textView_DataStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataFragment dlg = new DataFragment();
                dlg.show(getSupportFragmentManager(),"Calender_dlg1");
            }
        });

        // 도착날짜 텍스트를 눌렀을때 하는 다이얼로그
        textView_DataEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataEndFragment dlg = new DataEndFragment();
                dlg.show(getSupportFragmentManager(), "Calender_dlg2");

            }
        });
        // 1. RecyLerView 를 찾음
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // 2. RecyLerView에 LayoutManger 를 설정
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 3. RecyclerView에 Adapter 를 설정
        ShipInfos = new ArrayList<>();
        adapter = new ShipAdatper();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        startNetWorkTask();
    }

    // 네트워크 설정
    private void startNetWorkTask() {
        // 사용가능한 네트워크가 있는지 검사
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connMgr.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isAvailable()) {
            // TODO: AsyncTask를 사용해서 URL 주소(Open API)로 요청을 보내고
            // 응답(배 역 주소/전화번호 데이터)을 받음
            Log.i("logTag", "netInfo isAvailable()");

            Ship_DATE = "20160808";

            GetShipInfoTask task = new GetShipInfoTask();
            task.execute(URL_ShipNAME_INFO_1 + Ship_DATE);

        }


    }

    public class GetShipInfoTask extends AsyncTask<String, Void, String>{

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
                conn.setRequestMethod("GET"); // 생략 불가

                int resCode = conn.getResponseCode();
                if (resCode == HttpURLConnection.HTTP_OK) {
                    Log.i("logTag", "HttpURLConnction OK");
                }

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

            } catch (Exception e) {
                e.printStackTrace();
                try {

                    br.close();
                    conn.disconnect();

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }


            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("logTag", " onPostExecute()");
            Log.i("logTag", s);


            try {
                ShipInfoXmlParser parser = new ShipInfoXmlParser();


                List<ShipInfo> list = parser.parser(s);

                Log.i("logTag", "list size : " + list.size());

                // StringBuffer buffer = new StringBuffer();
//                for (AirplaneInfo airplaneInfo : list) {
//                    Log.i("logTag", "airplaneInfo : \n" + airplaneInfo.toString());
//
//                    buffer.append(airplaneInfo.toString())
//                            .append("\n\n");
//                }

                ShipInfos = list;
                Log.i("test", "***** size=" + ShipInfos.size());
                adapter.notifyDataSetChanged();

            } catch (Exception e) {
                Log.e("logTag", e.getMessage());
                e.printStackTrace();
            }
        }

    }


    public class ShipAdatper extends RecyclerView.Adapter<ShipViewHolder>{

        @Override   // 레이아웃(XML)을 만들수 있는 LayoutInFlater 객체를 찾음
        public ShipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            viewHolderCount++;
            LayoutInflater inflater = LayoutInflater.from(ShipActivity.this);
            // Adapter 안에
            // ShipActivity 주소가 와야함

            // 아이템 하나를 보여주는 View 객체를 생성
            View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

            // 아이템 하나의 View 를 재활용 하는 ViewHolder 객체를 생성
            ShipViewHolder viewHolder = new ShipViewHolder(view);

            // 생성된 viewHolder 객체를 리턴

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ShipViewHolder holder, int position) {

            if(ShipInfos != null){
                // 재활용 틀 viewHolder에 아이템 내용을 채워줄때 호출
                holder.textView.setText(ShipInfos.get(position).toString());

            }else{
                Toast.makeText(ShipActivity.this, "NULL", Toast.LENGTH_SHORT).show();
                holder.textView.setText("");
            }
        }

        @Override
        public int getItemCount() {
            return ShipInfos.size();
        }
    }


    // onBindViewHolder 에서 세팅하기 위해 viewHolder에서 맴버변수 선언
    public class ShipViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private int index;

        public void setIndex(int index){
            this.index = index;
        }


        public ShipViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(ShipActivity.this, textView.toString(), Toast.LENGTH_SHORT).show();
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
        shipdepartYear = year;
        shipdepartMonth = month;
        shipdepartDay = dayOfMonth;

        String text = String.format("%02d.%02d.%02d", year, month+1, dayOfMonth);

//            String text = year + "." + (mouth+1) + "." +dayOfMouth;
            textView_DataStart.setText(text);

            //TODO : URL 만들어서 Task에 적용
            startNetWorkTask();

            // 날짜 정보를 시간정보로 바꿈
            Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
            Date searchDepartTime = cal.getTime();
            // notifyDataSetChanged : adapter 에게 요청하면 Rcyclewview 다시 그려주는 메소드
            adapter.notifyDataSetChanged();


    }

    @Override
    public void dateSelectedTwo(int yearTwo, int monthTwo, int dayOfMonthTwo) {

        arrivalDaySelected = true;
        if (departDaySelected && departDaySelected) {
            btn_next.setEnabled(true);
        }

        shiparrivalYear = yearTwo;
        shiparrivalMonth = monthTwo;
        shiparrivalDay = dayOfMonthTwo;

        String text = String.format("%02d.%02d.%02d", yearTwo, monthTwo+1, dayOfMonthTwo);

        textView_DataEnd.setText(text);

        startNetWorkTask();

        Calendar cal = new GregorianCalendar(yearTwo, monthTwo, dayOfMonthTwo);
        Date searchDepartTime = cal.getTime();
         // notifyDataSetChanged : adapter 에게 요청하면 Rcyclewview 다시 그려주는 메소드
        adapter.notifyDataSetChanged();


        }



    public void next(View view) {


        Intent intent = new Intent(ShipActivity.this, schedule1Activity.class);

        intent.putExtra(schedule1Activity.KEY_DEPART_YEAR, shipdepartYear);
        intent.putExtra(schedule1Activity.KEY_DEPART_MONTH, shipdepartMonth);
        intent.putExtra(schedule1Activity.KEY_DEPART_DAY, shipdepartDay);
        intent.putExtra(schedule1Activity.KEY_ARRIVAL_YEAR, shiparrivalYear);
        intent.putExtra(schedule1Activity.KEY_ARRIAVAL_MONTH, shiparrivalMonth);
        intent.putExtra(schedule1Activity.KEY_ARRIVAL_DAY, shiparrivalDay);
        intent.putExtra(schedule1Activity.KEY_TRAFFIC_TYPE, "osakaship");

        startActivity(intent);

    }
}
