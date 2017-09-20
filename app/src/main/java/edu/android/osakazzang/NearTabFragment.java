package edu.android.osakazzang;


import android.content.Context;
import android.location.Location;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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

    private GoogleMap googleMap;

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
                    markerCurrent = googleMap.addMarker(new MarkerOptions().position(lastLatLng).title(lastLatLng.toString()));
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

        ArrayAdapter<Place> adapter = new PlaceArrayAdapter(view.getContext(), -1, PlaceLab.getInstance().getPlaceList());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Place selectPlace = PlaceLab.getInstance().getPlaceList().get(i);
                selectedPName = selectPlace.getpName();
//                String phone = selectPlace.getpPhone();
//                String address = selectPlace.getpAddress();
                double lat = selectPlace.getpLat();
                double lon = selectPlace.getpLon();

                LatLng selectLatLan = new LatLng(lat, lon);
                Toast.makeText(getContext(), selectLatLan.toString(), Toast.LENGTH_SHORT).show();


                if(markerDestination == null){
                    markerDestination = googleMap.addMarker(new MarkerOptions().position(selectLatLan).title(selectedPName));
                }else{
                    markerDestination.setPosition(selectLatLan);
                }
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(selectLatLan, 17));
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

            ImageView image_photo = (ImageView) view.findViewById(R.id.image_item_photo);
            TextView text_name = (TextView) view.findViewById(R.id.text_item_name);
            TextView text_phone = (TextView) view.findViewById(R.id.text_item_phone);
            TextView text_address = (TextView) view.findViewById(R.id.text_item_address);

            String name = placeList.get(position).getpName();
            String phone = placeList.get(position).getpPhone();
            String address = placeList.get(position).getpAddress();
            switch (pagePosition) {
                case 0:
                    name += "-page1";
                    break;
                case 1:
                    name += "-page2";
                    break;
                case 2:
                    name += "-page3";
                    break;
            }
            image_photo.setImageResource(placeList.get(position).getpPhoto());
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
