package com.example.project.myapplication;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageActivity extends NavigationDrawer {



    TitleFragmentClass titleFragmentClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ImageStatus.image_path_status=0;

        View titleFragment  = findViewById(R.id.titleFragment);
        TextView title = (TextView)titleFragment.findViewById(R.id.title);
        ImageView camera = (ImageView)titleFragment.findViewById(R.id.camera);
        title.setText("Wall of Shame");
        camera.setVisibility(View.VISIBLE);



        //titleFragmentClass = new TitleFragmentClass(v,this,true,true,"Crime Images");
       //titleFragmentClass.setTitleFragment();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(UserLoginStatus.status==1) {
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            startActivity(i);
        }
        else
        {
            Intent i = new Intent(getApplicationContext(),login_activity.class);
            startActivity(i);
        }
    }
}
