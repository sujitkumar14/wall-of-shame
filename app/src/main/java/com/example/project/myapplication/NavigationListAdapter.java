package com.example.project.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rajkumar on 08-13-2015.
 */
public class NavigationListAdapter extends ArrayAdapter<OboutNavigation> {

    List<OboutNavigation> navigation = new ArrayList<OboutNavigation>();
    LayoutInflater inflater;
    ImageView navigationImages;
    TextView  navigationNames,navigationTotalImages;

    NavigationListAdapter(Activity activity,List<OboutNavigation> navigation)
    {
        super(activity,R.layout.navigationdrawer,navigation);
        this.navigation = navigation;
        inflater = activity.getLayoutInflater();


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v==null)
        {
            v= inflater.inflate(R.layout.navigationsinglelistview,parent,false);
        }

        navigationImages = (ImageView) v.findViewById(R.id.navigationImage);
        navigationNames = (TextView) v.findViewById(R.id.navigationName);
        navigationTotalImages = (TextView) v.findViewById(R.id.totalImages);


        navigationImages.setImageResource(navigation.get(position).getImage());
        navigationNames.setText(navigation.get(position).getName());

        if(navigationNames.getText().equals("All Upload Images"))
        {
            navigationTotalImages.setText(""+ImageStatus.totalImages);
            navigationTotalImages.setVisibility(View.VISIBLE);
        }
        else if(navigationNames.getText().equals("Your Images"))
        {
            navigationTotalImages.setText(""+ImageStatus.userImage);
            navigationTotalImages.setVisibility(View.VISIBLE);
        }




        return v;



    }
}
