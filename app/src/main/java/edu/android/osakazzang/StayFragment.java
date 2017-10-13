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
public class StayFragment extends Fragment {

    private static final String KEY_ARG2 ="arg_position2"; // 포지션 키값저장

    private int pagePostion;

    public void setPosition(int position){
        pagePostion = position;
    }

    public StayFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        pagePostion = args.getInt(KEY_ARG2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stay, container, false);
    }

    public static StayFragment newInstance(int position){
        StayFragment fragment = new StayFragment();
        Bundle args2 = new Bundle();
        args2.putInt(KEY_ARG2, position);
        fragment.setArguments(args2);

        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        RecyclerView recyclerView = view.findViewById(R.id.recycler2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new StayFragment.StayRecyclerAdapter());
    }

    class StayRecyclerAdapter extends RecyclerView.Adapter<StayItemViewHolder>{
        private AccommoLab lab = AccommoLab.getInstance(getContext());

        @Override
        public StayItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
           LayoutInflater inflater = LayoutInflater.from(getContext());
            View itemView = inflater.inflate(R.layout.stay_item, parent, false);

            StayItemViewHolder stayItemViewHolder = new StayItemViewHolder(itemView);
            return stayItemViewHolder;
        }

        @Override
        public void onBindViewHolder(StayItemViewHolder holder, final int position) {
            Accommo a  = lab.getAccommoList().get(position);
            holder.imageView.setImageResource(a.getaPhoto());
            holder.textName.setText(a.getaName());
            holder.textAddress.setText(a.getaAddress());
            holder.textPhon.setText(a.getaPhone());
            holder.textHttp.setText(""); //Http 없어서 일단 이렇게
            holder.StayCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lab.getAccommoList().get(position).setSelected2(((CheckBox) view).isChecked());
                }
            });
        }

        @Override
        public int getItemCount() {
            return lab.getAccommoList().size();
        }
    }

    class StayItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textName;
        private TextView textPhon;
        private TextView textAddress;
        private TextView textHttp;
        private CheckBox StayCheck;

        public StayItemViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.imageView2);
            textName = (TextView)itemView.findViewById(R.id.textStayName);
            textAddress = (TextView)itemView.findViewById(R.id.textStayAddress);
            textPhon = (TextView)itemView.findViewById(R.id.textStayPhone);
            textHttp = (TextView)itemView.findViewById(R.id.textStayHttp);
            StayCheck = (CheckBox)itemView.findViewById(R.id.StayCheckBox);
        }
    }


}
