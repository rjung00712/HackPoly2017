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

public class ChangeName extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);

        final EditText etChangeFirstName = (EditText) (findViewById(R.id.etChangeFirstName));
        final EditText etChangeLastName = (EditText) (findViewById(R.id.etChangeLastName));
        final Button bChangeName = (Button) (findViewById(R.id.bChangeName));
        final Button bCancelName = (Button) (findViewById(R.id.bCancelName));

        bChangeName.setOnClickListener(new View.OnClickListener()
        {
            final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            final String ChangeNameURL = "http://app.comli.com/changeName.php";
            final String Username = Login.getUsername();


            @Override
            public void onClick(View v) {
                final String ChangeFirstName = etChangeFirstName.getText().toString();
                final String ChangeLastName = etChangeLastName.getText().toString();


                StringRequest request = new StringRequest(Request.Method.POST, ChangeNameURL, new Response.Listener<String>() {
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
                        parameters.put("FirstName", ChangeFirstName);
                        parameters.put("LastName", ChangeLastName);
                        parameters.put("Username", Username);

                        return parameters;
                    }
                };
                requestQueue.add(request);

                AlertDialog.Builder builder = new AlertDialog.Builder(ChangeName.this);
                builder.setMessage("Name Changed Successful").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent peopleIntent = new Intent(ChangeName.this, Setting.class);
                        ChangeName.this.startActivity(peopleIntent);
                    }
                }).show();

            }
        });

        bCancelName.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                Intent peopleIntent = new Intent(ChangeName.this, Setting.class);
                ChangeName.this.startActivity(peopleIntent);
            }
        });
    }
}

