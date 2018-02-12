package com.example.project.myapplication;

import android.app.Fragment;
import android.content.res.Resources;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import android.database.Cursor;

public class DisplayImage extends Fragment {


    ListView myList;
    List<OboutImages> chats;
    TextView date;
    String des,imagePlace,imageDate,imageSource;
    DBHelper helper;
    int imageId,userId;
    // LayoutInflater inflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_display_image, container, false);
        Log.d("mysql","fragment"+ImageStatus.imageSearchFromUserId);
        Resources res = getResources();
        if(ImageStatus.imageSearchFromUserId!=1) {
            ImageStatus.totalImages = 0;

            ImageStatus.userImage = 0;
        }

        // int image[] = {R.mipmap.shame,R.mipmap.ic_launcher,R.mipmap.shame,R.mipmap.ic_launcher,R.mipmap.shame,R.mipmap.ic_launcher,R.mipmap.shame,R.mipmap.ic_launcher,R.mipmap.shame,R.mipmap.ic_launcher};
       // int image[] = {R.drawable.city1,R.drawable.city2,R.drawable.city3,R.drawable.city4,R.drawable.city5,R.drawable.city5,R.drawable.city6,R.drawable.city1,R.drawable.city2,R.drawable.city3};


        helper = new DBHelper(getActivity().getApplicationContext());
        Log.d("mysql","helper Object");


        Log.d("mysql","oncreate view is called");
        myList = (ListView)v.findViewById(R.id.listView);
        chats = new ArrayList<>();

        try
        {
            Cursor c= checkData();


              while(c.moveToNext())
              {

                    Log.d("mysql", "inside loop");
                    int descriptionIndex = c.getColumnIndex(DBHelper.IMAGE_DESCRIPTION);
                    int placeIndex = c.getColumnIndex(DBHelper.IMAGE_LOCATION);
                    int dateIndex = c.getColumnIndex(DBHelper.IMAGE_UPLOAD_DATE);
                    int userIdIndex = c.getColumnIndex(DBHelper.USER_ID);
                    int imageIdIndex = c.getColumnIndex(DBHelper.IMAGE_ID);
                    int imageSourceIndex = c.getColumnIndex(DBHelper.IMAGE_SOURCE);
                    if (ImageStatus.imageSearchFromUserId != 1)
                        ImageStatus.totalImages++;

                    imageSource = c.getString(imageSourceIndex);
                    des = c.getString(descriptionIndex);
                    imagePlace = c.getString(placeIndex);
                    imageDate = c.getString(dateIndex);
                    imageId = c.getInt(imageIdIndex);
                    userId = c.getInt(userIdIndex);
                    if (userId == ImageStatus.user_id) {
                        ImageStatus.userImage++;
                    }

                    chats.add(new OboutImages(des, imagePlace, imageDate, imageId, imageSource));

                }

        }
        catch (SQLException e)
        {
            Log.d("mysql",""+e);
        }

        ImageArrayAdapter arrayAdapter = new ImageArrayAdapter(getActivity(),chats);
        myList.setAdapter(arrayAdapter);
        ImageStatus.imageSearchFromUserId =0;
        return v;
    }





    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("mysql","on activity called");
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Log.d("mytag", "list was clicked");
                ImageStatus.image_id = chats.get(position).getImageId();
                Intent i = new Intent(getActivity().getApplicationContext(), ImageDescription.class);
                getActivity().startActivity(i);
                //Toast t = Toast.makeText(getActivity().getApplicationContext(), "Item clicked", Toast.LENGTH_LONG);
                //t.show();
            }
        });
    }

    Cursor checkData()
    {
        Cursor c = null;
        SQLiteDatabase db = helper.getWritableDatabase();
        Log.d("mysql","inside images description");
        //select * from crime Image;
        try {
            String[] columns = {helper.IMAGE_DESCRIPTION, helper.IMAGE_LOCATION,helper.USER_ID, helper.IMAGE_UPLOAD_DATE,helper.IMAGE_ID,helper.IMAGE_SOURCE};
            if(ImageStatus.imageSearchFromUserId==0)
            c = db.query(helper.CRIME_IMAGE, columns, null, null, null, null, null);
            else {
                Log.d("mysql","inside else "+UserLoginStatus.user_id);
                String[] args = {String.valueOf(UserLoginStatus.user_id)};
                c = db.query(helper.CRIME_IMAGE, columns, DBHelper.USER_ID +"=?", args, null, null, null);
            }
            Log.d("mysql","excecuting image query");
        }
        catch (SQLException e)
        {
            Log.d("sql",""+e);
        }
        return c;
    }

}
