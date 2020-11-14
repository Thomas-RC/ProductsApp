package com.rosdesign.productsapp;


import android.content.Intent;
import android.util.Log;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends Base
{
    String token;
    private List<Product> productList;
    private RecyclerView recyclerView;
    public static CustomAdapter adapter;

    BottomNavigationView bottomNavigationViewHome;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (Session.getInstance(this).isLoggedIn())
        {

            if (Session.getInstance(this).getToken() != null)
            {
                token = Session.getInstance(this).getToken().getToken();
            }
        }
        else
        {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }


        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();
        productList = productsData();
        adapter = new CustomAdapter(this, productList);
        recyclerView.setAdapter(adapter);

        bottomNavigationViewHome = findViewById(R.id.bottom_navigation_home);
        bottomNavigationViewHome.setSelectedItemId(R.id.page_main_home);

        /*
         * Wywołanie metody menu dolnego
         * */
        setBottomNavigationViewHome();
    }


    public List<Product> productsData()
    {
        productList.clear();

        /* Wywołanie request do API zgodnie z dokumentacją Android:
         * https://developer.android.com/training/volley/simple
         */
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, ConfigsApp.PRODUCTS, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject r)
            {
                /*
                 * Tworzę logi w logcat do sprawdzenia poprawności zwracanych danych
                 */
                Log.d("Moje 2", String.valueOf(r));

                /*
                 * RestAPI zwraca dane w postaci JSON
                 * Parametr success ma wartośc TRUE lub FALSE
                 * Zatem zgodnie z dokumentacją Android wartosci bool pobieramy: public boolean getBoolean (String name)
                 * https://developer.android.com/reference/org/json/JSONObject#getBoolean(java.lang.String)
                 * Zwracamy Obiekt Toast w razie powodzenia lub porażki
                 */
                try
                {
                    JSONArray productsArray = r.getJSONArray("data");

                    for (int i = 0; i < productsArray.length(); i++)
                    {
                        JSONObject productObj = productsArray.getJSONObject(i);
                        Product product = new Product();
                        product.setId(productObj.getInt("id"));
                        product.setName(productObj.getString("name"));
                        product.setDesc(productObj.getString("description"));
                        product.setPrice(productObj.getString("price"));
                        product.setCreated_at(productObj.getString("created_at"));

                        productList.add(product);
                        adapter.notifyDataSetChanged();

                    }

                } catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener()
        {
            /**
             * Obsługa błędów z klasy nadrzędnej Base
             * */
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d("error:", String.valueOf(error));
            }
        })
        {
            public Map<String, String> getHeaders()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer  " + token);
                return params;
            }
        };

        /*
         * Wysłanie rządania: https://developer.android.com/training/volley/simple#send
         * */
        RequestQueue rQueue = Volley.newRequestQueue(HomeActivity.this);
        rQueue.add(request);

        return productList;
    }

    /**
     * Metoda obsługujaca menu dolne
     */
    public void setBottomNavigationViewHome()
    {
        bottomNavigationViewHome.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.page_logout_home:
                        startActivity(new Intent(getApplicationContext(), LogoutActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_main_home:
                        return true;
                    case R.id.page_info_home:
                        startActivity(new Intent(getApplicationContext(), InfoActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}