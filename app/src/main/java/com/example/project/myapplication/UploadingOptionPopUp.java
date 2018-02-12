package com.example.project.myapplication;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.View;
import android.content.Intent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Timestamp;
import java.sql.Time;
import java.util.Date;

public class UploadingOptionPopUp extends Activity implements  View.OnClickListener{

    static int IMAGE_CODE = 1;
    Uri imageUri;
    SharedPreferences preferences;
    SharedPreferences.Editor edit;
    Date date  = new Date();
    int time  = (int)System.currentTimeMillis();

    File file = new File(login_activity.path,time+".jpeg");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploading_option);

        preferences = getSharedPreferences("user_info",MODE_PRIVATE);
        edit = preferences.edit();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)( width *0.8), (int)(height*0.6));

        View selectCamera = findViewById(R.id.selectCamera);
        View selectUplaod = findViewById(R.id.selectUpload);
        selectCamera.setOnClickListener(this);
        selectUplaod.setOnClickListener(this);



    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.selectCamera)
        {
            imageUri = Uri.fromFile(file);
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(i, 1);

        }
        else
            if(v.getId()==R.id.selectUpload)
            {
                edit.putString("username",UserLoginStatus.user_name);
                edit.putString("userstatus","1");
                edit.commit();
                edit.apply();
                Intent i  = new Intent(getApplicationContext(),Upload.class);
                startActivity(i);
            }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode == RESULT_OK)
        {
            Bitmap bm ;
            try {
                bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
                Matrix rotateMatrix = new Matrix();
                rotateMatrix.postRotate(90);
                Bitmap rotatedBitmap = Bitmap.createBitmap(bm,0,0,bm.getWidth(),bm.getHeight(),rotateMatrix,false);
                ImageStatus.image_path = file.getAbsolutePath();
                ImageStatus.image_path_status=1;
                Intent i = new Intent(getApplicationContext(),Upload.class);
                Log.d("mysql",""+ImageStatus.image_path);
                startActivity(i);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    void saveImage(Bitmap bm)
    {

        try
        {

            FileOutputStream fos = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG,100,fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
