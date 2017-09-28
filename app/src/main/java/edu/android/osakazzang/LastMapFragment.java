package edu.android.osakazzang;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class LastMapFragment extends Fragment implements OnMapReadyCallback{

    private static final String KEY = "key";

    private ListView listView;
    // TODO : 리스트 배열 선언 -> 모델 클래스 타입의 리스트 생성
    private int position;

    // 구글 맵 참조 변수
    private GoogleMap map;

    public void setPosition(int position){
        this.position = position;
    }

    public LastMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_last_map, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public static LastMapFragment newInstance(int position){
        LastMapFragment fragment = new LastMapFragment();

        Bundle args = new Bundle();
        args.putInt(KEY, position);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // TODO : 리스트뷰에서 선택 된 position 값을 가지고
        // TODO : position 값에 대한 맵의 위치를 띄워 줘야 함.

        // 구글 맵 객체를 불러온다
        map = googleMap;

        // TODO : 관광지 위치 값이 들어가야 할 코드
        // LatLng seoul = new LatLng(37.499482, 127.031091);

        // 구글 맵에 표시할 마커에 대한 옵션 설정
        MarkerOptions options = new MarkerOptions();
//        options.position(); -> () 안에 위치 값이 저장된 변수 입력
//        options.title(); -> () 안에 관광지 이름이 들어가야함

//        map.addMarker(); -> 마커를 띄어 줌
//        map.animateCamera(CameraUpdateFactory.newLatLngZoom()); -> () 안에 위치 값이 들어간 변수, 줌 크기(17)

    }
}
