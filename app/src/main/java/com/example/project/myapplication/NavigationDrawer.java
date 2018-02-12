package com.example.project.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rajkumar on 08-13-2015.
 */
public class NavigationDrawer extends Activity implements AdapterView.OnItemClickListener{

    String[] navigationBar;
    ListView drawerListView;
    String[] navigationName;
    String[] navigationGuestName;
    DrawerLayout fullLayout;
    ListView navigationList;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;

    List<OboutNavigation> navigation;
    Integer[] navigationImages = {R.drawable.profilenavigation, R.drawable.yourimages, R.drawable.uploadcolor,R.drawable.allimagescolor,R.drawable.logout};
    Integer[] navigationGuestImages = {R.drawable.signinnavigation,R.drawable.registernavigation};
    FrameLayout frameLayout;
    TextView userName;

    DBHelper helper;

    String imageSource,imageDescription,imageDate,imagePlace;

    ListView displayImageList;

    List<OboutImages> images;
    int imageId;


    @Override
    public void setContentView(int layoutResID) {


        View  v = getLayoutInflater().inflate(R.layout.fragment_display_image,null);

        helper = new DBHelper(this);
        displayImageList = (ListView)v.findViewById(R.id.listView);

        fullLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.navigationdrawer, null);
        frameLayout = (FrameLayout) fullLayout.findViewById(R.id.content_frame);

        sharedPreferences = getSharedPreferences(UserLoginStatus.sharedPreferencesUserLoginDetailsFileName, MODE_PRIVATE);
        edit = sharedPreferences.edit();

         getLayoutInflater().inflate(layoutResID, frameLayout, true);
        navigationList = (ListView)fullLayout.findViewById(R.id.left_drawer);
        userName = (TextView) fullLayout.findViewById(R.id.userName);
        super.setContentView(fullLayout);

        navigation = new ArrayList<OboutNavigation>();
        navigationName = getResources().getStringArray(R.array.navigation);
        navigationGuestName = getResources().getStringArray(R.array.guestnavigation);

        if(UserLoginStatus.status ==1)
        {
            userName.setText("Hello " + UserLoginStatus.user_name);
            int i =0;
            for(i=0;i<5;i++)
            {
                navigation.add(new OboutNavigation(navigationName[i],navigationImages[i]));
            }

        }
        else
        {
            userName.setText("Hello Guest");
            int i =0;
            for(i=0;i<2;i++)
            {
                navigation.add(new OboutNavigation(navigationGuestName[i],navigationGuestImages[i]));
            }

        }

       images = new ArrayList<>();
        NavigationListAdapter navigationListAdapter = new NavigationListAdapter(this,navigation);
        navigationList.setAdapter(navigationListAdapter);


        navigationList.setOnItemClickListener(this);
        //Your drawer content...

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.d("mysql","clicked"+UserLoginStatus.status);

      if(UserLoginStatus.status ==1)
      {
          Log.d("mysql","status 1 clicked");
          if(navigationName[position].equals("Your Profile"))
          {
            Log.d("mysql","Your profile clicked");
          }
          else
              if(navigationName[position].equals("Your Images"))
              {

                  ImageStatus.imageSearchFromUserId =1;

                 /* Log.d("mysql","your images clicked");
                  Cursor c = getUserImages(UserLoginStatus.user_id);

                  while(c.moveToNext())
                  {

                      int imageSourceIndex = c.getColumnIndex(DBHelper.IMAGE_SOURCE);
                      int imageDescriptionIndex = c.getColumnIndex(DBHelper.IMAGE_DESCRIPTION);
                      int imagePlaceIndex = c.getColumnIndex(DBHelper.IMAGE_LOCATION);
                      int imageDateIndex = c.getColumnIndex(DBHelper.IMAGE_UPLOAD_DATE);
                      int imageIdIndex = c.getColumnIndex(DBHelper.IMAGE_ID);

                      imageSource = c.getString(imageSourceIndex);
                      imageDescription = c.getString(imageDescriptionIndex);
                      imageId = c.getInt(imageIdIndex);
                      imagePlace = c.getString(imagePlaceIndex);
                      imageDate = c.getString(imageDateIndex);

                      Log.d("mysql",""+imageSource);
                      images.add(new OboutImages(imageDescription,imagePlace,imageDate,imageId,imageSource));
                   }

                  ImageArrayAdapter adapter = new ImageArrayAdapter(this,images);
                  displayImageList.setAdapter(adapter);*/

                  Intent i = new Intent(getApplicationContext(),ImageActivity.class);
                  startActivity(i);


              }
          else
                  if(navigationName[position].equals("Upload Images"))
                  {
                      Log.d("mysql","upload Images clicked");

                      Intent i  = new Intent(getApplicationContext(),UploadingOptionPopUp.class);
                      startActivity(i);
                  }
                  else
                      if(navigationName[position].equals("All Upload Images"))
                      {
                          ImageStatus.imageSearchFromUserId = 0;
                          Intent i = new Intent(this,ImageActivity.class);
                          startActivity(i);
                      }
          else
                  if(navigationName[position].equals("Logout")){
                      Log.d("mysql","upload Images clicked");
                      UserLoginStatus.status =0;
                      edit.remove(UserLoginStatus.sharedPreferencesUserTagName);
                      edit.remove(UserLoginStatus.sharedPreferencesPasswordTagName);
                      edit.commit();
                      edit.apply();
                      Intent i  = new Intent(getApplicationContext(),ImageActivity.class);
                      startActivity(i);
                  }
      }

        else
      {
          Log.d("mysql","status 0 clicked");
          if(navigationGuestName[position].equals("Sign In"))
          {
              Log.d("mysql","sign in clicked");
            Intent  i = new Intent(getApplicationContext(),login_activity.class);
              startActivity(i);
          }
          else
              if(navigationGuestName[position].equals("Register"))
              {
                  Log.d("mysql","register clicked");
                  Intent i  = new Intent(getApplicationContext(),Registration.class);
                  startActivity(i);
              }
      }

        fullLayout.closeDrawers();

    }

    Cursor getUserImages(int user_id) {

        Cursor  c = null;

        SQLiteDatabase db = helper.getWritableDatabase();
    try {

        String[] columns = {DBHelper.IMAGE_SOURCE, DBHelper.IMAGE_ID, DBHelper.USER_ID, DBHelper.IMAGE_UPLOAD_DATE, DBHelper.IMAGE_DESCRIPTION, DBHelper.IMAGE_LOCATION};
         String[] args = {String.valueOf((UserLoginStatus.user_id))};
        c = db.query(DBHelper.CRIME_IMAGE, columns, DBHelper.USER_ID + "=?", args, null, null, null);

    }
    catch (SQLException e)
    {
        Log.d("mysql", "" + e);
    }
        return c;
    }
}
