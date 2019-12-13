package com.example.familymap.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.familymap.Models.DataCache;
import com.example.familymap.Models.Event_Model;
import com.example.familymap.Models.Person_Model;
import com.example.familymap.Models.Settings_Model;
import com.example.familymap.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PersonActivity extends AppCompatActivity {

    private TextView firstName;
    private TextView lastName;
    private TextView gender;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter listAdapter;
    private HashMap<String, List<String>> dataHash;
    private List<String> data;
    private Person_Model person;
    private Person_Model spouse;
    private Person_Model child;
    private String personID;
    public static final String PERSON_ACTIVITY_ID = "com.example.familymapclient.Views.PersonActivity";
    private Event_Model[] events;
    private List<String> dataHeader;
    HashMap<String, List<String>> dataChild;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_activity);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        firstName = findViewById(R.id.firstNameTextView);
        lastName = findViewById(R.id.lastNameTextView);
        gender = findViewById(R.id.genderTextView);
        personID = getIntent().getStringExtra("PersonID");
        person = DataCache.getInstance().getPerson(personID);
        firstName.setText(person.getFirstName());
        lastName.setText(person.getLastName());
        if(person.getGender().equals("m")){
            gender.setText("Male");
        } else {
            gender.setText("Female");
        }
        expandableListView = findViewById(R.id.expandableListView);
        findFamily();
        PopulateExpandable();
        listAdapter = new ExpandableListAdapter(this, dataHeader, dataChild);
        expandableListView.setAdapter(listAdapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (groupPosition == 0) {
                    Intent intent = new Intent(PersonActivity.this, EventActivity.class);
                    intent.putExtra("EventID", events[childPosition].getEventID());
                    startActivity(intent);
                }
                if (groupPosition == 1) {
                    if (childPosition == 0) {
                        Intent intent = new Intent(PersonActivity.this, PersonActivity.class);
                        intent.putExtra("PersonID", spouse.getPersonID());
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(PersonActivity.this, PersonActivity.class);
                        intent.putExtra("PersonID", child.getPersonID());
                        startActivity(intent);
                    }
                }
                return false;
            }
        });
    }
    public void PopulateExpandable(){
        events = DataCache.getInstance().getEventsForIndividual().get(personID);
        dataHeader = new ArrayList<>();
        dataChild = new HashMap<>();
        List<String> eventList = new ArrayList<>();

        if (Settings_Model.getInstance().isFemaleEvents() && Settings_Model.getInstance().isMaleEvents()) {
            for (Event_Model event : events) {
                eventList.add(eventString(event));
            }
        }
        else if (Settings_Model.getInstance().isFemaleEvents() && !Settings_Model.getInstance().isMaleEvents()) {
            for (Event_Model event : events) {
                Person_Model p = DataCache.getInstance().getPerson(event.getPersonID());
                if (p.getGender().toLowerCase().equals("f")) {
                    eventList.add(eventString(event));
                }
            }
        }
        else if (Settings_Model.getInstance().isMaleEvents() && !Settings_Model.getInstance().isFemaleEvents()) {
            for (Event_Model event : events) {
                Person_Model p = DataCache.getInstance().getPerson(event.getPersonID());
                if (p.getGender().toLowerCase().equals("m")) {
                    eventList.add(eventString(event));
                }
            }
        }

        dataHeader.add("LIFE EVENTS");
        dataChild.put("LIFE EVENTS", eventList);

        //FAMILY
        List<String> familyList = new ArrayList<>();

        if (spouse != null) {
            String spouseStr = spouse.getFirstName() + " " + spouse.getLastName() + "\n" + "Spouse";
            familyList.add(spouseStr);
        }
        if (child != null) {
            String childStr = child.getFirstName() + " " + child.getLastName() + "\n" + "Child";
            familyList.add(childStr);
        }

        dataHeader.add("FAMILY");
        dataChild.put("FAMILY", familyList);
    }

    private void findFamily() {
        Person_Model[] persons = DataCache.getInstance().getPersonArray();

        for (Person_Model value : persons) {
            if (value.getPersonID() != null) {
                if (value.getPersonID().equals(person.getSpouseID())) {
                    spouse = value;
                    break;
                }
            }
        }
        Person_Model[] children = DataCache.getInstance().getPersonArray();

        for (Person_Model value : children) {
            if (value.getMotherID() != null && value.getFatherID() != null) {
                child = value;
                break;
            }
        }
    }

    private String eventString(Event_Model event) {
        return event.getEventType().toUpperCase() + ": " + event.getCity() + ", " + event.getCountry() +
                " (" + event.getYear() + ") " + "\n" + person.getFirstName() + " " +
                person.getLastName();
    }
}
