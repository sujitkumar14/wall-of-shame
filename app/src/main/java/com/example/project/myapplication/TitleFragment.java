package com.example.project.myapplication;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.content.*;

import javax.xml.transform.Result;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.sql.Time;
import android.net.Uri;

public class TitleFragment extends Fragment implements View.OnClickListener{


    TextView title,logout;
    ImageView camera;
    SharedPreferences preferences;
    SharedPreferences.Editor edit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v = inflater.inflate(R.layout.fragment_top, container, false);
        preferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        edit = preferences.edit();
        title = (TextView)v.findViewById(R.id.title);
        logout = (TextView)v.findViewById(R.id.logout);
        camera = (ImageView)v.findViewById(R.id.camera);
        if(UserLoginStatus.status==1)
        {
            logout.setVisibility(View.VISIBLE);
        }
        else
        {
            logout.setVisibility(View.INVISIBLE);
        }
        logout.setOnClickListener(this);
        camera.setVisibility(View.INVISIBLE);
        camera.setOnClickListener(this);

        return v;
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.camera)
        {
            if(UserLoginStatus.status==1)
            {
                Intent i = new Intent(getActivity().getApplicationContext(),UploadingOptionPopUp.class);
                startActivity(i);
            }

            else
            {
                Intent i = new Intent(getActivity().getApplicationContext(),login_activity.class);
                startActivity(i);
            }

        }
      /*  else
            if(v.getId()==R.id.logout)
            {
                UserLoginStatus.status =0;
                edit.remove("user_name");
                edit.remove("password");
                edit.commit();
                edit.apply();
                Intent i = new Intent(getActivity().getApplicationContext(),login_activity.class);
                startActivity(i);
            }*/

    }


}
