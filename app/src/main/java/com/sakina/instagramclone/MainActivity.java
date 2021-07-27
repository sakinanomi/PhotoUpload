package com.sakina.instagramclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {

    int logOrSign;
    EditText username;
    EditText password;
    EditText confirmpassword;
    TextView change;
    Button button;
    ConstraintLayout background;
    ImageView logo;
    TextView name;



    //adding this so that when we press enter on keyborad it automatically logsin or signup
    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

        if(logOrSign==0)//if login is enabled
        {
            if(i==KeyEvent.KEYCODE_ENTER && keyEvent.getAction()==KeyEvent.ACTION_DOWN)//checking if enter key is pressed
            {
                logSigninto(view);//we use any view as we do not use it in our function

            }
        }
        else
        {
            //in this case do nothing if password calls the method
            if(view==confirmpassword)
            {
                if(i==KeyEvent.KEYCODE_ENTER && keyEvent.getAction()==KeyEvent.ACTION_DOWN)//checking if enter key is pressed
                {
                    logSigninto(view);//we use any view as we do not use it in our function

                }
            }
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.background || view.getId()==R.id.logo|| view.getId()==R.id.name)
        {
            //hiding the keyboard if clicked on these positions
            InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);//gets the keyboard
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }

    }


    public void Change(View view)//changing from signUp to login and vice-versa
    {
        if(logOrSign==0)
        {
            username.setText("");
            password.setText("");
            confirmpassword.setText("");

            confirmpassword.setVisibility(View.VISIBLE);
            change.setText("Or,Login");
            button.setText("Sign Up");
            logOrSign=1;

        }
        else
        {
            username.setText("");
            password.setText("");
            confirmpassword.setText("");
            confirmpassword.setVisibility(View.INVISIBLE);
            change.setText("or,Sign Up");
            button.setText("Login");
            logOrSign=0;
        }


    }

    public void logSigninto(View view)//button for signUp and login
    {
        final String uname;
        String pass;
        String cpass;
            if(logOrSign==0)
            {
                //user wants to login
                 uname=username.getText().toString();
                 pass=password.getText().toString();
                if(uname.matches("") || pass.matches(""))
                {
                    Toast.makeText(this, "Username or password cannot be empty", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Log.i("status","Log in");
                    //check if username exits
                    ParseUser.logInInBackground(uname, pass, new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if(user!=null)
                            {
                                Toast.makeText(MainActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                                loginpage(uname);
                            }
                            else
                            {
                                Log.i("Status","Failed "+e.toString());
                                Toast.makeText(MainActivity.this, "Failed to Login."+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }

            else//means user wants to signin
            {

                cpass=confirmpassword.getText().toString();
                uname=username.getText().toString();
                pass=password.getText().toString();
                if(uname.matches("") || pass.matches("")||cpass.matches(""))
                {
                    Toast.makeText(this, "Username or password or confirmpassword cannot be empty", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    if(pass.matches(cpass))
                    {
                       // Toast.makeText(this, "User Created", Toast.LENGTH_SHORT).show();
                        ParseUser user=new ParseUser();
                        user.setUsername(uname);
                        user.setPassword(pass);
                        user.signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e==null)
                                {
                                    Toast.makeText(MainActivity.this, "Sign Up sucessfull", Toast.LENGTH_SHORT).show();
                                    loginpage(uname);
                                }
                                else
                                {
                                    Toast.makeText(MainActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                    else
                    {
                        Toast.makeText(this, "Password confirmation failed", Toast.LENGTH_SHORT).show();
                    }
                }

            }




    }

    //changing activity
    public void loginpage(String user)
    {

        Intent intent=new Intent(getApplicationContext(),LoggedIn.class);
        intent.putExtra("user",user);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting up all the variables
        confirmpassword=(EditText)findViewById(R.id.confirmpassword);
        logOrSign=0;  //means login shown
        confirmpassword.setVisibility(View.INVISIBLE);
        confirmpassword.setText("");

        username=(EditText)findViewById(R.id.username);
        username.setText("");
        password=(EditText)findViewById(R.id.password);
        password.setText("");
        change=(TextView)findViewById(R.id.change);
        button=(Button)findViewById(R.id.button);

        background=(ConstraintLayout)findViewById(R.id.background);
        logo=(ImageView)findViewById(R.id.logo);
        name=(TextView)findViewById(R.id.name);

        password.setOnKeyListener(this);//setting on keyListener
        confirmpassword.setOnKeyListener(this);

        background.setOnClickListener(this);
        name.setOnClickListener(this);
        logo.setOnClickListener(this);

        //check if user is logged in
        if(ParseUser.getCurrentUser() !=null)
        {
            //Log.i("Current user","user logged in is "+ ParseUser.getCurrentUser().getUsername());
            loginpage(ParseUser.getCurrentUser().getUsername());
        }















        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }



}