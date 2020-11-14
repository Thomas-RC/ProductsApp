package com.rosdesign.productsapp;

import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import android.os.Bundle;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InfoActivity extends Base
{
    Button productAddButton;
    TextInputLayout layoutName, layoutPrice, layoutDesc;
    TextInputEditText textName, textPrice, textDesc;
    BottomNavigationView bottomNavigationViewHome;

    String token;
    TextView userTextName;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


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


        /**
         * Deklaracja zmiennych z widoku
         */
        layoutName = findViewById(R.id.textInputLayoutNameProduct);
        layoutPrice = findViewById(R.id.textInputLayoutpriceProduct);
        layoutDesc = findViewById(R.id.textInputLayoutPasswordRegister);
        productAddButton = findViewById(R.id.button_add_product);

        textName = findViewById(R.id.text_name_product);
        textPrice = findViewById(R.id.text_price_product);
        textDesc = findViewById(R.id.text_desc_product);

        bottomNavigationViewHome = findViewById(R.id.bottom_navigation_home);
        bottomNavigationViewHome.setSelectedItemId(R.id.page_info_home);

        /*
         * Wywołanie metody menu dolnego
         * */
        setBottomNavigationViewHome();

        productAdd();
    }

    public void productAdd()
    {
        productAddButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                /* Wywołanie request do API zgodnie z dokumentacją Android:
                 * https://developer.android.com/training/volley/simple
                 */
                StringRequest request = new StringRequest(Request.Method.POST, ConfigsApp.PRODUCTS, new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String s)
                    {
                        /*
                         * Tworzę logi w logcat do sprawdzenia poprawności wprowadzanych danych
                         */
                        Log.d("Moje string", s);

                        /*
                         * RestAPI zwraca dane w postaci JSON
                         * Parametr success ma wartośc TRUE lub FALSE
                         * Zatem zgodnie z dokumentacją Android wartosci bool pobieramy: public boolean getBoolean (String name)
                         * https://developer.android.com/reference/org/json/JSONObject#getBoolean(java.lang.String)
                         * Zwracamy Obiekt Toast w razie powodzenia lub porażki
                         */
                        try
                        {
                            JSONObject obj = new JSONObject(s);
                            if (obj.getBoolean("success"))
                            {
                                Toast.makeText(InfoActivity.this, "Dodano produkt poprawnie", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(InfoActivity.this, HomeActivity.class));
                            } else
                            {
                                Toast.makeText(InfoActivity.this, "Niepoprawne dane", Toast.LENGTH_LONG).show();
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
                        String[] arrInput = {"name","price","description"};
                        TextInputLayout[] editText = {layoutName, layoutPrice, layoutDesc};
                        InfoActivity.this.networkErrorResponse(error, arrInput, editText, InfoActivity.this);
                    }
                })
                {
                    /**
                     * Nadpisanie metody getParams klasy StringRequest w celu zmapowania argumentów i nagłówków z formularza
                     */
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError
                    {
                        Map<String, String> params = new HashMap<>();
                        params.put("Content-Type", "application/json");
                        params.put("name", textName.getText().toString());
                        params.put("price", textPrice.getText().toString());
                        params.put("description", textDesc.getText().toString());
                        Log.d("getParams", String.valueOf(params));
                        return params;
                    }
                    /**
                     * przekazanie nagłowków przy zapytaniu o produkty z API z wykorzystaniem tokena
                     * @return
                     */
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
                RequestQueue rQueue = Volley.newRequestQueue(InfoActivity.this);
                rQueue.add(request);
            }
        });
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
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_info_home:
                        return true;
                }
                return false;
            }
        });
    }
}