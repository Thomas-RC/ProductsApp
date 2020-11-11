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

public class RegisterActivity extends Base
{
    TextInputLayout layoutName, layoutEmail, layoutPassword, layoutConfirmPassword;
    TextInputEditText textName, textEmail, textPassword, textConfirmPassword;
    Button registerButton;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        layoutName = findViewById(R.id.textInputLayoutNameRegister);
        layoutEmail = findViewById(R.id.textInputLayoutEmailRegister);
        layoutPassword = findViewById(R.id.textInputLayoutPasswordRegister);
        layoutConfirmPassword = findViewById(R.id.textInputLayoutConfirmPasswordRegister);
        registerButton = findViewById(R.id.button_register);

        textName = findViewById(R.id.text_name_register);
        textEmail = findViewById(R.id.text_email_register);
        textPassword = findViewById(R.id.text_password_register);
        textConfirmPassword = findViewById(R.id.text_confirm_password_register);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.page_register);

        setBottomNavigationView();
        registerApp();
    }

    public void registerApp()
    {
        registerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                /* Wywołanie request do API zgodnie z dokumentacją Android:
                 * https://developer.android.com/training/volley/simple
                 */
                StringRequest request = new StringRequest(Request.Method.POST, ConfigsApp.REGISTER, new Response.Listener<String>()
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
                                Toast.makeText(RegisterActivity.this, "Zarejestrowano poprawnie", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            } else
                            {
                                Toast.makeText(RegisterActivity.this, "Niepoprawne dane", Toast.LENGTH_LONG).show();
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
                        String[] arrInput = {"name","email","password","c_password"};
                        TextInputLayout[] editText = {layoutName, layoutEmail, layoutPassword, layoutConfirmPassword};
                        RegisterActivity.this.networkErrorResponse(error, arrInput, editText, RegisterActivity.this);
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
                        params.put("email", textEmail.getText().toString());
                        params.put("password", textPassword.getText().toString());
                        params.put("c_password", textConfirmPassword.getText().toString());
                        Log.d("getParams", String.valueOf(params));
                        return params;
                    }

                };

                /*
                 * Wysłanie rządania: https://developer.android.com/training/volley/simple#send
                 * */
                RequestQueue rQueue = Volley.newRequestQueue(RegisterActivity.this);
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
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_main:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_register:
                        return true;
                }
                return false;
            }
        });
    }
}