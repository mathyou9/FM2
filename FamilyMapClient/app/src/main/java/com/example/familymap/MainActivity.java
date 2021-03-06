package com.example.familymap;

import android.content.Intent;
import android.os.Bundle;

import com.example.familymap.Activities.SearchActivity;
import com.example.familymap.Fragments.LoginFragment;
import com.example.familymap.Activities.SettingsActivity;
import com.example.familymap.Fragments.MapsFragment;
import com.example.familymap.Models.DataCache;
import com.example.familymap.Models.Person_Model;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Person_Model user = DataCache.getInstance().getUser();
        if(user == null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.mainFrame);

            if(fragment == null){
                fragment = new LoginFragment();
                fragmentManager.beginTransaction().add(R.id.mainFrame, fragment).commit();
            }
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.mainFrame);

            if(fragment == null){
                fragment = new MapsFragment();
                fragmentManager.beginTransaction().add(R.id.mainFrame, fragment).commit();
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem settingsButton = menu.findItem(R.id.settings);
        //add icons later
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            this.startActivity(intent);
            return true;
        } else if(id == R.id.search){
            Intent intent = new Intent(this, SearchActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
