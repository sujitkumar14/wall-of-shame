package com.example.project.myapplication;


import android.app.*;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import android.view.View;

public class Upload extends Activity {


    TextView title,userName;
    TitleFragmentClass titleFragmentClass;
    SharedPreferences preferences;
    SharedPreferences.Editor edit;
    ImageView camera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_activity);
        preferences = getSharedPreferences("user_info",MODE_PRIVATE);
        edit = preferences.edit();

        //setting top of the activity starts//////////////////////////////////
        View v = getLayoutInflater().inflate(R.layout.fragment_top, null);
        View titleFragment  = findViewById(R.id.titleFragment);
        TextView title = (TextView)titleFragment.findViewById(R.id.title);

        ImageView camera = (ImageView)titleFragment.findViewById(R.id.camera);
        title.setText("Upload Image");
        camera.setVisibility(View.INVISIBLE);

        if(preferences.contains("userstatus"))
        {
            UserLoginStatus.status =1;
            UserLoginStatus.user_name = preferences.getString("username","");

        }



        //titleFragmentClass = new TitleFragmentClass(v,this,false,true,"Upload Image");
        //titleFragmentClass.setTitleFragment();

        //setting top of the activity ends////////////////////////////////








    }


}
