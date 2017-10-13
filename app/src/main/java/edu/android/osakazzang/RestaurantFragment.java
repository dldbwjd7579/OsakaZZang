package edu.android.osakazzang;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantFragment extends Fragment {


    private static final String KEY_ARG3 = "time"; // 포지션 저장

    private int arrivalTime;

    public RestaurantFragment() {

    }

    public void setArrivalTime(int position) {
        arrivalTime = position;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        arrivalTime = args.getInt(KEY_ARG3);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restaurant, container, false);
    }

    public static RestaurantFragment newInstance(int arrival) {
        RestaurantFragment fragment = new RestaurantFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_ARG3, arrival);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        RecyclerView recyclerView = view.findViewById(R.id.recycler3);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new restaurantRecyclerAdapter());

    }

    class restaurantRecyclerAdapter extends RecyclerView.Adapter<restaurantItemViewHolder> {
        private RestaurantLab lab = RestaurantLab.getInstance();

        @Override
        public restaurantItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View itemview = inflater.inflate(R.layout.restaurant_item, parent, false);

            restaurantItemViewHolder restaurantItemViewHolder = new restaurantItemViewHolder(itemview);
            return restaurantItemViewHolder;
        }


        @Override
        public void onBindViewHolder(restaurantItemViewHolder holder, final int position) {
            Log.i("tag", "arrivalTime" + arrivalTime);
            Restaurant rs = lab.find(arrivalTime).get(position);
            Log.i("tag", "size=" + lab.find(arrivalTime).size());

            holder.imageView.setImageResource(rs.getImageId());
            holder.textName.setText(rs.getName());
            holder.textAdress.setText(rs.getAddress());
            holder.textPhone.setText(rs.getPhone());
            holder.textOpen.setText("오픈시간" + rs.getOpen() + "시");
            holder.textClose.setText("닫는시간" + rs.getClose() + "시");
            holder.RestuarantCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lab.find(arrivalTime).get(position).setSelected3(((CheckBox) view).isChecked());
                }
            });
        }

        @Override
        public int getItemCount() {

            Log.i("mytag", "RestaurantFragment:: arrivalTime=" + arrivalTime);
            return lab.find(arrivalTime).size();

        }

    }

    class restaurantItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textName, textAdress, textPhone, textOpen, textClose;
        private CheckBox RestuarantCheck;

        public restaurantItemViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageView3);
            this.textName = itemView.findViewById(R.id.textStayName);
            this.textAdress = itemView.findViewById(R.id.textAddress);
            this.textPhone = itemView.findViewById(R.id.textPhone);
            this.textOpen = itemView.findViewById(R.id.textOpen);
            this.textClose = itemView.findViewById(R.id.textclose);
            this.RestuarantCheck = itemView.findViewById(R.id.RestuarantCheck);
        }
    }
}
