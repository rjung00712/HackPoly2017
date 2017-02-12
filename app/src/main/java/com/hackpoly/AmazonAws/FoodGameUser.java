package com.hackpoly.AmazonAws;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by waiphyo on 2/11/17.
 */

@DynamoDBTable(tableName = Constants.TEST_TABLE_NAME)
public class FoodGameUser {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private double latitude;
    private double longitude;
    private boolean active;
    private Set<String> friendSet;

    public FoodGameUser() {
        friendSet = new HashSet<>();
        friendSet.add("InvalidUser");
    }

    @DynamoDBAttribute(attributeName = "emailAddress")
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @DynamoDBHashKey(attributeName = Constants.HASH_KEY)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @DynamoDBAttribute(attributeName = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @DynamoDBAttribute(attributeName = "firstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @DynamoDBAttribute(attributeName = "lastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @DynamoDBAttribute(attributeName = "latitude")
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @DynamoDBAttribute(attributeName = "longitude")
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @DynamoDBAttribute(attributeName = "active")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @DynamoDBAttribute(attributeName = "friendSet")
    public Set<String> getFriendSet() {
        return friendSet;
    }

    public void setFriendSet(Set<String> friendSet) {
        this.friendSet = friendSet;
    }

    public void addFriend(String friendUserName) {
        friendSet.add(friendUserName);
    }

    public void removeFriend(String friendUserName) {
        friendSet.remove(friendUserName);
    }

    public String toString() {
        return getUsername() + Constants.TAB +
                getFirstName() + Constants.TAB +
                getLastName() + Constants.TAB +
                isActive() + Constants.TAB +
                getFriendSet();

    }
}
