package com.example.wetmyplants;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.zip.Deflater;

public class RequestAPI extends AppCompatActivity {
    private RequestQueue queue;
    private SharedPreferences sp2;
    private Button getButton, homeButton, addButton, deleteButton, myGarden;
    private TextView getName, getFamily, getGenus, getNutritions, getCarbohydrates, getProtein, getFat, getCalories, getSugar;
    private EditText searchItem;
    private ArrayList<String> list;
    int itemsAdded = 0;

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
        addButton = (Button) findViewById(R.id.button11);
        deleteButton = (Button) findViewById(R.id.button13);
        searchItem = (EditText) findViewById(R.id.editText1);
        myGarden = (Button) findViewById(R.id.button14);

        List<String> fruits = Arrays.asList("Apple", "Apricot", "Banana", "Blueberry", "Cherry", "Guava", "Lemon", "Mango", "Orange", "Pear", "Pineapple", "Raspberry", "Strawberry", "Tomato", "Watermelon");
        list = new ArrayList<>();
        list.addAll(fruits);
        sp2 = getSharedPreferences("MyFruits", Context.MODE_PRIVATE);
        setItemsAdded();

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

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredFruit = searchItem.getText().toString();
                if(list.contains(enteredFruit) ){
                    SharedPreferences.Editor editor2 = sp2.edit();
                    editor2.putString("fruit"+String.valueOf(itemsAdded), enteredFruit);
                    editor2.commit();
                    itemsAdded++;
                    editor2.putInt("itemsAdded", itemsAdded);
                    editor2.commit();
                    Toast.makeText(RequestAPI.this, enteredFruit+" has been added to your personal page!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RequestAPI.this, enteredFruit+" not available or already added!", Toast.LENGTH_LONG).show();
                }
            }
        });

        myGarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestAPI.this, MyGarden.class);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredFruit = searchItem.getText().toString();
                if(itemsAdded > 0 && list.contains(enteredFruit)){
                    SharedPreferences.Editor editor3 = sp2.edit();
                    editor3.remove("fruit"+String.valueOf(itemsAdded));
                    editor3.commit();
                    itemsAdded--;
                    editor3.putInt("itemsAdded", itemsAdded);
                    editor3.commit();
                    Toast.makeText(RequestAPI.this, "Item has been deleted!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RequestAPI.this, "This item hasn't been saved!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setItemsAdded(){
        SharedPreferences sp5 = getApplicationContext().getSharedPreferences("MyFruits", Context.MODE_PRIVATE);
        if(sp5.getInt("itemsAdded", 0) > 0){
            itemsAdded = sp5.getInt("itemsAdded", 0);
        }
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
}
