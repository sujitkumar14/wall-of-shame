    package com.example.project.myapplication;


    import android.content.ContentValues;
    import android.content.res.Resources;
    import android.database.SQLException;
    import android.database.sqlite.SQLiteDatabase;
    import android.media.Image;
    import android.os.Bundle;
    import android.app.Activity;
    import android.text.Editable;
    import android.text.TextWatcher;
    import android.view.MotionEvent;
    import android.widget.*;
    import android.view.View;
    import android.util.Log;
    import android.content.Intent;

    public class Registration extends Activity  implements View.OnClickListener,View.OnTouchListener{


        EditText name,userName,password,mobile,confirmPassword,gender;
        String perName,perUserName,perPass,perConfirmPassword,perGender;
        TextView countUserName;
        long perMobile;
        Button register;
        ImageView correctImage, wrongImage,male,female;
        DBHelper helper;
        Resources  res;
        TitleFragmentClass titleFragmentClass;
        int countCharactersUserName;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_registration);

            //setting top of the activity/////////////////
            View v = getLayoutInflater().inflate(R.layout.fragment_top,null);
            View titleFragment  = findViewById(R.id.titleFragment);
            TextView title = (TextView)titleFragment.findViewById(R.id.title);

            ImageView camera = (ImageView)titleFragment.findViewById(R.id.camera);
            title.setText("Sign Up");
            camera.setVisibility(View.INVISIBLE);


           // titleFragmentClass = new TitleFragmentClass(v,this,false,false,"Sign Up");
           //titleFragmentClass.setTitleFragment();

            //setting top of the actiity ends///////////////////


            name = (EditText)findViewById(R.id.name);
            userName = (EditText)findViewById(R.id.userName);
            password = (EditText)findViewById(R.id.password);
            confirmPassword = (EditText)findViewById(R.id.confirmPassword);

            confirmPassword.setOnTouchListener(this);
            password.setOnTouchListener(this);


            mobile = (EditText)findViewById(R.id.mobile);
            gender = (EditText)findViewById(R.id.gender);
            register = (Button)findViewById(R.id.register);
            correctImage = (ImageView)findViewById(R.id.right);
            countUserName = (TextView)findViewById(R.id.countUserName);

            male = (ImageView) findViewById(R.id.male);
            female = (ImageView)findViewById(R.id.female);




            correctImage.setVisibility(View.INVISIBLE);
            gender.setFocusable(false);
            gender.setClickable(false);
            countCharactersUserName = Integer.parseInt(countUserName.getText().toString());

            register.setOnClickListener(this);
            male.setOnClickListener(this);
            female.setOnClickListener(this);
            helper = new DBHelper(Registration.this);
            userName.addTextChangedListener(new TextWatcher() {

                String userNameCount;

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {



                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                  countUserName.setText(""+(12-userName.getText().toString().length()));
                    if(userName.getText().toString().length()>12)
                    {
                        userName.setText(userNameCount);
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if(userName.getText().toString().length()==12)
                    {
                        userNameCount = userName.getText().toString();
                    }

                }
            });

            confirmPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if(password.getText().toString().equals(confirmPassword.getText().toString()))
                    {
                       correctImage.setImageResource(R.drawable.rightblue);
                        correctImage.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                     correctImage.setImageResource(R.drawable.wrongred);
                        correctImage.setVisibility(View.VISIBLE);
                    }

                }
            });


        }
        @Override
        public void onClick(View v) {


            if (v.getId() == R.id.register) {
                try {
                    perName = name.getText().toString();
                    perUserName = userName.getText().toString();
                    perPass = password.getText().toString();
                    perGender = gender.getText().toString();
                    perConfirmPassword = confirmPassword.getText().toString();


                     if (perPass.equals("")) {
                        Toast.makeText(getApplicationContext(), "Enter your Password", Toast.LENGTH_SHORT).show();
                    } else if (perConfirmPassword.equals("")) {
                        Toast.makeText(getApplicationContext(), "password  Mismatch ", Toast.LENGTH_SHORT).show();
                        wrongImage.setVisibility(View.VISIBLE);
                    } else if (mobile.getText().toString().equals("")) {

                        Toast.makeText(getApplicationContext(), "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        if (perPass.equals(perConfirmPassword)) {
                            perMobile = Long.parseLong(mobile.getText().toString());


                            long id = insertData();//inserting a database in the table;

                            if (id < 0) {
                                Toast.makeText(getApplicationContext(), "unsuccessful", Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(getApplicationContext(), "successfully Registered ", Toast.LENGTH_LONG).show();

                                //calling login activity
                                Intent i = new Intent(getApplicationContext(), login_activity.class);
                                startActivity(i);

                            }
                        } else {

                          Toast.makeText(getApplicationContext(),"Password and confirm password missmatch",Toast.LENGTH_LONG).show();
                        }

                    }
                } catch (Exception e) {
                    Log.e("sql", "" + e);
                    if (perName.equals("")) {
                        Toast.makeText(getApplicationContext(), "Enter your Name", Toast.LENGTH_SHORT).show();
                    } else if (perUserName.equals("")) {
                        Toast.makeText(getApplicationContext(), "Enter your User Name", Toast.LENGTH_SHORT).show();
                    } else if (perPass.equals("")) {
                        Toast.makeText(getApplicationContext(), "Enter your Password", Toast.LENGTH_SHORT).show();
                    } else if (perConfirmPassword.equals("")) {
                        Toast.makeText(getApplicationContext(), "password  Mismatch ", Toast.LENGTH_SHORT).show();
                        wrongImage.setVisibility(View.VISIBLE);
                    } else if (mobile.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                    }
                    else
                        if(perGender.equals(""))
                        {
                            Toast.makeText(getApplicationContext(),"Select Your Gender",Toast.LENGTH_SHORT).show();
                        }


                }
            }
            else
                if (v.getId()==R.id.male)
                {
                    gender.setText("Male");

                }
            else
                    if (v.getId()==R.id.female)
                    {
                        gender.setText("Female");
                    }
        }

        long  insertData()
        {
            long id=-1;
            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            try {

                contentValues.put(helper.USER_Name, perUserName);
                contentValues.put(helper.PASSWORD, perPass);
                contentValues.put(helper.MOBILE_NO, perMobile);
                contentValues.put(helper.GENDER,perGender);
              //  Log.d("mysql", "" + perMobile);
                id=db.insert(helper.USER_INFO, null, contentValues);

            }
            catch(SQLException e) {
                Log.d("sql",""+e);
            }
            return id;

        }


        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(v.getId()==R.id.confirmPassword)
            {

                correctImage.setVisibility(View.INVISIBLE);
            }
            else
                if(v.getId()==R.id.password)
                {

                    correctImage.setVisibility(View.INVISIBLE);
                }
            return false;
        }
    }
