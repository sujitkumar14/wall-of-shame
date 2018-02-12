package com.example.project.myapplication;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
public class upload_image_Fragment extends Fragment implements View.OnClickListener,View.OnTouchListener {


    SharedPreferences preferences;
    SharedPreferences.Editor edit;
    EditText title,date,location,description;
    String imageTitle,imageLocation,imageDescription,stringDate,imageSource,selectedImagePath;
    Button submit;

    Date imageDate = new Date();
    String Date= DateFormat.getDateTimeInstance().format(new Date());

    DBHelper  helper;

    ImageView upload;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_upload_image_activity, container, false);

        preferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        edit = preferences.edit();


        title = (EditText)v.findViewById(R.id.title);
        date  = (EditText)v.findViewById(R.id.date);
        location = (EditText)v.findViewById(R.id.location);
        description = (EditText)v.findViewById(R.id.description);
        upload = (ImageView)v.findViewById(R.id.upload);
        submit = (Button)v.findViewById(R.id.submit);
        upload.setOnClickListener(this);
        submit.setOnClickListener(this);

        date.setClickable(false);
        date.setFocusable(false);

        date.setText("" + Date);

        title.setOnTouchListener(this);
        location.setOnTouchListener(this);
        description.setOnTouchListener(this);
        helper = new DBHelper(getActivity());

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(ImageStatus.image_path_status==1)
        {

                Bitmap bm = BitmapFactory.decodeFile(ImageStatus.image_path);
                upload.setImageBitmap(bm);
        }
        else
        {
            upload.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        imageTitle =title.getText().toString();
        imageLocation=location.getText().toString();
        imageDescription= description.getText().toString();;


        stringDate = date.getText().toString();


        if(v.getId()==R.id.submit)
        {
            if(ImageStatus.image_path_status==0 || imageTitle.equals("") || imageLocation.equals("") || stringDate.equals("") ||imageDescription.equals("") )
            {
                if(ImageStatus.image_path_status==0)
                {
                    Toast.makeText(getActivity().getApplicationContext(),"Please Upload Your Image",Toast.LENGTH_LONG).show();
                }

                if(imageTitle.equals("")) {
                    title.setHintTextColor(Color.RED);
                }
                if(imageLocation.equals(""))
                {
                    location.setHintTextColor(Color.RED);
                }
                if(stringDate.equals(""))
                {
                    date.setHintTextColor(Color.RED);
                }
                if(imageDescription.equals(""))
                {
                    description.setHintTextColor(Color.RED);
                }
            }
            else
            {
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();


                try
                {
                    contentValues.put(helper.IMAGE_TITLE,imageTitle);
                    contentValues.put(helper.IMAGE_LOCATION,imageLocation);
                    contentValues.put(helper.IMAGE_UPLOAD_DATE,stringDate);
                    contentValues.put(helper.IMAGE_DESCRIPTION, imageDescription);
                    contentValues.put(helper.USER_ID,UserLoginStatus.user_id);
                    contentValues.put(helper.IMAGE_SOURCE,ImageStatus.image_path);
                    contentValues.put(helper.TOTAL_LIKE,0);
                    contentValues.put(helper.TOTAL_DISLIKES, 0);
                   if( (db.insert(DBHelper.CRIME_IMAGE,null,contentValues))>0)
                   {
                       Log.d("mysql","successfully inserted image description");

                       notification(imageTitle, imageDescription);

                       Intent i  = new Intent(getActivity().getApplicationContext(),ImageActivity.class);
                       startActivity(i);
                   }
                    else
                   {
                       Toast.makeText(getActivity().getApplicationContext(),"Something happens please try again!",Toast.LENGTH_LONG).show();;
                   }




                }
                catch (SQLException e)
                {
                    Log.d("mysql", "" + e);
                }
            }
        }

        else
            if(v.getId()==R.id.upload)
            {
                edit.putString("userstatus","1");
                edit.putString("username",UserLoginStatus.user_name);
                edit.apply();
                edit.commit();
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i,"Select Pictures"),10);

            }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==10 )
        {
            try {
                Uri selectImageUri = data.getData();
                selectedImagePath = getPath(selectImageUri);
                ImageStatus.image_path = selectedImagePath;
                ImageStatus.image_path_status = 1;
                Bitmap bm = BitmapFactory.decodeFile(selectedImagePath);
                upload.setImageBitmap(bm);
                Log.d("mysql", "inside on activity result" + selectedImagePath + selectImageUri.getPath());
            }
            catch (Exception e)
            {
                Log.d("image","No image Selected");
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(uri, projection, null, null, null);

            cursor.moveToFirst();
            int column_index = cursor.getColumnIndex(projection[0]);
            return cursor.getString(column_index);



    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if(v.getId()==R.id.title)
        {
            title.setHintTextColor(Color.GRAY);
        }
        else
        if(v.getId()==R.id.location)
        {
            location.setHintTextColor(Color.GRAY);
        }
        else
        if(v.getId()==R.id.description)
        {
            description.setHintTextColor(Color.GRAY);
        }
        return false;
    }


    void notification(String imageTitle,String imageDescription )
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity().getApplicationContext());
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap bm = BitmapFactory.decodeFile(ImageStatus.image_path);

        builder.setSmallIcon(R.drawable.wallofshamelogo);
        builder.setSound(soundUri);
        builder.setLights(2, 100, 200);
        builder.setLargeIcon(bm);
        builder.setContentTitle(imageTitle);
        builder.setContentText(imageDescription);
        builder.setAutoCancel(true);

        Intent i = new Intent(getActivity().getApplicationContext(),ImageActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity().getApplicationContext());
        stackBuilder.addParentStack(ImageActivity.class);
        stackBuilder.addNextIntent(i);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());


    }


}
