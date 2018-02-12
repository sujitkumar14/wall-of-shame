package com.example.project.myapplication;



import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.text.method.PasswordTransformationMethod;
import android.widget.*;
import android.view.View;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.content.SharedPreferences;
import java.io.File;

public class login_activity extends Activity implements View.OnClickListener,View.OnFocusChangeListener {

    DBHelper helper;
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor edit;
    TitleFragmentClass titleFragmentClass;

    final static String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/Wall of Shame";
    //set up a path for file;


    Button login;
    TextView  signUp,skip,error;

    EditText userName,pass;
    ImageView passVisible, showGenderImage;
    boolean isShowPassword = false;
    boolean isPassWordFieldVisble = false;
    LinearLayout passwordField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        helper = new DBHelper(login_activity.this);
// override elements of Top Fragment starts//
        View v = findViewById(R.id.titleFragment);
        TextView title = (TextView)v.findViewById(R.id.title);

        ImageView camera = (ImageView)v.findViewById(R.id.camera);
        title.setText("SIGN IN");
        camera.setVisibility(View.INVISIBLE);


      //  titleFragmentClass = new TitleFragmentClass(v,this,false,false,"SIGN IN");
       //titleFragmentClass.setTitleFragment();
//overriding of elements of Top Fragment ends//


        //Declaration of login Activity elements starts//
        userName = (EditText)findViewById(R.id.userName);
        pass = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);
        error = (TextView)findViewById(R.id.error);
        signUp = (TextView)findViewById(R.id.signup);
        skip = (TextView)findViewById(R.id.skip);
        passVisible = (ImageView) findViewById(R.id.passwordVisible);
        showGenderImage = (ImageView)findViewById(R.id.showGender);
        passwordField = (LinearLayout) findViewById(R.id.passwordField);
        //Declaration of login Activity elements ends//

        //Listener on login Activity Elements//
        userName.setOnFocusChangeListener(this);
        pass.setOnFocusChangeListener(this);
        login.setOnClickListener(this);
        signUp.setOnClickListener(this);
        skip.setOnClickListener(this);
        passVisible.setOnClickListener(this);
        //Listener of Login Activity Elements//


        sharedPreferences = getSharedPreferences(UserLoginStatus.sharedPreferencesUserLoginDetailsFileName,MODE_PRIVATE);
        edit = sharedPreferences.edit();


        boolean result=false;
        File file = new File(path);
        if(!file.exists())
        {
            result =  file.mkdirs();
        }
        else {
           // Log.d("mysql", "can Write = " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).canWrite());
            //Log.d("mysql", "" + result);
        }
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.signup) {
            Intent i = new Intent(getApplicationContext(), Registration.class);
            startActivity(i);
        } else if (v.getId() == R.id.skip) {
            Intent i = new Intent(getApplicationContext(), ImageActivity.class);
            startActivity(i);
        }
        else if(v.getId()==R.id.passwordVisible)
        {
            if(isShowPassword==false) {
                pass.setTransformationMethod(new PasswordTransformationMethod());//make password visible
                passVisible.setImageResource(R.drawable.eyelogin);
                isShowPassword = true;
            }
            else
            {
                pass.setTransformationMethod(null);
                passVisible.setImageResource(R.drawable.closedeyelogin);//make password hide
                isShowPassword= false;
            }
        }
        else if (v.getId() == R.id.login)
        {
            String enteredName = userName.getText().toString();
           // Log.d("sql","you entered "+enteredName);
            try {
                Cursor c = checkData(enteredName); //Taking data from Database using user name entered by User//
                //checkData is a function  that checks whether user name exists or not
                //Toast.makeText(getApplicationContext(),""+c,Toast.LENGTH_LONG).show();

                if (c.moveToNext()) {

                    //Taking data from Database tables after Select query performs//

                    int userNameIndex = c.getColumnIndex(DBHelper.USER_Name);
                    int  passwordIndex = c.getColumnIndex(DBHelper.PASSWORD);
                    int userIdIndex = c.getColumnIndex(DBHelper.USER_ID);
                    int userGenderIndex = c.getColumnIndex(DBHelper.GENDER);
                    String dbGender = c.getString(userGenderIndex);
                    String dbName = c.getString(userNameIndex);
                    String dbPass = c.getString(passwordIndex);
                    int dbUserId = c.getInt(userIdIndex);
                    //data retrivel ends here//
                    if((dbGender.equals("Male") || dbGender.equals("Female")) && isPassWordFieldVisble==false)
                    {
                        isPassWordFieldVisble = true;
                        passwordField.setVisibility(View.VISIBLE);
                        login.setText("LOGIN");
                        if(dbGender.equals("Male"))
                        {
                            showGenderImage.setImageResource(R.drawable.profileboy);
                        }
                        else
                        {
                            showGenderImage.setImageResource(R.drawable.profilegirl);
                        }

                    }
                    else {
                        //Comparing entered user Name,password with Database UserName passWord//
                        if (dbName.equals(userName.getText().toString()) && dbPass.equals(pass.getText().toString())) {

                            UserLoginStatus.status = 1;
                            UserLoginStatus.user_name = dbName;
                            UserLoginStatus.user_id = dbUserId;

                            edit.putString(UserLoginStatus.sharedPreferencesUserTagName, dbName);
                            edit.putString(UserLoginStatus.sharedPreferencesPasswordTagName, dbPass);
                            edit.putInt(UserLoginStatus.sharedPreferencesUserIdTagName, dbUserId);
                            edit.commit();
                            edit.apply();


                            Toast.makeText(getApplicationContext(), "you are logged IN", Toast.LENGTH_LONG).show();

                            //calling imageActivity clASS
                            Intent i = new Intent(getApplicationContext(), ImageActivity.class);
                            startActivity(i);

                        } else {
                            error.setText("Wrong User Name or Password");
                            //Toast.makeText(getApplicationContext(), "wrong userName or password", Toast.LENGTH_LONG).show();

                        }
                    }
                    //comparision ends here//

                } else {
                    error.setText("Wrong User Name or Password");//for wrongImage user Name password
                }
            }
            catch (SQLException e)
            {
                Log.d("sql",""+e);
            }
        }

    }


   Cursor checkData(String name)
    {
        Cursor c =null;
      //  Toast.makeText(getApplicationContext(),"inside  checkdata",Toast.LENGTH_LONG).show();
        StringBuffer str = new StringBuffer();
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            String[] columns = {helper.USER_Name, helper.PASSWORD,helper.USER_ID,helper.GENDER};
            String selectionArgs[] = {name};

            //Select UserName,password from table_name
            //where userName =name;
             c = db.query(helper.USER_INFO, columns, helper.USER_Name+"=?", selectionArgs, null, null, null);//Select querey//

         //   Toast.makeText(getApplicationContext(),"try block",Toast.LENGTH_LONG).show();
            /*while(c.moveToNext())
            {
                Toast.makeText(getApplicationContext(),"inside loop checkdata",Toast.LENGTH_LONG).show();
                String a = c.getString(c.getColumnIndex(helper.USER_Name));
                Log.d("sql",""+a);
                String b = c.getString(c.getColumnIndex(helper.PASSWORD));
                Log.d("sql",""+b);
                str.append(a+" "+b);
            }*/
        }
        catch (SQLException e)
        {
            Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_LONG).show();
            Log.d("sql",""+e);
        }
        return c;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Intent.ACTION_MAIN);//overring BackButton pressed Method
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {  //change in Focus method for userName and password to display
        //error-"User Name or Password is Wrong"
        if((v.getId()==R.id.userName || v.getId()==R.id.password) && hasFocus==true)
        {
            error.setText("");
        }

    }
}
