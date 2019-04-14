package com.example.whereiamdisplay.Photo_PostAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import com.example.whereiamdisplay.PostPojo;
import com.example.whereiamdisplay.R;


public class Photo_PostAdapter extends ArrayAdapter {

    private Context context;
    private int layRes;
    private ArrayList<PostPojo> arrayList;
    private LayoutInflater inflater;

    public Photo_PostAdapter(Context context, int resource, ArrayList<PostPojo> objects) {
        super(context, resource, objects);

        this.context = context;
        this.layRes = resource;
        this.arrayList = objects;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(layRes, null);

        ImageView postImageView = (ImageView) view.findViewById(R.id.postImageView);
        TextView postTextView = (TextView) view.findViewById(R.id.postTextView);
        TextView postUserTextView = (TextView) view.findViewById(R.id.postUserTextView);

        PostPojo pojo = arrayList.get(position);

        if (!pojo.getPostImage().equals("")) {
            Glide.with(context)
                    .load(pojo.getPostImage())
                    .placeholder(R.mipmap.ic_launcher)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(postImageView);
        } else {
            postImageView.setVisibility(View.GONE);
        }
        if (!pojo.getPostText().equals("")) {
            postTextView.setText(pojo.getPostText());
        } else {
            postTextView.setVisibility(View.GONE);
        }if (!pojo.getPostUserText().equals("")) {
            postUserTextView.setText(pojo.getPostUserText());
        } else {
            postTextView.setVisibility(View.GONE);
        }

        return view;
    }
}
