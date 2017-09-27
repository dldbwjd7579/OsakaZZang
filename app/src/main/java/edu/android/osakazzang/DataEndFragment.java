package edu.android.osakazzang;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * 도착날짜 정보를 갖고 있는 다이얼로그
 */
public class DataEndFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener{

    public interface DataSelectListenerTwo{
        void dateSelectedTwo(int year, int mouth, int dayOfMouth);

    }
    private DataSelectListenerTwo listenerTwo;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof DataSelectListenerTwo){
            listenerTwo = (DataSelectListenerTwo) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listenerTwo = null;
    }

    public DataEndFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 현재 날짜 정보
        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);
        int mouth =cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dlg = new DatePickerDialog(getContext(),
                this, year, mouth, dayOfMonth);

        return dlg;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int mouth, int dayOfMonth) {

        Toast.makeText(getContext(), year + " / " +(mouth+1) + " / " + dayOfMonth
                ,Toast.LENGTH_SHORT).show();


        if(listenerTwo != null){
            listenerTwo.dateSelectedTwo(year, mouth, dayOfMonth);
        }




    }
}
