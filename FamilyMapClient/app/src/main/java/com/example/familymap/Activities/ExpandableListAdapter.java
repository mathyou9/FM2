package com.example.familymap.Activities;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.familymap.Models.Person_Model;
import com.example.familymap.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> data;
    private HashMap<String, List<String>> dataHash;

    ExpandableListAdapter(Context context, List<String> data, HashMap<String, List<String>> dataHash){
        this.context = context;
        this.data = data;
        this.dataHash = dataHash;
    }

    @Override
    public int getGroupCount() {
        return this.data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.dataHash.get(this.data.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.dataHash.get(this.data.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expandable_list_group_header, null);
        }
        TextView header = convertView.findViewById(R.id.headerTextView);
        header.setTypeface(null, Typeface.BOLD);
        header.setText(headerTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expandable_list_child, null);
        }
        String top = getChild(groupPosition, childPosition).toString();

        ImageView imageView = convertView.findViewById(R.id.childImageView);
        if(getChild(groupPosition, childPosition).toString().contains("Spouse") || getChild(groupPosition, childPosition).toString().contains("Child")){
            imageView.setImageDrawable(imageView.getResources().getDrawable(R.drawable.androidwhite));
        } else {
            imageView.setImageDrawable(imageView.getResources().getDrawable(R.drawable.pin));
        }
        TextView topText = convertView.findViewById(R.id.childTextView);
        topText.setText(top);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
