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

public class ChangePassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        final EditText etChangeOldPassword = (EditText) (findViewById(R.id.etChangeOldPassword));
        final EditText etChangeNewPassword = (EditText) (findViewById(R.id.etChangeNewPasssword));
        final EditText etChangeNewPasswordVerify = (EditText) (findViewById(R.id.etChangeNewPasswordVerify));
        final Button bChangePassword = (Button) (findViewById(R.id.bChangePassword));
        final Button bCancelPassword = (Button) (findViewById(R.id.bCanelPassword));
        final String OldPassword = Login.getPassword();
        //1234System.out.println("OOLD: "  + OldPassword);
        if (bChangePassword != null) {
            bChangePassword.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    final String ChangeOldPassword = etChangeOldPassword.getText().toString();
                    final String ChangeNewPassword = etChangeNewPassword.getText().toString();
                    final String ChangeNewPasswordVerify = etChangeNewPasswordVerify.getText().toString();
                    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    final String ChangePasswordURL = "http://app.comli.com/changePassword.php";
                    final String Username = Login.getUsername();
                    boolean isValid = true;

                    //  System.out.println("OLD: "  + OldPassword + "\nchangeOLD: " + ChangeOldPassword);

                    if(!ChangeOldPassword.equals(OldPassword)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);
                        builder.setMessage("Incorrect Old Password").setNegativeButton("Retry", null).create().show();
                        etChangeOldPassword.requestFocus();
                        isValid = false;
                    }
                    if(!ChangeNewPassword.equals(ChangeNewPasswordVerify)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);
                        builder.setMessage("New Password does not match").setNegativeButton("Retry", null).create().show();
                        etChangeNewPassword.requestFocus();
                        isValid = false;
                    }
                    if(isValid) {

                        StringRequest request = new StringRequest(Request.Method.POST, ChangePasswordURL, new Response.Listener<String>() {
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
                                parameters.put("password", ChangeNewPassword);
                                parameters.put("Username", Username);

                                return parameters;
                            }
                        };
                        requestQueue.add(request);

                        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);
                        builder.setMessage("Password Changed Successful").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Login.setNewPassword(ChangeNewPassword);
                                Intent peopleIntent = new Intent(ChangePassword.this, Setting.class);
                                ChangePassword.this.startActivity(peopleIntent);
                            }
                        }).show();
                    }
//                    else
//                    {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);
//                        builder.setMessage("Error, Password change unsuccessful").setNegativeButton("Retry", null).create().show();
//                        etChangeNewPassword.requestFocus();
//                    }
                }
            });
        }
        bCancelPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent peopleIntent = new Intent(ChangePassword.this, Setting.class);
                ChangePassword.this.startActivity(peopleIntent);
            }
        });
    }
}

