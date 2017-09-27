package edu.android.osakazzang;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TravelFragment extends Fragment {

    private  static final String KEY_ARG1 = "arg_arrival_time"; // 포지션 저장값

    private TravelLab lab = TravelLab.getInstance();

    private int arrivalTime; // 도착 시간(?)

    public void setArrivalTime(int position) {
        arrivalTime = position;
    }



    public TravelFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        arrivalTime = args.getInt(KEY_ARG1);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_travel, container, false);
    }


    public static TravelFragment newInstance(int arrival){
        TravelFragment fragment = new TravelFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_ARG1, arrival);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new TravelRecyclerAdapter());
    }

    class TravelRecyclerAdapter extends RecyclerView.Adapter<TravelItemViewHolder> {

        @Override
        public TravelItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View itemView = inflater.inflate(R.layout.travel_item, parent, false);

            TravelItemViewHolder viewHolder = new TravelItemViewHolder(itemView);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder (TravelItemViewHolder holder, final int position) {
            // open <= 도착 시간 <= close인 여행지 정보만 추출
            final Travel travel = lab.find(arrivalTime).get(position);
            holder.imageView.setImageResource(travel.getImageId());
            holder.textName.setText(travel.getName());
            holder.textAddress.setText(travel.getAdress());
            holder.textPhone.setText(travel.getPhone());
            //setText안에는 문자열을 넣어야하므로 int일떄는 사용할 수 없다
            holder.textOpen.setText("여는시간 " + travel.getOpen() + "시");
            holder.textClose.setText("닫는시간 " + travel.getClose() + "시");
            holder.travelCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // -> 체크박스가 선택됐는지 안됐는지를 저장
                    lab.getList().get(position).setSelected(((CheckBox) view).isChecked());
                }
            });

        }

        @Override
        public int getItemCount() {
            return lab.find(arrivalTime).size();
        }
        // RecyclerView의 아이템을 저장하고 보여주는 역할을 담당


    }

    class TravelItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textName, textAddress, textPhone;
        private TextView textOpen, textClose;
        private CheckBox travelCheck;
        // TODO: CheckBox 멤버 변수 선언

        public TravelItemViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageView);
            this.textName = itemView.findViewById(R.id.text_name);
            this.textAddress = itemView.findViewById(R.id.text_address);
            this.textPhone = itemView.findViewById(R.id.textPhone);
            this.textOpen= itemView.findViewById(R.id.textOpen);
            this.textClose = itemView.findViewById(R.id.textClose);
            this.travelCheck = itemView.findViewById(R.id.TravelCheck);
            // TODO: CheckBox 멤버 변수 찾기(findViewById)

        }// 뷰를 재활용

    }
}
