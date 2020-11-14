package com.rosdesign.productsapp;

import android.content.Intent;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class InfoActivity extends AppCompatActivity
{
    BottomNavigationView bottomNavigationViewHome;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        bottomNavigationViewHome = findViewById(R.id.bottom_navigation_home);
        bottomNavigationViewHome.setSelectedItemId(R.id.page_info_home);

        /*
         * Wywołanie metody menu dolnego
         * */
        setBottomNavigationViewHome();
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