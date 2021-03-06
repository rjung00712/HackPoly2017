package com.hackpoly.AmazonAws;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;
import com.hackpoly.DynamoDBActivities.LogInActivity;
import com.hackpoly.DynamoDBActivities.PopulateActivities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by waiphyo on 2/11/17.
 */

public class DynamoDB {

    private CognitoCachingCredentialsProvider credentialsProvider;
    private AmazonDynamoDBClient ddbClient;
    private DynamoDBMapper mapper;
    public DynamoDB(Context context) {
        credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                Constants.IDENTITY_POOL_ID,
                Constants.CURRENT_REGION);
        ddbClient = new AmazonDynamoDBClient(credentialsProvider);
        ddbClient.setRegion(Region.getRegion(Constants.CURRENT_REGION));
        mapper = new DynamoDBMapper(ddbClient);
    }

    public void addUser(final FoodGameUser foodGameUser, final LogInActivity logInActivity) {
        Runnable runnable = new Runnable() {
            public void run() {
                FoodGameUser user = mapper.load(FoodGameUser.class, foodGameUser.getUsername());
                if (user != null) {
                    logInActivity.execute(null);
                } else {
                    foodGameUser.setActive(true);
                    mapper.save(foodGameUser);
                    logInActivity.execute(foodGameUser);
                }
            }
        };
        new Thread(runnable).start();
    }

    public void updateLocation(final String userName, final double latitude, final double longitude) {
        Runnable runnable = new Runnable() {
            public void run() {
                FoodGameUser selectedUser = mapper.load(FoodGameUser.class, userName);
                selectedUser.setLatitude(latitude);
                selectedUser.setLongitude(longitude);
                mapper.save(selectedUser);
            }
        };
        new Thread(runnable).start();
    }

    public void addFriend(final String userName, final String friendUserName) {
        Runnable runnable = new Runnable() {
            public void run() {
                FoodGameUser selectedUser = mapper.load(FoodGameUser.class, userName);
                selectedUser.addFriend(friendUserName);
                mapper.save(selectedUser);
            }
        };
        new Thread(runnable).start();
    }

    public void removeFriend(final String userName, final String friendUserName) {
        Runnable runnable = new Runnable() {
            public void run() {
                FoodGameUser selectedUser = mapper.load(FoodGameUser.class, userName);
                selectedUser.addFriend(friendUserName);
                mapper.save(selectedUser);
            }
        };
        new Thread(runnable).start();
    }

    public void userLogOn(final String userName, final String password, final LogInActivity logInActivity) {
        Runnable runnable = new Runnable() {
            public void run() {
                FoodGameUser selectedUser = mapper.load(FoodGameUser.class, userName);
                if (selectedUser == null || !selectedUser.getPassword().equals(password)) {
                    selectedUser = null;
                } else {
                    selectedUser.setActive(true);
                }
                logInActivity.execute(selectedUser);
            }
        };
        new Thread(runnable).start();
    }

    public void userLogOff(final String userName) {
        Runnable runnable = new Runnable() {
            public void run() {
            FoodGameUser selectedUser = mapper.load(FoodGameUser.class, userName);
            if (selectedUser != null) {
                selectedUser.setActive(false);
            }
            }
        };
    }

    public void getFriends(final String userName, final PopulateActivities populateActivities) {
        Runnable runnable = new Runnable() {
            public void run() {
                FoodGameUser selectedUser = mapper.load(FoodGameUser.class, userName);
                List<FoodGameUser> allActiveFriends = new ArrayList<>();
                for (String user : selectedUser.getFriendSet()) {
                    FoodGameUser currentUser = mapper.load(FoodGameUser.class, user);
                    if (currentUser != null && currentUser.isActive()) {
                        allActiveFriends.add(currentUser);
                    }
                }
                populateActivities.execute(allActiveFriends);
            }
        };
        new Thread(runnable).start();
    }
}
