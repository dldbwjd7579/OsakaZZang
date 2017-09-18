package edu.android.osakazzang;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class CloseFragment extends DialogFragment {

    private Button yes, no;


    public interface CloseListener{
        void yesInfo();
        void noInfo();
    }

    private CloseListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof CloseListener){
            listener = (CloseListener)context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    public CloseFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_close, null);



        yes = view.findViewById(R.id.btn_yes);
        no = view.findViewById(R.id.btn_no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.yesInfo();


            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.noInfo();
                }
            }
        });



        builder.setView(view);
        return builder.show();
    }
}
