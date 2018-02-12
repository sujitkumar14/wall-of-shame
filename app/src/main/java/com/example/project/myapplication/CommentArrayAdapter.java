package com.example.project.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.app.Activity;
import java.util.List;
import java.util.ArrayList;
import android.widget.*;
/**
 * Created by Rajkumar on 07-19-2015.
 */
public class CommentArrayAdapter extends ArrayAdapter {

    TextView date, userName, comments;
    LayoutInflater inflater;
    View v;
    List<OboutComments> commentList = new ArrayList<>();

    public CommentArrayAdapter(Activity activity, List<OboutComments> comments) {
        super(activity, R.layout.comment_card_view, comments);
        commentList = comments;
        inflater = activity.getWindow().getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        v = convertView;


        if (v == null)
            v = inflater.inflate(R.layout.comment_card_view, parent, false);

        date = (TextView) v.findViewById(R.id.date);
        userName = (TextView) v.findViewById(R.id.userName);
        comments = (TextView) v.findViewById(R.id.comment);

        comments.setText(commentList.get(position).getComment());
        userName.setText(commentList.get(position).getUserName());
        date.setText(commentList.get(position).getDate());


        return v;

    }

}
