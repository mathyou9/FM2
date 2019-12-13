package com.example.familymap.Fragments;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.familymap.Activities.PersonActivity;
import com.example.familymap.Models.DataCache;
import com.example.familymap.Models.Event_Model;
import com.example.familymap.Models.Person_Model;
import com.example.familymap.Models.Settings_Model;
import com.example.familymap.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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
            BitmapDescriptorFactory.HUE_BLUE,
            BitmapDescriptorFactory.HUE_CYAN,
            BitmapDescriptorFactory.HUE_GREEN,
            BitmapDescriptorFactory.HUE_MAGENTA,
            BitmapDescriptorFactory.HUE_ORANGE,
            BitmapDescriptorFactory.HUE_RED,
            BitmapDescriptorFactory.HUE_ROSE,
            BitmapDescriptorFactory.HUE_VIOLET,
            BitmapDescriptorFactory.HUE_YELLOW,
    };

    private TextView eventDetails;
    private ImageView imageDetails;
    private LinearLayout eventLinearLayout;
    private String eventID;
    private boolean fromEvent;
    private ArrayList<String> fathersSideIDs = new ArrayList<>();
    private ArrayList<String> mothersSideIDs = new ArrayList<>();
    private Person_Model user = null;

    Event_Model currentEvent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = DataCache.getInstance().getUser();
        DataCache.getInstance().setPersonsEvents();
        findMothersSide(user.getMotherID());
        findFathersSide(user.getMotherID());
        DataCache.getInstance().setMotherSideIDs(mothersSideIDs);
        DataCache.getInstance().setFatherSideIDs(fathersSideIDs);
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
        eventLinearLayout = view.findViewById(R.id.eventLinearLayout);

        return view;
    }
    @Override
    public void onMapReady(GoogleMap map) {
        final GoogleMap gmap = map;
        for(Map.Entry<String, Event_Model> event : DataCache.getInstance().getAllEvents().entrySet()){
            System.out.println(event);
            LatLng location = new LatLng(event.getValue().getLatitude(), event.getValue().getLongitude());
            List<String> eventTypes = new ArrayList<>(DataCache.getInstance().getEventTypes());
            marker = map.addMarker(new MarkerOptions()
                    .position(location)
                    .title(event.getValue().getEventType())
                    .draggable(false)
                    .icon(BitmapDescriptorFactory.defaultMarker(MARKERS[eventTypes.indexOf(event.getValue().getEventType().toUpperCase())%10])));
            marker.setTag(event.getValue().getEventID());
        }

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                gmap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                System.out.println("clicked");
                currentEvent = DataCache.getInstance().getEvents((String)marker.getTag());
                eventDetails.setText(textForMapFragment(currentEvent));
                if(DataCache.getInstance().getPerson(currentEvent.getPersonID()).getGender().equals("m")){
                    imageDetails.setImageDrawable(getResources().getDrawable(R.drawable.boy));
                } else {
                    imageDetails.setImageDrawable(getResources().getDrawable(R.drawable.girl));
                }
                return false;
            }
        });
        eventLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), PersonActivity.class);
                intent.putExtra(PersonActivity.PERSON_ACTIVITY_ID, currentEvent.getPersonID());
                startActivity(intent);
            }
        });

    }

    private void findMothersSide(String personID) {
        Person_Model child = DataCache.getInstance().getPersonMap().get(personID);
        String motherID = child.getMotherID();
        String fatherID = child.getFatherID();

        if (motherID != null && !motherID.equals("")) {
            mothersSideIDs.add(motherID);
            mothersSideIDs.add(fatherID);
            findMothersSide(motherID);
            findMothersSide(fatherID);
        }
    }

    private void findFathersSide(String personID) {
        Person_Model child = DataCache.getInstance().getPersonMap().get(personID);
        String motherID = child.getMotherID();
        String fatherID = child.getFatherID();

        if (fatherID != null && !fatherID.equals("")) {
            mothersSideIDs.add(motherID);
            mothersSideIDs.add(fatherID);
            findMothersSide(motherID);
            findMothersSide(fatherID);
        }
    }

    private String textForMapFragment(Event_Model event){
        StringBuilder sb = new StringBuilder();
        Person_Model person = DataCache.getInstance().getPerson(event.getPersonID());
        sb.append(person.getFirstName() + " " + person.getLastName() + '\n');
        sb.append(event.getEventType() + ": " + event.getCity() + ", " + event.getCountry() + " (" + event.getYear() + ")");
        return sb.toString();
    }

    public void setIDFromEventActivity(String eventID) {
        this.eventID = eventID;
        fromEvent = true;
    }

    private void addEventMarkers() {
        for (Map.Entry<String, Event_Model> marker : DataCache.getInstance().getAllEvents().entrySet()) {
            int color = 0;
            Event_Model event = marker.getValue();
            double lat = event.getLatitude();
            double lon = event.getLongitude();
            LatLng place = new LatLng(lat, lon);
            String gender = DataCache.getInstance().getPerson(event.getPersonID()).getGender();
//            DataCache.getInstance().setEventTypes();
            List<String> types = new ArrayList<>(DataCache.getInstance().getEventTypes());
            for (int i = 0; i < types.size(); i++) {
                if (types.get(i).equals(event.getEventType().toUpperCase())) {
                    color = i % MARKERS.length;
                }
            }
            if (!Settings_Model.getInstance().isMothersSide() && !Settings_Model.getInstance().isFathersSide()) {
                if (!mothersSideIDs.contains(event.getPersonID()) && !fathersSideIDs.contains(event.getPersonID())) {
                    filterMarkerByMF(gender, place, event, color);
                }
            }
            else if (!Settings_Model.getInstance().isMothersSide() && !mothersSideIDs.contains(event.getPersonID())) { //not showing mom side & doesn't contain id
                filterMarkerByMF(gender, place, event, color);
            }
            else if (!Settings_Model.getInstance().isFathersSide() && !fathersSideIDs.contains(event.getPersonID())) { //not showing dad side & doesn't contain id
                filterMarkerByMF(gender, place, event, color);
            }
            else if (Settings_Model.getInstance().isMothersSide() && Settings_Model.getInstance().isFathersSide()) { //showing both sides
                filterMarkerByMF(gender, place, event, color);
            }

        }
    }
    private void filterMarkerByMF(String gender, LatLng place, Event_Model event, int color) {
        Marker mapMarker;
        if (Settings_Model.getInstance().isMaleEvents()) {
            if (gender.toLowerCase().equals("m")) {
                mapMarker = googleMap.addMarker(new MarkerOptions().position(place).title(event.getEventType())
                        .icon(BitmapDescriptorFactory.defaultMarker(MARKERS[color])));
                mapMarker.setTag(event.getEventID());
            }
        }
        if (Settings_Model.getInstance().isFemaleEvents()) {
            if (!gender.toLowerCase().equals("m")) {
                mapMarker = googleMap.addMarker(new MarkerOptions().position(place).title(event.getEventType())
                        .icon(BitmapDescriptorFactory.defaultMarker(MARKERS[color])));
                mapMarker.setTag(event.getEventID());
            }
        }
    }


}

