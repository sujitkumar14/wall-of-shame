package com.example.project.myapplication;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Rajkumar on 07-22-2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="WALL_OF_SHAME";
    public static final int VERSION=33;
    public static final String USER_INFO = "USER_INFO";
    public static final String USER_ID = "user_id";
    public static final String USER_Name="user_name";
    public static final String PASSWORD = "password";
    public static final String MOBILE_NO= "mobile_no";
    public static final String CRIME_IMAGE = "CRIME_IMAGE";
    public static final String IMAGE_ID = "IMAGE_ID";
    public static final String IMAGE_LOCATION = "LOCATION";
    public static final String IMAGE_UPLOAD_DATE = "DATE";
    public static final String IMAGE_DESCRIPTION = "DESCRIPTION";
    public static final String IMAGE_SOURCE = "SOURCE";
    public static final String IMAGE_TITLE = "IMAGE_TITLE";
    public static final String APOLOGY_TEXT = "APOLOGY_TEXT";
    public static final String APOLOGY_DATE = "APOLOGY_DATE";
    public static final String APOLOGY = "APOLOGY";
    public static final String IMAGE_LIKES = "IMAGE_LIKES";
    public static final String IMAGE_DISLIKES = "IMAGE_DISLIKES";
    public static final  String APOLOGY_COMMENTS = "APOLOGY_COMMENTS";
    public static final String COMMENTS = "COMMENTS";
    public static final String COMMENTS_DATE = "COMMENTS_DATE";
    public static final String TABLE_IMAGE_LIKE ="IMAGE_LIKE";
    public static final String TOTAL_LIKE ="TOTAL_LIKES";
    public static final String TOTAL_DISLIKES = "TOTAL_DISLIKES";
    public static final String GENDER = "GENDER";
    public Context context;

    //TABLE FOR USER_INFORMATION
    public static final String CREATE_TABLE_USER_INFO = "create table "+ USER_INFO +"" +
            "("+USER_ID+" INTEGER   PRIMARY KEY AUTOINCREMENT ," +
            ""+USER_Name+" VARCHAR(12) UNIQUE,"+GENDER +" VARCHAR(6),"+PASSWORD+" VARCHAR(6)," +
            ""+MOBILE_NO+" INT(10) UNIQUE);";

    //TABLE FOR CRIME IMAGE
    public static final String CREATE_TABLE_CRIME_IMAGE = "CREATE TABLE "+CRIME_IMAGE+"("+IMAGE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT" +
            ","+USER_ID+" INTEGER ,"+IMAGE_LOCATION+" VARCHAR(10),"+IMAGE_TITLE+" VARCHAR(10)," +
            ""+IMAGE_UPLOAD_DATE+" DATE,"+IMAGE_DESCRIPTION+" VARCHAR(255)," +
            ""+IMAGE_SOURCE+" VARCHAR(200),"+TOTAL_LIKE+" INTEGER , "+TOTAL_DISLIKES+" INTEGER, FOREIGN KEY("+USER_ID+") REFERENCES "+USER_INFO+"("+USER_ID+"));";

    public static final String CREATE_TABLE_IMAGES_LIKES_DISLIKES ="CREATE TABLE "+TABLE_IMAGE_LIKE+"("+IMAGE_ID+" INTEGER,"+USER_ID+" INTEGER,"+IMAGE_LIKES+" INTEGER," +
            ""+IMAGE_DISLIKES+" INTEGER,FOREIGN KEY("+USER_ID+") REFERENCES "+USER_INFO+"("+USER_ID+")," +
            "FOREIGN KEY("+IMAGE_ID+") REFERENCES "+CRIME_IMAGE+"("+IMAGE_ID+"));";

    public static final String CREATE_TABLE_APOLOGY = "CREATE TABLE "+APOLOGY+"("+IMAGE_ID+" INTEGER," +
            ""+USER_ID+" INTEGER,"+USER_Name+" VARCHAR(32),"+APOLOGY_TEXT+" VARCHAR(32),"+APOLOGY_DATE+" date,FOREIGN KEY("+IMAGE_ID+") REFERENCES "+CRIME_IMAGE+"("+IMAGE_ID+"),FOREIGN KEY("+USER_ID+") REFERENCES "+USER_INFO+"("+USER_ID+"));";

    public static final String CREATE_TABLE_COMMENTS = "CREATE TABLE "+ APOLOGY_COMMENTS +"("+IMAGE_ID+" INTEGER,"+USER_Name+" VARCHAR(32),"+COMMENTS+" VARCHAR(255),"+COMMENTS_DATE+" DATE,FOREIGN KEY("+IMAGE_ID+") REFERENCES "+CRIME_IMAGE+"("+IMAGE_ID+"));";

    public static final String DROP_TABLE_USER_INFO = "Drop table if Exists "+ USER_INFO +";";
    public static final String DROP_TABLE_CRIME_IMAGE = "Drop table if Exists "+ CRIME_IMAGE +";";
    public static final String DROP_TABLE_APOLOGY ="Drop table if Exists "+APOLOGY+";";
    public static final String DROP_TABLE_COMMENTS = "Drop table if Exists "+APOLOGY_COMMENTS+";";
    public static final String DROP_TABLE_IMAGES_LIKES_DISLIKES  = "Drop table if Exists "+TABLE_IMAGE_LIKE+";";

    public DBHelper(Context context) {

        super(context,DATABASE_NAME,null,VERSION);
        //Toast.makeText(context,"constuctor called",Toast.LENGTH_LONG).show();

        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_USER_INFO);
            db.execSQL(CREATE_TABLE_CRIME_IMAGE);
            db.execSQL(CREATE_TABLE_APOLOGY);
            db.execSQL(CREATE_TABLE_COMMENTS);
            db.execSQL(CREATE_TABLE_IMAGES_LIKES_DISLIKES);
          //  Toast.makeText(context,"oncreate called",Toast.LENGTH_LONG).show();

        }
        catch (SQLException e) {
            Log.d("sql",""+e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE_USER_INFO);
            db.execSQL(DROP_TABLE_CRIME_IMAGE);
           db.execSQL(DROP_TABLE_APOLOGY);
            db.execSQL(DROP_TABLE_COMMENTS);
            db.execSQL(DROP_TABLE_IMAGES_LIKES_DISLIKES);
            onCreate(db);
        //    Log.d("sql","onupgrade called");
        }
        catch (SQLException e)
        {
         Log.d("sql",""+e);
        }


    }
}
