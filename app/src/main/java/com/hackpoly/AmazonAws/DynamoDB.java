package com.hackpoly.AmazonAws;

import android.content.Context;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;
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



    public void addUser(final FoodGameUser foodGameUser) {
        Runnable runnable = new Runnable() {
            public void run() {
                mapper.save(foodGameUser);
            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();
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
        Thread mythread = new Thread(runnable);
        mythread.start();
    }

    public void addFriend(final String userName, final String friendUserName) {
        Runnable runnable = new Runnable() {
            public void run() {
                FoodGameUser selectedUser = mapper.load(FoodGameUser.class, userName);
                selectedUser.addFriend(friendUserName);
                mapper.save(selectedUser);
            }
        };
    }

    public void removeFriend(final String userName, final String friendUserName) {
        Runnable runnable = new Runnable() {
            public void run() {
                FoodGameUser selectedUser = mapper.load(FoodGameUser.class, userName);
                selectedUser.addFriend(friendUserName);
                mapper.save(selectedUser);
            }
        };
    }

    public FoodGameUser getUser(final String userName) {
        try {
            return mapper.load(FoodGameUser.class, userName);
        } catch (AmazonServiceException ase) {
            System.out.println(ase.toString());
            return null;
        }
    }

}
