package edu.android.osakazzang;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Year;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * 출발날짜 정보를 갖고 있는 다이얼로그
 */
public class DataFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener{

    public interface  DataSelectListener{
        void dataSelected(int year, int mouth, int dayOfMouth);
    }

    private DataSelectListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof AirplaneActivity){
            listener=(DataSelectListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 현재 날짜 정보
        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);

        int month = cal.get(Calendar.MONTH);

        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog dlg =
                new DatePickerDialog(getContext(),
                        this, year, month, dayOfMonth);

        return dlg;


    }

    public DataFragment() {
        // Required empty public constructor
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Toast.makeText(getContext(), year + " / " + (month+1) + " / " + dayOfMonth
                , Toast.LENGTH_SHORT).show();

        if(listener != null){
            listener.dataSelected(year, month, dayOfMonth);
        }


    }
}
