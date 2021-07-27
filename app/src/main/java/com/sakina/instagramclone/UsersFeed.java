package com.sakina.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class UsersFeed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_feed);
        Intent intent=getIntent();
        String username=intent.getStringExtra("username");
        //TextView user=(TextView)findViewById(R.id.username);
        setTitle(username+"'s Feed");

        final LinearLayout linearLayout=(LinearLayout)findViewById(R.id.linearLayout);
        //Log.i("check",username);
        //user.setText(username);

        //Log.i("username",username);
        ParseQuery<ParseObject> query=ParseQuery.getQuery("Image");
        query.whereEqualTo("username",username);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e==null)//no error
                {
                    if(objects.size()>0)//if images present
                    {
                       for(ParseObject obj:objects)
                       {
                           ParseFile file=(ParseFile) obj.get("image");
                           file.getDataInBackground(new GetDataCallback(){//downloading the file
                               @Override
                               public void done(byte[] data, ParseException e) {
                                   if(data.length>0 && e==null)
                                   {
                                       Bitmap bitmap=BitmapFactory.decodeByteArray(data,0,data.length);
                                       ImageView imageView=new ImageView(getApplicationContext());
                                       imageView.setLayoutParams(new ViewGroup.LayoutParams(
                                               ViewGroup.LayoutParams.MATCH_PARENT,
                                               ViewGroup.LayoutParams.WRAP_CONTENT
                                       ));

                                       imageView.setImageBitmap(bitmap);
                                       linearLayout.addView(imageView);
                                   }
                               }
                           });
                       }
                    }
                    else
                    {
                        Toast.makeText(UsersFeed.this, "Users Feed is Empty", Toast.LENGTH_SHORT).show();
                        Log.i("status","no image");
                    }

                }
                else
                {
                    Log.i("error",e.getMessage());
                }

            }
        });


    }
}