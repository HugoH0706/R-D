package com.example.wetmyplants;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestAPI extends AppCompatActivity {
    private RequestQueue queue;
    private Button getButton, homeButton;
    private TextView getName, getFamily, getGenus, getNutritions, getCarbohydrates, getProtein, getFat, getCalories, getSugar;
    private EditText searchItem;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_api);

        getFamily = findViewById(R.id.textViewFamily);
        getGenus = findViewById(R.id.textViewGenus);
        getNutritions = findViewById(R.id.textViewNutritions);
        getCarbohydrates = findViewById(R.id.textViewCarboHydrates);
        getCalories = findViewById(R.id.textViewCalories);
        getProtein = findViewById(R.id.textViewProtein);
        getFat = findViewById(R.id.textViewFat);
        getSugar = findViewById(R.id.textViewSugar);

        getName = findViewById(R.id.textViewName);
        queue = Volley.newRequestQueue(this);
        getButton = (Button)findViewById(R.id.button9);
        homeButton = (Button) findViewById(R.id.button10);
        searchItem = (EditText) findViewById(R.id.editText1);

        List<String> fruits = Arrays.asList("Apple", "Apricot", "Banana", "Blueberry", "Cherry", "Guava", "Lemon", "Mango", "Orange", "Pear", "Pineapple", "Raspberry", "Strawberry", "Tomato", "Watermelon");
        list = new ArrayList<>();
        list.addAll(fruits);

        getButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                String enteredFruit = searchItem.getText().toString();
                if(list.contains(enteredFruit)){
                    jsonParse(enteredFruit);
                } else if(enteredFruit.equals("all")){
                    Toast.makeText(RequestAPI.this, "Available fruits: \n"+showList(list), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RequestAPI.this, "Input not found... (to see the possible options enter: all)", Toast.LENGTH_LONG).show();
                }
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

    }

    private StringBuilder showList(ArrayList<String> fruitList){
        StringBuilder allFruits = new StringBuilder();
        for(String s : fruitList){
            allFruits.append(s).append(", ");
        }
        allFruits.delete(allFruits.length()-2, allFruits.length());
        allFruits.append(".");
        return allFruits;
    }

    private void jsonParse(String crop){
        String url = "https://www.fruityvice.com/api/fruit/"+crop;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                            try {
                                String name = response.getString("name");
                                String genus = response.getString("genus");
                                String family = response.getString("family");
                                getName.setText("Name = " + name);
                                getGenus.setText("Genus = " + genus);
                                getFamily.setText("Family = " + family);

                                JSONObject nutritionObject = response.getJSONObject("nutritions");
                                double carbohydrates = nutritionObject.getDouble("carbohydrates");
                                double protein = nutritionObject.getDouble("protein");
                                double fat = nutritionObject.getDouble("fat");
                                double calories = nutritionObject.getDouble("calories");
                                double sugar = nutritionObject.getDouble("sugar");
                                getCarbohydrates.setText("Carbohydrates: " + String.valueOf(carbohydrates));
                                getProtein.setText("Protein: " + String.valueOf(protein));
                                getFat.setText("Fat: " + String.valueOf(fat));
                                getCalories.setText("Calories: " + String.valueOf(calories));
                                getSugar.setText("Sugar: " + String.valueOf(sugar));
                            } catch (JSONException e) {
                                Toast.makeText(RequestAPI.this, "Wrong input", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RequestAPI.this, "Error"+error, Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        queue.add(request);
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
                Toast.makeText(RequestAPI.this, "TESTTTT", Toast.LENGTH_LONG).show();
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization: Token token", "thijs.vandegriendt99@gmail.com:67ed627cb2d5e93fecbe4e7a4b5355d3");
                return params;
            }
           // Toast.makeText(RequestAPI.this, "TESSST", Toast.LENGTH_LONG).show();
        };
    }
}
