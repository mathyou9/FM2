package com.example.familymap.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.familymap.MainActivity;
import com.example.familymap.Models.DataCache;
import com.example.familymap.Models.Settings_Model;
import com.example.familymap.R;

public class SettingsActivity extends AppCompatActivity {
    private Switch lifeStoryLines;
    private Switch familyTreeLines;
    private Switch spouseLines;
    private Switch fathersSide;
    private Switch mothersSide;
    private Switch maleEvents;
    private Switch femaleEvents;
    private RelativeLayout logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager().beginTransaction().commit();

        lifeStoryLines = findViewById(R.id.lifeStorySwitch);
        familyTreeLines = findViewById(R.id.familyTreeSwitch);
        spouseLines = findViewById(R.id.spouseLinesSwitch);
        fathersSide = findViewById(R.id.fathersSideSwitch);
        mothersSide = findViewById(R.id.mothersSideSwitch);
        maleEvents = findViewById(R.id.maleEventsSwitch);
        femaleEvents = findViewById(R.id.femaleEventsSwitch);
        logoutButton = findViewById(R.id.logoutRelativeLayout);

        lifeStoryLines.setChecked(Settings_Model.getInstance().isLifeStoryLines());
        lifeStoryLines.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings_Model.getInstance().setLifeStoryLines(isChecked);
            }
        });
        familyTreeLines.setChecked(Settings_Model.getInstance().isFamilyTreeLines());
        familyTreeLines.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings_Model.getInstance().setFamilyTreeLines(isChecked);
            }
        });
        spouseLines.setChecked(Settings_Model.getInstance().isSpouseLines());
        spouseLines.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings_Model.getInstance().setSpouseLines(isChecked);
            }
        });
        fathersSide.setChecked(Settings_Model.getInstance().isFathersSide());
        fathersSide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings_Model.getInstance().setFathersSide(isChecked);
            }
        });
        mothersSide.setChecked(Settings_Model.getInstance().isMothersSide());
        mothersSide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings_Model.getInstance().setMothersSide(isChecked);
            }
        });
        maleEvents.setChecked(Settings_Model.getInstance().isMaleEvents());
        maleEvents.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings_Model.getInstance().setMaleEvents(isChecked);
            }
        });
        femaleEvents.setChecked(Settings_Model.getInstance().isFemaleEvents());
        femaleEvents.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings_Model.getInstance().setFemaleEvents(isChecked);
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                DataCache.getInstance().logout();
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //finish();
                Toast.makeText(getApplicationContext(), "Logging you out.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
