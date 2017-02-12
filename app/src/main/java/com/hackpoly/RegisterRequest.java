package com.hackpoly;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by walki on 2/11/2017.
 */

public class RegisterRequest extends StringRequest
{
    private static final String REGISTER_REQUEST_URL= "http://app.comli.com/Register.php";
    private Map<String, String> params;

    public RegisterRequest (String FirstName, String LastName, String Email,String dateOfBirth,String Username,
                            String password,Response.Listener<String> listener)
    {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("FirstName", FirstName);
        params.put("LastName", LastName);
        params.put("Email", Email);
        params.put("dateOfBirth", dateOfBirth);
        params.put("Username", Username);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams()
    {
        return params;
    }
}
