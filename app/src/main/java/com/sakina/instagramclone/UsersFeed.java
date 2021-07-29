package com.sakina.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UsersFeed extends AppCompatActivity //implements View.OnClickListener
{
    Button date;
    Button likes;
    int likef = 0;//means not liked


   /* @Override
    public void onClick(View view) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Likes");
        query.getInBackground(String.valueOf(view.getId()), new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject obj, ParseException e) {
                if (e == null) {
                    if (likef == 0)//want to like
                    {
                        likef = 1;
                        obj.put("Likes", obj.getInt("Likes") + 1);
                        obj.saveInBackground();
                        likes.setBackgroundColor(Color.GREEN);


                    } else//already liked
                    {
                        likef = 0;
                        obj.put("Likes", obj.getInt("Likes") - 1);
                        obj.saveInBackground();
                        likes.getBackground().clearColorFilter();
                    }
                } else {
                    Log.i("Error", e.getMessage());
                }
            }
        });

    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_feed);


        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        //TextView user=(TextView)findViewById(R.id.username);
        setTitle(username + "'s Feed");




        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        //Log.i("check",username);
        //user.setText(username);

       /* final View.OnClickListener onclicklistener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("onclick set for:",String.valueOf(view.getTag()));
                ParseQuery<ParseObject> query1=ParseQuery.getQuery("Image");
                query1.getInBackground(String.valueOf(view.getTag()), new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {

                        if(e==null)
                        {
                            if(likef==0)//want to like
                            {
                                likef=1;
                                object.put("Likes",object.getInt("Likes")+1);
                                object.saveInBackground();
                                likes.setText((object.getInt("Likes")));
                                likes.setBackgroundColor(Color.GREEN);


                            }
                            else//already liked
                            {
                                likef=0;
                                object.put("Likes",object.getInt("Likes")-1);
                                object.saveInBackground();
                                likes.setText((object.getInt("Likes")));
                                likes.getBackground().clearColorFilter();
                            }
                        }

                        else
                        {
                            Log.i("Error",e.getMessage());
                        }

                    }
                });


            }
        };*/

        //Log.i("username",username);
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("Image");
        query.whereEqualTo("username", username);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null)//no error
                {

                    if (objects.size() > 0)//if images present
                    {
                        for (final ParseObject obj : objects) {
                            //Log.i("obj", String.valueOf(obj));
                            ParseFile file = (ParseFile) obj.get("image");
                            file.getDataInBackground(new GetDataCallback()
                            {//downloading the file
                                @Override
                                public void done(byte[] data, ParseException e) {
                                    if(data!=null) {
                                        if (data.length > 0 && e == null) {
                                            LinearLayout ll = new LinearLayout(getApplicationContext());
                                            ll.setLayoutParams(new ViewGroup.LayoutParams(
                                                    ViewGroup.LayoutParams.FILL_PARENT,
                                                    ViewGroup.LayoutParams.WRAP_CONTENT
                                            ));
                                            ll.setOrientation(LinearLayout.VERTICAL);
                                            ll.setGravity(Gravity.CENTER);
                                            ll.setPadding(20, 10, 20, 30);


                                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                            ImageView imageView = new ImageView(getApplicationContext());
                                            imageView.setLayoutParams(new ViewGroup.LayoutParams(
                                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                                    ViewGroup.LayoutParams.WRAP_CONTENT

                                            ));

                                            imageView.setImageBitmap(bitmap);

                                            ll.addView(imageView);

                                            LinearLayout ll2 = new LinearLayout(getApplicationContext());
                                            ll2.setLayoutParams(new ViewGroup.LayoutParams(
                                                    ViewGroup.LayoutParams.FILL_PARENT,
                                                    ViewGroup.LayoutParams.WRAP_CONTENT
                                            ));
                                            ll2.setOrientation(LinearLayout.HORIZONTAL);
                                            ll2.setGravity(Gravity.CENTER);

                                            date = new Button(getApplicationContext());
                                            date.setLayoutParams(new ViewGroup.LayoutParams(
                                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                                    ViewGroup.LayoutParams.WRAP_CONTENT
                                            ));


                                            java.util.Date d = obj.getCreatedAt();

                                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                            String strDate = formatter.format(d);


                                            date.setText(strDate);
                                            Log.i("Date is", strDate);

                                            //date.setText("Date");

                                            likes = new Button(getApplicationContext());
                                            likes.setLayoutParams(new ViewGroup.LayoutParams(
                                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                                    ViewGroup.LayoutParams.WRAP_CONTENT
                                            ));



                                            likes.setTag((obj.getObjectId()));//giving every like button the same Tag as its rowId
                                            //likes.setOnClickListener(onclicklistener);

                                            //Log.i("Tag", String.valueOf(likes.getTag()));


                                            /*likes.setOnClickListener(new View.OnClickListener() {

                                                @Override
                                                public void onClick(View view) {
                                                    Log.i("onclick set for:",String.valueOf(view.getTag()));
                                                    ParseQuery<ParseObject> query1=ParseQuery.getQuery("Image");
                                                    query1.getInBackground(String.valueOf(view.getTag()), new GetCallback<ParseObject>() {
                                                        @Override
                                                        public void done(ParseObject object, ParseException e) {

                                                            if(e==null)
                                                            {
                                                                if(likef==0)//want to like
                                                                {
                                                                    likef=1;
                                                                    object.put("Likes",object.getInt("Likes")+1);
                                                                    object.saveInBackground();
                                                                    //likes.setText((object.getInt("Likes")));
                                                                    likes.setBackgroundColor(Color.GREEN);


                                                                }
                                                                else//already liked
                                                                {
                                                                    likef=0;
                                                                    object.put("Likes",object.getInt("Likes")-1);
                                                                    object.saveInBackground();
                                                                    //likes.setText((object.getInt("Likes")));
                                                                    likes.getBackground().clearColorFilter();
                                                                }
                                                            }

                                                            else
                                                            {
                                                                Log.i("Error",e.getMessage());
                                                            }

                                                        }
                                                    });



                                                }
                                            });*/
                                            //!!!implement like feature!!!



                                            //setLikes(username);
                                            likes.setText(String.valueOf(obj.getInt("Likes")));


                                            ll2.addView(date);
                                            ll2.addView(likes);


                                            ll.addView(ll2);


                                            linearLayout.addView(ll);
                                            //Log.i("Checking", "After the upload");


                                        }
                                    }

                                    else
                                    {
                                        Log.i("error","Still stuck");
                                    }
                                }
                            });
                        }




                    }

                    else {
                        Toast.makeText(UsersFeed.this, "Users Feed is Empty", Toast.LENGTH_SHORT).show();
                        Log.i("status", "no image");
                    }

                } else {
                    Log.i("error", e.getMessage());
                }

            }
        });


    }
}


