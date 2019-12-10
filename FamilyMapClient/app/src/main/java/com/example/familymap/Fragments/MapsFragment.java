package com.example.familymap.Fragments;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.familymap.Models.DataCache;
import com.example.familymap.Models.Event_Model;
import com.example.familymap.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private ImageButton imageButton;
    private Marker marker;

    private List<Integer> colorCoordinator = new ArrayList<>();

    private float[] MARKERS = new float[]{
            BitmapDescriptorFactory.HUE_AZURE,
            BitmapDescriptorFactory.HUE_YELLOW,
            BitmapDescriptorFactory.HUE_ROSE,
            BitmapDescriptorFactory.HUE_BLUE,
            BitmapDescriptorFactory.HUE_VIOLET,
            BitmapDescriptorFactory.HUE_ORANGE,
            BitmapDescriptorFactory.HUE_CYAN,
            BitmapDescriptorFactory.HUE_GREEN,
            BitmapDescriptorFactory.HUE_RED,
            BitmapDescriptorFactory.HUE_MAGENTA
    };

    private TextView eventDetails;
    private ImageView imageDetails;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.maps_fragment, container, false);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
        eventDetails = view.findViewById(R.id.mapFragmentTextView);
        imageDetails = view.findViewById(R.id.mapFragmentImageView);

        return view;
    }
    @Override
    public void onMapReady(GoogleMap map) {
        for(Map.Entry<String, Event_Model> event : DataCache.getInstance().getAllEvents().entrySet()){
            System.out.println(event);
            LatLng location = new LatLng(event.getValue().getLatitude(), event.getValue().getLongitude());
            List<String> eventTypes = new ArrayList<>(DataCache.getInstance().getEventTypes());
            marker = map.addMarker(new MarkerOptions()
                    .position(location)
                    .title(event.getValue().getEventType())
                    .draggable(false)
                    .icon(BitmapDescriptorFactory.defaultMarker(MARKERS[eventTypes.indexOf(event.getValue().getEventType())%10])));
        }
        map.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                System.out.println("clicked");
                eventDetails.setText("Clicked " + marker.getTitle());
                return false;
            }
        });
    }


}

