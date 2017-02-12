package com.hackpoly;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ChangeEmail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);

        final EditText etNewEmail = (EditText) findViewById(R.id.etChangeEmail);
        final Button bChangeEmail = (Button) findViewById(R.id.bChangeEmail);
        final Button bCancelEmail = (Button) findViewById(R.id.bCancelEmail);


        if (bChangeEmail != null) {
            bChangeEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String ChangeEmailURL = "http://app.comli.com/changeEmail.php";
                    final String Username = Login.getUsername();
                    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    final String newEmail = etNewEmail.getText().toString();
                    boolean isValid = true;

                    if(!Register.isValidEmail(newEmail))
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ChangeEmail.this);
                        builder.setMessage("Not a valid Email address").setNegativeButton("Retry", null).create().show();
                        etNewEmail.requestFocus();
                        isValid = false;
                    }
                    if(isValid) {
                        StringRequest request = new StringRequest(Request.Method.POST, ChangeEmailURL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                System.out.println(response.toString());
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> parameters = new HashMap<>();
                                parameters.put("Email", newEmail);
                                parameters.put("Username", Username);

                                return parameters;
                            }
                        };

                        requestQueue.add(request);

                        AlertDialog.Builder builder = new AlertDialog.Builder(ChangeEmail.this);
                        builder.setMessage("Email Changed Successful").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent peopleIntent = new Intent(ChangeEmail.this, Setting.class);
                                ChangeEmail.this.startActivity(peopleIntent);
                            }
                        }).show();
                    }
                }
            });
        }
        bCancelEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent peopleIntent = new Intent(ChangeEmail.this, Setting.class);
                ChangeEmail.this.startActivity(peopleIntent);
            }
        });
    }
}

