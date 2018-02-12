package com.example.project.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.sql.Date;
import java.text.DateFormat;
import java.util.List;
import java.util.ArrayList;
import android.content.res.Resources;
import android.database.Cursor;
import android.util.Log;
import android.content.Intent;


public class ImageDescription extends NavigationDrawer  implements  View.OnClickListener{




    TextView imageDescription,upVote,downVote,title;
    ImageView likesBtn, dislikesBtn,crimeImage;
    EditText apologyText,commentText;

    String imageApologyText;
    Bitmap bm;
    DBHelper helper;
    Button submitApology,commentSubmitButton;
    String description,imageTitle,imageSource;
    int like,dislike;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent  i = new Intent(getApplicationContext(),ImageActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_description);

        //View v = getLayoutInflater().inflate(R.layout.fragment_comment,null);

        helper = new DBHelper(this);

       //////setting top of the activity//////////////////
        View titleFragment  = findViewById(R.id.titleFragment);
        title = (TextView)titleFragment.findViewById(R.id.title);

        ImageView camera = (ImageView)titleFragment.findViewById(R.id.camera);


        imageDescription = (TextView)findViewById(R.id.description);
        likesBtn = (ImageView)findViewById(R.id.likeBtn);
        dislikesBtn = (ImageView)findViewById(R.id.dislikeBtn);
        upVote = (TextView)findViewById(R.id.upVote);
        downVote = (TextView)findViewById(R.id.downVote);
        submitApology = (Button)findViewById(R.id.apologyButton);
        apologyText = (EditText)findViewById(R.id.apology);
        commentSubmitButton = (Button)findViewById(R.id.commentSubmitButton);
        commentText = (EditText)findViewById(R.id.userComment);
        crimeImage = (ImageView)findViewById(R.id.crimeImage);

        submitApology.setOnClickListener(this);
        likesBtn.setOnClickListener(this);
        dislikesBtn.setOnClickListener(this);
        commentSubmitButton.setOnClickListener(this);

        camera.setVisibility(View.VISIBLE);



      //  titleFragmentClass = new TitleFragmentClass(v,this,true,true,"Image Description");
       //titleFragmentClass.setTitleFragment();
      ///setting top of the activiyt/////////////////


    }

    @Override
    protected void onStart() {
        super.onStart();
        try
        {
            Cursor c = getData(ImageStatus.image_id);
            if(c.moveToNext()) {
                int imageTitleIndex = c.getColumnIndex(DBHelper.IMAGE_TITLE);
                int imageDescriptionIndex = c.getColumnIndex(DBHelper.IMAGE_DESCRIPTION);
                int imageLikesIndex = c.getColumnIndex(DBHelper.TOTAL_LIKE);
                int imageDislikeIndex = c.getColumnIndex(DBHelper.TOTAL_DISLIKES);
                int imageSourceIndex = c.getColumnIndex(DBHelper.IMAGE_SOURCE);
                imageSource = c.getString(imageSourceIndex);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = false;
                 bm = BitmapFactory.decodeFile(imageSource);
                crimeImage.setImageBitmap(bm);
                like = c.getInt(imageLikesIndex);
                dislike = c.getInt(imageDislikeIndex);
                description = c.getString(imageDescriptionIndex);
                imageTitle = c.getString(imageTitleIndex);
                title.setText("" + imageTitle);
                imageDescription.setText("" + description);
                upVote.setText("" + like);
                downVote.setText("" + dislike);
            }
            else
            {
                Log.d("sql","crime image");
            }
        }
        catch (SQLException e)
        {
            Log.d("sql",""+e);
        }

        try {
            Cursor apologyCursor = getApologyData(ImageStatus.image_id);

            if (apologyCursor.moveToNext()) {
                int apologyTextIndex = apologyCursor.getColumnIndex(DBHelper.APOLOGY_TEXT);
                imageApologyText = apologyCursor.getString(apologyTextIndex);
                apologyText.setText(imageApologyText);
                apologyText.setClickable(false);
                apologyText.setFocusable(false);
                submitApology.setVisibility(View.INVISIBLE);
                apologyText.setBackgroundColor(Color.parseColor("#43c6db"));
                apologyText.setTextColor(Color.WHITE);


            } else {
                Log.d("mysql", "error occurred");
            }
        }catch (SQLException e)
        {
            Log.d("mysql",""+e);
        }

    }

    Cursor getData(int imageId)
    {
        Cursor c = null;
        SQLiteDatabase db = helper.getWritableDatabase();

        try {
             String[] columns = {DBHelper.IMAGE_DESCRIPTION,DBHelper.IMAGE_TITLE,DBHelper.TOTAL_LIKE,DBHelper.TOTAL_DISLIKES,DBHelper.IMAGE_SOURCE};
            String[] args = {String.valueOf(imageId)};

            c = db.query(DBHelper.CRIME_IMAGE, columns, DBHelper.IMAGE_ID + "=?", args, null, null, null);
        }
        catch (SQLException e)
        {
            Log.d("mysql",""+e);
        }
        return c;
    }

    Cursor getApologyData(int imageId)
    {
        Cursor c = null;

        SQLiteDatabase db=  helper.getWritableDatabase();

        try
        {
            String[] columns = {DBHelper.APOLOGY_TEXT};
            String args[] ={String.valueOf(imageId)};
            c = db.query(DBHelper.APOLOGY,columns,DBHelper.IMAGE_ID+"=?",args,null,null,null);


        }
        catch (SQLException e)
        {
            Log.d("mysql",""+e);
        }

        return c;
    }



    Cursor getLikeData(int userId)
    {
        Cursor c = null;

        SQLiteDatabase db = helper.getWritableDatabase();

        try {
            String[] args = {String.valueOf(userId), String.valueOf(ImageStatus.image_id)};
            String[] columns = {DBHelper.IMAGE_LIKES, DBHelper.IMAGE_DISLIKES};
            c = db.query(DBHelper.TABLE_IMAGE_LIKE, columns, DBHelper.USER_ID + "=? AND " + DBHelper.IMAGE_ID + "=?", args, null, null, null);
        }
        catch (SQLException e)
        {
            Log.d("mysql","failes to excecute like table");
        }

        return c ;

    }

    void updateCrimeImage()
    {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = helper.getWritableDatabase();

        contentValues.put(DBHelper.TOTAL_LIKE,like);
        contentValues.put(DBHelper.TOTAL_DISLIKES, dislike);

        String[] args = {String.valueOf(ImageStatus.image_id)};
        db.update(DBHelper.CRIME_IMAGE,contentValues,DBHelper.IMAGE_ID+"=?",args);
    }

    @Override
    public void onClick(View v) {

        SQLiteDatabase db = helper.getWritableDatabase();

        if(v.getId()==R.id.apologyButton)
        {
            if(UserLoginStatus.status==0)
            {
                Intent i = new Intent(getApplicationContext(),login_activity.class);
                startActivity(i);
            }
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DBHelper.APOLOGY_TEXT, apologyText.getText().toString());
                contentValues.put(DBHelper.IMAGE_ID, ImageStatus.image_id);
                contentValues.put(DBHelper.USER_Name, UserLoginStatus.user_name);
                if ((db.insert(DBHelper.APOLOGY, null, contentValues)) > 0) {
                    Log.d("mysql", "successfully inserted");

                    Intent  i = new Intent(getApplicationContext(),ImageDescription.class);
                    startActivity(i);

                } else {
                    Log.d("mysql", "unsuccessfully inserted");
                }
            }
            catch (SQLException e)
            {
                Log.d("mysql",""+e);
            }
        }

        else
        if(v.getId()==R.id.commentSubmitButton)
        {
            if(UserLoginStatus.status==0)
            {
                Intent i = new Intent(getApplicationContext(),login_activity.class);
                startActivity(i);
            }
            else {
                if(commentText.getText().toString().equals(""))
                {
                    Toast.makeText(this,"Please Enter your Comment!",Toast.LENGTH_LONG).show();
                }
                else {
                    String Date = DateFormat.getDateTimeInstance().format(new java.util.Date());
                    ContentValues contentValues = new ContentValues();
                    contentValues.put   (DBHelper.COMMENTS, commentText.getText().toString());
                    contentValues.put(DBHelper.IMAGE_ID, ImageStatus.image_id);
                    contentValues.put(DBHelper.USER_Name, UserLoginStatus.user_name);
                    contentValues.put(DBHelper.COMMENTS_DATE, Date);

                    if ((db.insert(DBHelper.APOLOGY_COMMENTS, null, contentValues)) > 0) {
                        Log.d("mysql", "Successfully inserted Comments");
                        Intent i = new Intent(getApplicationContext(), CommentPopUp.class);
                        startActivity(i);
                    } else {
                        Log.d("mysql", "Unsuccessfully inserted Comments");
                    }
                }


            }


        }
        else
        if(v.getId()==R.id.likeBtn || v.getId()==R.id.dislikeBtn) {

            try {
                Cursor c = getLikeData(UserLoginStatus.user_id);

                if (c.moveToNext()) {
                    Toast.makeText(getApplicationContext(), "You already liked or Disliked this image", Toast.LENGTH_LONG).show();
                } else {
                    ContentValues contentValues = new ContentValues();
                    if (v.getId() == R.id.likeBtn) {
                        contentValues.put(DBHelper.IMAGE_LIKES, 1);
                        like++;
                    } else if (v.getId() == R.id.dislikeBtn) {
                        contentValues.put(DBHelper.IMAGE_DISLIKES, 0);
                        dislike++;
                    }
                    contentValues.put(DBHelper.IMAGE_ID, ImageStatus.image_id);
                    contentValues.put(DBHelper.USER_ID, UserLoginStatus.user_id);
                    db.insert(DBHelper.TABLE_IMAGE_LIKE, null, contentValues);
                    upVote.setText("" + like);
                    downVote.setText("" + dislike);
                    updateCrimeImage();


                }
            } catch (SQLException e) {
                Log.d("sql", "" + e);
            }
        }


    }




}
