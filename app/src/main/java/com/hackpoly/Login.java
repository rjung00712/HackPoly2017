package com.hackpoly;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.hackpoly.AmazonAws.DynamoDB;
import com.hackpoly.AmazonAws.FoodGameUser;
import com.hackpoly.DynamoDBActivities.LogInActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity
{
    private static SharedPreferences ps;
    private static SharedPreferences.Editor pe;
    private static String Username;
    private static String Password;
    private static boolean invalid = true;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ps = getPreferences(0);
        pe = ps.edit();

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);

        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final TextView tvRegisterLink = (TextView) findViewById(R.id.tvRegister);
        final TextView tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);

        tvRegisterLink.setPaintFlags(tvRegisterLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvForgotPassword.setPaintFlags(tvForgotPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        String spUsername = ps.getString("Username","");
        String spPassword = ps.getString("password", "");

        //When Register is clicked, send user to Register screen; Register.class
        tvRegisterLink.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent registerIntent = new Intent(Login.this, Register.class);
                Login.this.startActivity(registerIntent);
            }
        });

        //When Forgot password is clicked, send user to Forgot password screen; ForgotPassword.class
        tvForgotPassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent ForgotPasswordIntent = new Intent(Login.this, ForgotPassword.class);
                Login.this.startActivity(ForgotPasswordIntent);
            }
        });

        //auto-login
        if (spUsername.length() != 0 || spPassword.length() != 0)
        {
            etUsername.setText(spUsername);
            etPassword.setText(spPassword);
            //create an intent to store Username information for UserActivity
            Intent userIntent = new Intent(Login.this, UserActivity.class);
            userIntent.putExtra("Username", spUsername);
            Username = spUsername;
            userIntent.putExtra("password", spPassword);
            Password = spPassword;
            //start activity to UserActivity.class
            Login.this.startActivity(userIntent);
        }


        //Login process
        if (bLogin != null) {
            //when Login button is clicked, store username and password to a String
            bLogin.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    final String Username = etUsername.getText().toString();
                    final String password = etPassword.getText().toString();

                    DynamoDB db = new DynamoDB(Login.this);
                    db.userLogOn(Username, password, new LogInActivity()
                    {
                        @Override
                        public void execute(FoodGameUser foodGameUser)
                        {
                            if(foodGameUser != null)
                            {
                                //invalid = false;
                                pe.putString("Username", Username);
                                pe.putString("password", password);
                                pe.commit();

                                //create an intent to store Username information for UserActivity
                                Intent userIntent = new Intent(Login.this, UserActivity.class);
                                userIntent.putExtra("Username", Username);
                                userIntent.putExtra("password", password);

                                //start activity to UserActivity.class
                                Login.this.startActivity(userIntent);
                            }
                            else
                            {
                                //invalid = true;
                                //error();
                            }
                        }
                    });
                    //if(invalid)
                   // {
//                        AlertDialog.Builder Alert = new AlertDialog.Builder(Login.this);
//                        Alert.setMessage("Invalid Username or Password");
//                        Alert.setPositiveButton("OK", null);
//                        etPassword.setText("");
//                        Alert.create().show();
//                        invalid = true;
                  //  }
                }
            });
        }

    }

    public static void clearUsername()
    {
        pe.clear();
        pe.commit();
    }

    public static String getUsername() { return Username;}
    public static void setNewPassword(String x)
    {
        Password = x;
    }
    public static String getPassword() { return Password; }

}
