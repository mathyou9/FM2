package com.example.familymap.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.familymap.Models.DataCache;
import com.example.familymap.Models.Event_Model;
import com.example.familymap.Models.Person_Model;
import com.example.familymap.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonActivity extends AppCompatActivity {

    private TextView firstName;
    private TextView lastName;
    private TextView gender;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter listAdapter;
    private HashMap<String, List<String>> dataHash;
    private List<String> data;
    Person_Model person;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_activity);
        firstName = findViewById(R.id.firstNameTextView);
        lastName = findViewById(R.id.lastNameTextView);
        gender = findViewById(R.id.genderTextView);
        String personID = getIntent().getStringExtra("PersonID");
        person = DataCache.getInstance().getPerson(personID);
        firstName.setText(person.getFirstName());
        lastName.setText(person.getLastName());
        if(person.getGender().equals("m")){
            gender.setText("Male");
        } else {
            gender.setText("Female");
        }
        expandableListView = findViewById(R.id.expandableListView);
        PopulateExpandable();
    }
    public void PopulateExpandable(){

        listAdapter = new ExpandableListAdapter(getApplicationContext(), data, dataHash );
    }
}
