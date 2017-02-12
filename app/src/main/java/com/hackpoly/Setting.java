package com.hackpoly;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Setting extends AppCompatActivity implements View.OnClickListener
{
    Button bLogout, bChangeName, bChangeEmail, bChangePassword, bDeleteAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        bLogout = (Button) findViewById(R.id.bLogout);
        bLogout.setOnClickListener(this);
        bChangeName = (Button) findViewById(R.id.bChangeName);
        bChangeName.setOnClickListener(this);
        bChangeEmail = (Button) findViewById(R.id.bChangeEmail);
        bChangeEmail.setOnClickListener(this);
        bChangePassword = (Button) findViewById(R.id.bChangePassword);
        bChangePassword.setOnClickListener(this);
        bDeleteAccount = (Button) findViewById(R.id.bDeleteAccount);
        bDeleteAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        final String DeleteAccountURL = "http://app.comli.com/deleteAccount.php";
        final String Username = Login.getUsername();
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        switch(v.getId())
        {
            //CHANGE NAME
            case R.id.bChangeName:
                //System.out.println("Change name");
                startActivity(new Intent(this, ChangeName.class));
                break;

            //CHANGE EMAIL ADDRESS
            case R.id.bChangeEmail:
                //System.out.println("Change Email");
                startActivity(new Intent(this, ChangeEmail.class));
                break;

            //CHANGE PASSWORD
            case R.id.bChangePassword:
                //System.out.println("Change Password");
                startActivity(new Intent(this, ChangePassword.class));
                break;

            //DELETE ACCOUNT
            case R.id.bDeleteAccount:
                //System.out.println("USERNAME" + Username);
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int x) {
                        switch (x){
                            case DialogInterface.BUTTON_POSITIVE:
                                StringRequest request = new StringRequest(Request.Method.POST, DeleteAccountURL, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        System.out.println(response);
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {     }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String,String> parameters  = new HashMap<>();
                                        parameters.put("Username", Username);

                                        return parameters;
                                    }
                                };
                                requestQueue.add(request);

                                Login.clearUsername();
                                Intent peopleIntent = new Intent(Setting.this, MainActivity.class);
                                Setting.this.startActivity(peopleIntent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

                break;

            //LOGOUT
            case R.id.bLogout:
                Login.clearUsername();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
