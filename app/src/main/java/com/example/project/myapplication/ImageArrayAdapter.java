package com.example.project.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.*;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class ImageArrayAdapter extends ArrayAdapter<OboutImages> {

    int currentBitmapIndex =0;
    Bitmap currentBitmap =null;
    BitmapFactory.Options bitmapOptions;

    static int called =1;

    LayoutInflater inflater;
    View v;
    List<OboutImages> chats = new ArrayList<OboutImages>(); //Creating a list from OboutImage pojo
    ArrayList<Integer> positionList = new ArrayList<Integer>();
    //constructor of adapter whose display image on crime image activity;
    //taking two parameters first is activity other os list;
    ImageArrayAdapter(Activity activity, List<OboutImages> object)
    {

        super(activity,R.layout.single_card_view,object);
        chats = object;
        inflater = activity.getWindow().getLayoutInflater();


    }

    //defining a class holder used to initilize textvieew and image view;
    static class Holder
    {
        TextView desc,date,place;
        ImageView image;

    }


    //getview method called every time during each image loading
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        v = convertView;



        Holder holder ;
        if (v == null) {
            v = inflater.inflate(R.layout.single_card_view, parent, false);
            holder  = new Holder();
            holder.desc=(TextView) v.findViewById(R.id.description);
            holder.place = (TextView) v.findViewById(R.id.place);
            holder.date= (TextView) v.findViewById(R.id.date);
            holder.image = (ImageView)v.findViewById(R.id.crimeImage);




            v.setTag(holder);
        }

        else {
            holder = (Holder) v.getTag();
        }

        if(positionList.size()<=position)
        {
            positionList.add(0);
        }
       //using bitmap factory options to get an scaled image;



        //Bitmap bm =BitmapFactory.decodeFile(chats.get(position).getImageSource(),bitmapOptions);
       // holder.image.setImageBitmap(bm);
        
        holder.desc.setText(chats.get(position).getDescription());
        holder.place.setText(chats.get(position).getPlace());
        holder.date.setText(chats.get(position).getDate());
        holder.image.setImageBitmap(null);

        if(holder.image != null ) {
           // positionList.set(position,1);
            new MyTask(holder.image).execute(chats.get(position).getImageSource());

        }


        return v;

    }

    class MyTask extends AsyncTask<String,Void,Bitmap>
    {
        private final WeakReference<ImageView> imageViewReference;

        public MyTask(ImageView imageView) {
          //  Log.d("mysql","constructor calling");
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
         //  Log.d("mysql","do in background"+params[0]);
            int image = R.drawable.wallofshamelogo;
            //publishProgress();
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inSampleSize =2;
            return  BitmapFactory.decodeFile(params[0],bitmapOptions);


        }

        @Override
        protected void onProgressUpdate(Void... values) {

            ImageView imageView = imageViewReference.get();
            imageView.setImageResource(R.drawable.wallofshamelogo);

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
           // Log.d("mysql","on postExcecute");
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null) {

                ImageView imageView = imageViewReference.get();

                if (imageView != null) {
                    if (bitmap != null) {
                       // Log.d("mysql","setting bitmaps");
                        imageView.setImageBitmap(bitmap);
                    }
                }

            }
        }
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }
}
