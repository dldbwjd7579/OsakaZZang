package edu.android.osakazzang;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by SUT on 2017-10-11.
 */

public class TotalArrayAdapter extends ArrayAdapter<Food> {

    private Context context;
    private List<Food> dataList;

    public TotalArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Food> objects) {
        super(context, -1, objects);
        this.context = context;
        this.dataList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.total_list_item, parent, false);

        TextView name = (TextView)view.findViewById(R.id.textView_name);
        TextView phone = (TextView)view.findViewById(R.id.textView_phone);
        TextView address = (TextView)view.findViewById(R.id.textView_address);
        TextView openTime = (TextView)view.findViewById(R.id.textView_opentime);
        TextView closeTime = (TextView)view.findViewById(R.id.textView_closetime);

        name.setText(dataList.get(position).getfName());
        phone.setText(dataList.get(position).getfPhone());
        address.setText(dataList.get(position).getfAddress());
        //TODO : Food에 open/close 시간 추가해서 setText 해주기


        return view;
    }
}
