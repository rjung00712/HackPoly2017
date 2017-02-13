package com.hackpoly;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hackpoly.AmazonAws.DynamoDB;
import com.hackpoly.AmazonAws.FoodGameUser;
import com.hackpoly.DynamoDBActivities.PopulateActivities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Richard on 2/12/17.
 */

public class ListFriends extends AppCompatActivity {
    // Request code for READ_CONTACTS. It can be any number > 0
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    private static final int mId = 0;

    private Map<String, String> requestList = new HashMap<>();           // list of users to add to game

    private NotificationCompat.Builder mBuilder;
    private TaskStackBuilder stackBuilder;

    private static ArrayAdapter<String> adapter;         // list textview of all friends

    private ListView names; // The ListView
    private RadioButton submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        this.names = (ListView) findViewById(R.id.friendsNames);   // list of friends nearby

        submitButton = (RadioButton) findViewById(R.id.submitButton);

        DynamoDB db = new DynamoDB(ListFriends.this);

        final String userName = Login.getUsername();

        db.getFriends(userName, new PopulateActivities() {
            @Override
            public void execute(List<FoodGameUser> foodGameUserList) {
                showContacts(foodGameUserList);
            }
        });
    }

    // Show the contacts in the ListView
    private void showContacts(final List<FoodGameUser> fList) {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    List<String> friendsList = new ArrayList<String>();

                    try {
                        for (FoodGameUser f : fList) {
                            friendsList.add(f.getUsername());
                        }
                        adapter = new ArrayAdapter<String>(ListFriends.this, android.R.layout.simple_list_item_1, friendsList);
                        names.setAdapter(adapter);      // arrayadapter filled with friends' names
                        registerClickCallback();        // set button click functionality to friends names
                        sendRequests();                 // activate submit request button to request users to participate
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {

//            } else {
                Toast.makeText(this, "Until you grant the permission, we cannot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

//    // Read name of all contacts, return a list of names
//    private List<String> getContactNames() {
//        List<String> contacts = new ArrayList<>();
//        ContentResolver cr = getContentResolver();  // Get the ContentResolver
//        // Get the cursor of all the contacts
//        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
//
//        // Move the cursor to first. Also check whether the cursor is empty or not
//        if(cursor.moveToFirst()) {
//            do {    // Iterate through the cursor
//                // Get the contacts name
//                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//                contacts.add(name);
//            } while (cursor.moveToNext());
//        }
//        cursor.close(); // Close the cursor
//
//        return contacts;
//    }

    public void registerClickCallback() {
        names.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            boolean selected = false;



            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView textView = (TextView) viewClicked;


                selected = !(selected);
                if(selected) {
                    textView.setBackgroundColor(Color.CYAN);

                    requestList.put(textView.getText().toString(), "");
                } else {
                    textView.setBackgroundColor(Color.TRANSPARENT);

                    requestList.remove(textView.getText().toString());
                }
//                String message = "You clicked # " + position + ", which is string: " + textView.getText().toString();
//                Toast.makeText(ListFriends.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void sendRequests() {
        submitButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(ListFriends.this)
                        .setSmallIcon(R.drawable.android)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");

                // Creates an explicit intent for an Activity in your app
                Intent resultIntent = new Intent(ListFriends.this, Login.class);

                // The stack builder object will contain an artificial back stack for the
                // started Activity.
                // This ensures that navigating backward from the Activity leads out of
                // your application to the Home screen.
                stackBuilder = TaskStackBuilder.create(ListFriends.this);
                // Adds the back stack for the Intent (but not the Intent itself)
                stackBuilder.addParentStack(Login.class);
                // Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                // mId allows you to update the notification later on.
                mNotificationManager.notify(mId, mBuilder.build());


            }
        });
    }

    public void send() {
    }


}


