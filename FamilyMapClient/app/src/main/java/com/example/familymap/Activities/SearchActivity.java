package com.example.familymap.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.familymap.Models.DataCache;
import com.example.familymap.Models.Event_Model;
import com.example.familymap.Models.Person_Model;
import com.example.familymap.Models.Settings_Model;
import com.example.familymap.R;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        DataCache.getInstance().fromSettings(true);

    }
    private class SearchAdapter extends RecyclerView.Adapter<SearchHolder>{

        private List<SearchResult> searchResults;

        private SearchAdapter(List<SearchResult> searchResults){
            this.searchResults = searchResults;
        }
        void setSearchResults(List<SearchResult> searchResults) {
            this.searchResults = searchResults;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(SearchActivity.this)
                    .inflate(R.layout.expandable_list_child, parent, false);
            return new SearchHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchHolder holder, int position) {
            SearchResult result = searchResults.get(position);
//            isPerson = result.isPerson;
            id = result.id;

            if(result.isPerson){
                Person_Model p = DataCache.getInstance().getPerson(id);
                String setString = p.getFirstName() + " " + p.getLastName() + "\n" + "Person";
                holder.personID = id;
                holder.searchText.setText(setString);
                if (p.getGender().equals("m")) {
                    holder.imageView.setImageDrawable(getResources().getDrawable(R.drawable.boy));
                }
                else {
                    holder.imageView.setImageDrawable(getResources().getDrawable(R.drawable.girl));
                }
            }
            else {
                holder.activityID = id;
                Event_Model e = DataCache.getInstance().getEvents(id);
                if (e != null) {
                    Person_Model p = DataCache.getInstance().getPerson(e.getPersonID());
                    if (p != null) {
                        String pID = p.getPersonID();
                        if (!Settings_Model.getInstance().isMothersSide() && !Settings_Model.getInstance().isFathersSide()) {
                            if (!DataCache.getInstance().getMothersSideIDs().contains(pID) &&
                                    !DataCache.getInstance().getFatherSideIDs().contains(pID)) {
                                setInfoByGender(holder, p, e);
                            }
                        } else if (!Settings_Model.getInstance().isMothersSide() && !DataCache.getInstance().getMothersSideIDs().contains(pID)) {
                            setInfoByGender(holder, p, e);
                        } else if (!Settings_Model.getInstance().isFathersSide() && !DataCache.getInstance().getFatherSideIDs().contains(pID)) {
                            setInfoByGender(holder, p, e);
                        } else if (Settings_Model.getInstance().isMothersSide() && Settings_Model.getInstance().isFathersSide()) {
                            setInfoByGender(holder, p, e);
                        }
                    }
                }
            }
        }

        @Override
        public int getItemCount() {
            return 0;
        }
        private void setInfoByGender(SearchHolder itemHolder,Person_Model p, Event_Model e) {
            if (Settings_Model.getInstance().isFemaleEvents() && p.getGender().equals("f")) {
                String eventText = e.getEventType().toUpperCase() + ": " + e.getCity() +
                        ", " + e.getCountry() + " (" + e.getYear() + ")" + "\n" +
                        p.getFirstName() + " " + p.getLastName();
                itemHolder.searchText.setText(eventText);
                itemHolder.imageView.setImageDrawable(getResources().getDrawable(R.drawable.pin));
            }
            if (Settings_Model.getInstance().isFemaleEvents() && p.getGender().equals("m")) {
                String eventText = e.getEventType().toUpperCase() + ": " + e.getCity() +
                        ", " + e.getCountry() + " (" + e.getYear() + ")" + "\n" +
                        p.getFirstName() + " " + p.getLastName();
                itemHolder.searchText.setText(eventText);
                itemHolder.imageView.setImageDrawable(getResources().getDrawable(R.drawable.pin));
            }
        }
    }
    private class SearchHolder extends RecyclerView.ViewHolder{
        private TextView searchText;
        private ImageView imageView;
        String activityID;
        String personID;
        public SearchHolder(@NonNull View itemView) {
            super(itemView);
            searchText = itemView.findViewById(R.id.childTextView);
            imageView = itemView.findViewById(R.id.childImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    if (personID != null) {
                        intent = new Intent(SearchActivity.this, PersonActivity.class);
                        intent.putExtra(PersonActivity.PERSON_ACTIVITY_ID, personID);
                    } else {
                        intent = new Intent(SearchActivity.this, EventActivity.class);
                        intent.putExtra(PersonActivity.PERSON_ACTIVITY_ID, activityID);
                    }
                    startActivity(intent);
                }
            });
        }
    }

    public static class SearchResult{
        boolean isPerson;
        String id;

        public SearchResult(boolean isPerson, String id){
            this.isPerson = isPerson;
            this.id = id;
        }
    }
}
