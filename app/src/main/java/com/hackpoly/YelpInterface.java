package com.hackpoly;

import android.util.Log;

import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;
import com.yelp.clientlib.entities.options.CoordinateOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by walki on 2/11/2017.
 */

public class YelpInterface
{
    private String CONSUMER_KEY = "YUhkEmSpf8VM1MJ7gPROTQ";
    private String CONSUMER_SECRET = "iFAf8TxZDD5YRsBsbRQxyzdwsQA";
    private String TOKEN = "d78I56sAyB2w-EfNJUiUfvHuZenh8Szt";
    private String TOKEN_SECRET = "S6t778iQ0GcErwS56akKN20XTO0";
    private YelpAPIFactory yelpApiFactory;
    private YelpAPI yelpAPI;
    YelpInterface()
    {
        yelpApiFactory = new YelpAPIFactory(CONSUMER_KEY,CONSUMER_SECRET,TOKEN,TOKEN_SECRET);
        yelpAPI = yelpApiFactory.createAPI();
    }
    public void searchByCoordinate(double latitude, double longitude) throws IOException, InterruptedException
    {
        Map<String, String> params = new HashMap<>();
        params.put("term", "chicken");
        params.put("limit", "10");
        params.put("lang", "en");
        CoordinateOptions coordinate = CoordinateOptions.builder().latitude(latitude).longitude(longitude).build();
        Call<SearchResponse> call = yelpAPI.search(coordinate, params);
        SearchResponse searchResponse = call.execute().body();
        ArrayList<Business> businesses = searchResponse.businesses();
        //Log.d("Yelp count", searchResponse.total() +"");
        for(int i = 0; i < 10; i++)
        {
            Log.d("index", i + "");
            Log.d("yelp", businesses.get(i).name());
            Log.d("yelp", businesses.get(i).rating() +"");
            Log.d("yelp", businesses.get(i).distance() +"");

        }
    }
    public void searchByLocation(String location) throws IOException, InterruptedException
    {
        Map<String, String> params = new HashMap<>();
        params.put("term", "chicken");
        params.put("limit", "10");
        params.put("lang", "en");
        Call<SearchResponse> call = yelpAPI.search(location, params);
        SearchResponse searchResponse = call.execute().body();
        Log.d("test", searchResponse.toString() );
        ArrayList<Business> businesses = searchResponse.businesses();
        //Log.d("Yelp count", searchResponse.total() +"");
        for(int i = 0; i < 10; i++)
        {
            Log.d("index", i + "");
            Log.d("yelp", businesses.get(i).name());
            Log.d("yelp", businesses.get(i).rating() +"");
            Log.d("yelp", businesses.get(i) + "");
        }
    }


}
