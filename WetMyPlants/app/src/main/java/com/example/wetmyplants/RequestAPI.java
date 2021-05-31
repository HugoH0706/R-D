package com.example.wetmyplants;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RequestAPI extends AppCompatActivity {
    private RequestQueue queue;
    private Button postButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_api);
        // initializing the queue object
        queue = Volley.newRequestQueue(this);
        postButton = (Button)findViewById(R.id.button9);
        postButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                StringRequest stringRequest = searchNameStringRequest("/detail_options/");
                queue.add(stringRequest);
            }
        });
    }

    private StringRequest searchNameStringRequest(String nameSearch) {
        final String URL_PREFIX = "http://openfarm.cc/api/v1";

        String url = URL_PREFIX + nameSearch;

        // 1st param => type of method (GET/PUT/POST/PATCH/etc)
        // 2nd param => complete url of the API
        // 3rd param => Response.Listener -> Success procedure
        // 4th param => Response.ErrorListener -> Error procedure
        return new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    // 3rd param - method onResponse lays the code procedure of success return
                    // SUCCESS
                    @Override
                    public void onResponse(String response) {
                        // try/catch block for returned JSON data
                        // see API's documentation for returned format
                        try {
                            /*
                            JSONObject result = new JSONObject(response).getJSONObject("data");
                            JSONObject token = result.getJSONObject("token");
                            String expiration = (String) token.getString("expiration");
                            String secret = (String) token.getString("secret");
                            Toast.makeText(RequestAPI.this, expiration + "\n" + secret, Toast.LENGTH_LONG).show();
                            */
                            JSONObject result = new JSONObject(response).getJSONObject("data");
                            Toast.makeText(RequestAPI.this, result.toString(), Toast.LENGTH_LONG).show();

                            /*
                            int maxItems = result.getInt("end");
                            JSONArray resultList = result.getJSONArray("item");
                             */
                            // catch for the JSON parsing error
                        } catch (JSONException e) {
                            Toast.makeText(RequestAPI.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } // public void onResponse(String response)
                }, // Response.Listener<String>()
                new Response.ErrorListener() {
                    // 4th param - method onErrorResponse lays the code procedure of error return
                    // ERROR
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // display a simple message on the screen
                        Toast.makeText(RequestAPI.this, "Plant source is not responding (OpenFarm API)", Toast.LENGTH_LONG).show();
                    }
                })
        {
            /*
            protected Map<String, String> getHeaders() {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("email", "thijs.vandegriendt99@gmail.com");
                hashMap.put("password", "ObamaBidon");


                        Map<String, Object> MyData = new HashMap<String, Object>();
                        MyData.put("data", hashMap); //Add the data you'd like to send to the server.
                        return MyData;

                return hashMap;
            }

            */

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String>  params = new HashMap<String, String>();
            params.put("Authorization: Token token", "thijs.vandegriendt99@gmail.com:67ed627cb2d5e93fecbe4e7a4b5355d3");
            return params;
            }
        };
    }
}
