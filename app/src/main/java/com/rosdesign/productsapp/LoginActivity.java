package com.rosdesign.productsapp;

import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class LoginActivity extends Base
{

    /*
    * Deklaracja zmiennych dla pól i przycisku formularza oraz menu dolnego.
    * */
    TextInputLayout layoutEmail, layoutPassword;
    TextInputEditText textEmail, textPassword;
    Button loginButton;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*
        * Inicjalizacja zmiennych dla pól i przycisku formularza oraz menu dolnego
        * */
        layoutEmail = findViewById(R.id.textInputLayoutEmailLogin);
        layoutPassword = findViewById(R.id.textInputLayoutPasswordLogin);
        textEmail = findViewById(R.id.text_email_login);
        textPassword = findViewById(R.id.text_password_login);
        loginButton = findViewById(R.id.button_login);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.page_login);

        /*
         * Wywołanie metody menu dolnego
         * */
        setBottomNavigationView();

        /*
        * Wywołanie metody logowania
        * */
        loginApp();

    }

    /**
     * Metoda wykorzystuje bibliotekę Volley. Volley to biblioteka HTTP, która ułatwia i, co najważniejsze, przyspiesza tworzenie sieci dla aplikacji na Androida.
     */
    public void loginApp()
    {
        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                /* Wywołanie request do API zgodnie z dokumentacją Android:
                 * https://developer.android.com/training/volley/simple
                 */
                StringRequest request = new StringRequest(Request.Method.POST, ConfigsApp.LOGIN, new Response.Listener<String>()
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
                                Toast.makeText(LoginActivity.this, "Zalogowano poprawnie", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            } else
                            {
                                Toast.makeText(LoginActivity.this, "Niepoprawne dane logowania", Toast.LENGTH_LONG).show();
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
                        String[] arrInput = {"email","password","error"};
                        TextInputLayout[] editText = {layoutEmail, layoutPassword};
                        LoginActivity.this.networkErrorResponse(error, arrInput, editText, LoginActivity.this);
                    }
                })
                {
                    /**
                     * Nadpisanie metody getParams klasy StringRequest w celu zmapowania argumentów i nagłówków z formularza
                     */
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError
                    {
                        Map<String, String> parameters = new HashMap<>();
                        parameters.put("Content-Type", "application/json");
                        parameters.put("email", textEmail.getText().toString());
                        parameters.put("password", textPassword.getText().toString());
                        Log.d("getParams", String.valueOf(parameters));
                        return parameters;
                    }
                };

                /*
                * Wysłanie rządania: https://developer.android.com/training/volley/simple#send
                * */
                RequestQueue rQueue = Volley.newRequestQueue(LoginActivity.this);
                rQueue.add(request);
            }
        });
    }

    /**
     * Metoda obsługujaca menu dolne
     */
    public void setBottomNavigationView()
    {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.page_login:
                        return true;
                    case R.id.page_main:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_register:
                        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}