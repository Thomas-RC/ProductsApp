package com.rosdesign.productsapp;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
{
    Button buttonLogin, buttonRegister;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        * Pobranie wartości z widoku dla przycisków na podstawie ID
        * */
        buttonLogin = findViewById(R.id.button5);
        buttonRegister = findViewById(R.id.button6);

        /*
        * Wywołanie akcji LoginActivity dla kliknięcia przycisku id: button5
        * */
        buttonLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        /*
         * Wywołanie akcji RegisterActivity dla kliknięcia przycisku id: button6
         * */
        buttonRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        /*
        * Pobranie wartości z widoku dla menu dolnego
        * */
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.page_main);

        /*
        * Wywołanie metody uruchamiajacej menu dolne
        * */
        setBottomNavigationView();
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