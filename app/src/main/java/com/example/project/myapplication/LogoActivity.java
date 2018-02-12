package com.example.project.myapplication;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.util.Log;

public class LogoActivity extends Activity {

    Thread time;
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        sharedPreferences = getSharedPreferences(UserLoginStatus.sharedPreferencesUserLoginDetailsFileName,MODE_PRIVATE);
        edit = sharedPreferences.edit();


    }

    @Override
    protected void onStart() {
        super.onStart();
        time = new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(1000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }finally
                {
                    if(sharedPreferences.contains(UserLoginStatus.sharedPreferencesUserTagName))
                    {
                        UserLoginStatus.status = 1;
                        UserLoginStatus.user_name = sharedPreferences.getString(UserLoginStatus.sharedPreferencesUserTagName,"");
                        UserLoginStatus.user_id = sharedPreferences.getInt(UserLoginStatus.sharedPreferencesUserIdTagName, 0);
                        //Log.d("mysql","ImageActivity is called");
                        Intent i = new Intent(getApplicationContext(),ImageActivity.class);
                        startActivity(i);
                    }
                    else {
                        //Log.d("mysql","lgoinActivity called");
                        Intent i = new Intent(getApplicationContext(), login_activity.class);
                        startActivity(i);
                    }
                }
            }
        };
        time.start();
    }
}
