package com.example.project.myapplication;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.*;
import android.view.View;
/**
 * Created by Rajkumar on 07-26-2015.
 */

public class TitleFragmentClass {

    TextView topUserName,title,logout;
    ImageView camera;
    Activity activity;
    Boolean cameraVisibility,userNameVisiblity;
    String header;
    View v;


    TitleFragmentClass(View v,Activity activity, boolean cameraVisibility, boolean userNameVisibility, String header)  //constructor calls and intilize data members
    {
        this.activity = activity;
        this.cameraVisibility  = cameraVisibility;
        this.userNameVisiblity = userNameVisibility;
        this.header = header;
        this.v = v;
        new TitleFragmentClass();
    }

    TitleFragmentClass()//constructor initilize elements
    {


        title = (TextView) v.findViewById(R.id.title);
        camera = (ImageView) v.findViewById(R.id.camera);




    }

    void setTitleFragment()
    {
        //setting title
        title.setText(header);

        //setting camera Visibility
        if(cameraVisibility==true)
        {
            camera.setVisibility(View.VISIBLE);
        }
        else
        {
            camera.setVisibility(View.INVISIBLE);
        }
//setting user Name
        if(UserLoginStatus.status==1)
        {
            topUserName.setText("Hello " + UserLoginStatus.user_name);
        }
        else
        {
            topUserName.setText("Hello Guest");
        }
        //setting userName visibility
        if(userNameVisiblity==true) {
            topUserName.setVisibility(View.VISIBLE);
        }
        else
        {
            topUserName.setVisibility(View.INVISIBLE);
        }





    }


}
