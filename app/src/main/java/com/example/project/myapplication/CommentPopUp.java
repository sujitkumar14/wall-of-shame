package com.example.project.myapplication;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class CommentPopUp extends Activity {

    ListView commentList;
    List<OboutComments> oboutCommentList ;
    CommentArrayAdapter commentAdapter;
    String comment,commentDate,commentUserName;

    DBHelper helper ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_pop_up);

        //pop up window //
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.6));
        //pop up window command///////////////////


        helper = new DBHelper(this); //defing a reference variable of DBhelper/



        commentList = (ListView)findViewById(R.id.commentList);//decalaring commentList
        oboutCommentList = new ArrayList<>(); //declaring a array list for abouit
        Cursor commentCursor = getCommentData(ImageStatus.image_id);


        while(commentCursor.moveToNext())
        {
            int commentIndex = commentCursor.getColumnIndex(DBHelper.COMMENTS);
            int dateIndex = commentCursor.getColumnIndex(DBHelper.COMMENTS_DATE);
            int userNameIndex = commentCursor.getColumnIndex(DBHelper.USER_Name);
            comment  = commentCursor.getString(commentIndex);
            commentDate = commentCursor.getString(dateIndex);
            commentUserName = commentCursor.getString(userNameIndex);

            oboutCommentList.add(new OboutComments(comment,commentUserName,commentDate));

        }

        commentAdapter  = new CommentArrayAdapter(this,oboutCommentList);
        commentList.setAdapter(commentAdapter);

    }

    Cursor getCommentData(int imageId)
    {
        Cursor c = null;

        SQLiteDatabase db = helper.getWritableDatabase();

        try
        {
            String[] colums = {DBHelper.COMMENTS,DBHelper.USER_Name,DBHelper.COMMENTS_DATE};
            String[] args = {String.valueOf(imageId)};

            c =  db.query(DBHelper.APOLOGY_COMMENTS,colums,DBHelper.IMAGE_ID+"=?",args,null,null,null);

        }
        catch (SQLException e)
        {
            Log.d("sql", "" + e);
        }

        return c;
    }


}
