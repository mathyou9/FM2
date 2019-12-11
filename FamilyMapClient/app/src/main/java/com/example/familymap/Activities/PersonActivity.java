package com.example.familymap.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.familymap.Models.DataCache;
import com.example.familymap.Models.Person_Model;
import com.example.familymap.R;

public class PersonActivity extends AppCompatActivity {

    private TextView firstName;
    private TextView lastName;
    private TextView gender;
    private ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_activity);
        firstName = findViewById(R.id.firstNameTextView);
        lastName = findViewById(R.id.lastNameTextView);
        gender = findViewById(R.id.genderTextView);
        String personID = getIntent().getStringExtra("PersonID");
        Person_Model person = DataCache.getInstance().getPerson(personID);
        firstName.setText(person.getFirstName());
        lastName.setText(person.getLastName());
        if(person.getGender().equals("m")){
            gender.setText("Male");
        } else {
            gender.setText("Female");
        }

    }
}
