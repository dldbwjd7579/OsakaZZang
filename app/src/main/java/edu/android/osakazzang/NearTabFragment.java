package edu.android.osakazzang;


import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NearTabFragment extends Fragment implements OnMapReadyCallback{
    private static final long UPDATE_INTERVAL = 2 * 1000;
    private static final long FASTEST_UPDATE_INTERVAL = UPDATE_INTERVAL / 2;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient locationClient;
    private Location location;
    private LocationCallback locationCallback;

    private static final String KEY = "bundle_key";
    private int pagePosition;
    private LatLng lastLatLng;
    private String selectedPName;
    private ListView listView;
    private double lastLat;
    private double lastLon;

    private Marker markerCurrent;
    private Marker markerDestination;
    private boolean isAppStart = false;

    private LatLng selectLatLan;
    private GoogleMap googleMap;
    private PolylineOptions polylineOptions;

    private final String URL_DIRECTION_INFO_ORIGIN =
            "https://maps.googleapis.com/maps/api/directions/json?"
                    +"origin=";
    private final String URL_DIRECTION_INFO_DESTINATION = "&destination=";
    private final String URL_DIRECTION_INFO_END =
            "&key=AIzaSyAhe9Y8xuECA9I1-tFW_hq15Lpv5eRDxUI"
            +"&language=ko&unit=metric";

    private Polyline polyline;
    private ArrayList<Polyline> polyLines = new ArrayList<>();


    //이동경로 계산
    public class GetDirectionInfoTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            URL url = null;
            HttpURLConnection conn = null;

            InputStream in = null;
            InputStreamReader reader = null;
            BufferedReader br = null;
            StringBuffer data = new StringBuffer();

            try {
                String FULL_URL = URL_DIRECTION_INFO_ORIGIN
                        //+ lastLat +","+ lastLon
                        +"34.4320024,135.23039389999997" //간사이 국제 공항
                        + URL_DIRECTION_INFO_DESTINATION
                        + selectLatLan.latitude + "," + selectLatLan.longitude
                        + URL_DIRECTION_INFO_END;

                url = new URL(FULL_URL);
                conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.setReadTimeout(1000*5);
                conn.setConnectTimeout(1000*5);

                int rescode = conn.getResponseCode();
                if(rescode == HttpURLConnection.HTTP_OK){
                    in = conn.getInputStream();
                    reader = new InputStreamReader(in);
                    br = new BufferedReader(reader);

                    while(true){
                        String line = br.readLine();
                        if(line == null){
                            break;
                        }
                        data.append(line);
                    }

                }else{
                    Toast.makeText(getContext(), "네트워크 연결 실패", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    br.close();
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return data.toString();
        }

        protected void onPostExecute(String result){
            Gson gson = new Gson();
            Direction direction = gson.fromJson(result, Direction.class);
            Log.i("logTag", direction.getDirectionList().size()+"");

            List<Direction.DirectionItem> list = direction.getDirectionList();
            for(Direction.DirectionItem item : list){
                LatLng dirStartLatlng = item.getStartLatLng();
                LatLng dirEndLatlng = item.getEndLatLng();

                /*googleMap.addMarker(new MarkerOptions().position(dirStartLatlng));
                googleMap.addMarker(new MarkerOptions().position(dirEndLatlng));*/

                polylineOptions = new PolylineOptions();
                googleMap.addPolyline(polylineOptions.add(dirStartLatlng, dirEndLatlng).geodesic(true).color(Color.RED));

            }
        }

    }
    //


    public NearTabFragment() {
    }

    public void setPagePosition(int position) {
        pagePosition = position;
    }

    public static NearTabFragment newInstance(int position) {
        NearTabFragment fragment = new NearTabFragment();
        Bundle args = new Bundle();
        args.putInt(KEY, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        pagePosition = args.getInt(KEY);

        /////////////////////////////////////////////////////////
        locationClient = LocationServices.getFusedLocationProviderClient(getContext());
        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                location = locationResult.getLastLocation();
                lastLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                if(markerCurrent == null){
                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
                    markerCurrent = googleMap.addMarker(new MarkerOptions().position(lastLatLng).title("현재 위치").icon(bitmapDescriptor));
                    Log.i("logTag", lastLatLng.toString());
                }else{
                    markerCurrent.setPosition(lastLatLng);
                }
                if(isAppStart == false) {
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastLatLng, 17));
                    isAppStart = true;
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
            locationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        locationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_near_tab, container, false);
        listView = (ListView) view.findViewById(R.id.place_list);
        ArrayAdapter<Place> placeAdapter;
        placeAdapter = new PlaceArrayAdapter(view.getContext(), -1, PlaceLab.getInstance().getPlaceList());
        listView.setAdapter(placeAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                double lat = 0;
                double lng = 0;

                switch (pagePosition){
                    case 0:
                        Place selectPlace = PlaceLab.getInstance().getPlaceList().get(i);
                        selectedPName = selectPlace.getpName();
                        lat = selectPlace.getpLat();
                        lng = selectPlace.getpLon();
                        break;
                    case 1:
                        Accommo selectAccommo = AccommoLab.getInstance().getAccommoList().get(i);
                        selectedPName = selectAccommo.getaName();
                        lat = selectAccommo.getaLat();
                        lng = selectAccommo.getaLng();
                        break;
                    case 2:
                        selectPlace = PlaceLab.getInstance().getPlaceList().get(i);
                        selectedPName = selectPlace.getpName();
                        lat = selectPlace.getpLat();
                        lng = selectPlace.getpLon();
                        break;
                }

                selectLatLan = new LatLng(lat, lng);
                Log.i("logTag", selectLatLan.toString());


                if(markerDestination == null){
                    markerDestination = googleMap.addMarker(new MarkerOptions().position(selectLatLan).title(selectedPName));
                }else{
                    googleMap.clear();
                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
                    markerCurrent = googleMap.addMarker(new MarkerOptions().position(lastLatLng).title("현재 위치").icon(bitmapDescriptor));
                    markerDestination = googleMap.addMarker(new MarkerOptions().position(selectLatLan).title(selectedPName));
                    /*markerDestination.setVisible(false);
                    markerDestination.setTitle(selectedPName);
                    markerDestination.setVisible(true);
                    markerDestination.setPosition(selectLatLan);*/
                }
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(selectLatLan, 15));


                //경로
                ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = manager.getActiveNetworkInfo();
                if(netInfo != null && netInfo.isAvailable()){
                    GetDirectionInfoTask task = new GetDirectionInfoTask();
                    task.execute();
                }
            }
        });

        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        lastLatLng = new LatLng(lastLat, lastLon);
        Log.i("logTag", lastLatLng + "  :  in onMapReady");

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastLatLng, 17));
    }

    public class PlaceArrayAdapter extends ArrayAdapter<Place> {

        Context context;
        List<Place> placeList;

        public PlaceArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Place> objects) {
            super(context, resource, objects);
            this.context = context;
            this.placeList = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.near_item_layout, parent, false);

            //
            List<Accommo> accommoList = AccommoLab.getInstance().getAccommoList();

            ImageView image_photo = (ImageView) view.findViewById(R.id.image_item_photo);
            TextView text_name = (TextView) view.findViewById(R.id.text_item_name);
            TextView text_phone = (TextView) view.findViewById(R.id.text_item_phone);
            TextView text_address = (TextView) view.findViewById(R.id.text_item_address);

            String name = "";
            String phone = "";
            String address = "";
            switch (pagePosition) {
                case 0:
                    name = placeList.get(position).getpName();
                    phone = placeList.get(position).getpPhone();
                    address = placeList.get(position).getpAddress();
                    image_photo.setImageResource(placeList.get(position).getpPhoto());
                    break;
                case 1:
                    name = accommoList.get(position).getaName();
                    phone = accommoList.get(position).getaPhone();
                    address = accommoList.get(position).getaAddress();
                    image_photo.setImageResource(accommoList.get(position).getaPhoto());
                    break;
                case 2:
                    name += "-page3";
                    break;
            }
            text_name.setText(name);
            text_phone.setText(phone);
            text_address.setText(address);

            return view;
        }
    }

    public void setLastLocation(double latitude, double longitude) {
        lastLat = latitude;
        lastLon = longitude;
        lastLatLng = new LatLng(lastLat, lastLon);
        Log.i("logTag", lastLatLng + "  :  in setLastLocation");
    }
}
