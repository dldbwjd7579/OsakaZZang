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
        private StayLab lab = StayLab.getInstance();

        @Override
        public StayItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
           LayoutInflater inflater = LayoutInflater.from(getContext());
            View itemView = inflater.inflate(R.layout.stay_item, parent, false);

            StayItemViewHolder stayItemViewHolder = new StayItemViewHolder(itemView);
            return stayItemViewHolder;
        }

        @Override
        public void onBindViewHolder(StayItemViewHolder holder, final int position) {
            Stay stay  = lab.getList().get(position);
            holder.imageView.setImageResource(stay.getPhotoId());
            holder.textView.setText(stay.getName());
            holder.textView.setText(stay.getLocation());
            holder.textView.setText(stay.getPhone());
            holder.textView.setText(stay.getHttp());
            holder.textView.setText(stay.getPrice());
            holder.StayCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lab.getList().get(position).setSelected2(((CheckBox) view).isChecked());
                }
            });
        }

        @Override
        public int getItemCount() {
            return lab.getList().size();
        }
    }

    class StayItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;
        private CheckBox StayCheck;

        public StayItemViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageView2);
            this.textView = itemView.findViewById(R.id.text_name);
            this.textView = itemView.findViewById(R.id.textLocation);
            this.textView = itemView.findViewById(R.id.textPhone);
            this.textView = itemView.findViewById(R.id.textHttp);
            this.textView = itemView.findViewById(R.id.textPrice);
            this.StayCheck = itemView.findViewById(R.id.StayCheck);
        }
    }


}
