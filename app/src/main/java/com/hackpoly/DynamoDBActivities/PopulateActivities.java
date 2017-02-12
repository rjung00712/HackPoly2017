package com.hackpoly.DynamoDBActivities;

import com.hackpoly.AmazonAws.FoodGameUser;

import java.util.List;

/**
 * Created by waiphyo on 2/11/17.
 */

public interface PopulateActivities {
    public void execute(List<FoodGameUser> foodGameUserList);
}
