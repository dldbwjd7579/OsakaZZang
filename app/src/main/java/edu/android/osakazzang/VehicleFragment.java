package edu.android.osakazzang;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleFragment extends Fragment {

    private Button btn_airPlane; // 비행기 버튼의 맴버변수 선언
    private Button btn_ship; // 배 버튼의 맴버변수 선언
    private VehicleFragmentListener listener; // 메인 액티비티 주소를 리스너에 전달


    public interface VehicleFragmentListener{
        void onVehicleClick(int vehicle);
    }

    public VehicleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof VehicleFragmentListener){
            listener = (VehicleFragmentListener) context; // 메인 액티비티 주소를
//            listener 맴버변수에 저장
        }
    }

    @Override
    public void onDetach() {
        super.onDetach(); // 메모리에서 다 사라짐
        listener = null; // 주소값을 지워버리겠다.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vehicle, container, false);

        btn_airPlane = (Button) view.findViewById(R.id.btn_AirPlane);
        btn_ship =(Button) view.findViewById(R.id.btn_Ship);


        btn_airPlane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(listener != null){
                    listener.onVehicleClick(1);
                }
            }
        });


        btn_ship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(listener != null){

                    listener.onVehicleClick(2);

                }
            }
        });


        return view;
    }



}
