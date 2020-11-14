package com.rosdesign.productsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.util.Log;

/**
 * zapisanie zmiennych z wykorzystaniem SharedPreferences: https://developer.android.com/training/data-storage/shared-preferences
 * Pozwala na zapisanie danych klucz wartość, przez co moga byc dostepne w całym systemie
 */
public class Session
{

    private static final String PREF_NAME = "usersToken";
    private static final String KEY_NAME = "name";
    private static final String KEY_TOKEN = "token";


    private static Session mInstance;
    private static Context mCtx;

    public Session(Context context)
    {
        mCtx = context;
    }

    /**
     *
     * @param context
     * @return
     */
    public static Session getInstance(Context context)
    {
        if (mInstance == null)
        {
            mInstance = new Session(context);
        }
        return mInstance;
    }

    /**
     * Pobranie danych do sesji zalogowanego uzytkownika
     * @param user
     */
    public void userLogin(User user)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_TOKEN, user.getToken());
        editor.apply();
    }

    /**
     * Pobranie danych do sesji czy uzytkownik jest zalogowany
     * @return
     */
    public boolean isLoggedIn()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_TOKEN, null) != null;
    }
    /**
     * Pobranie nazwy uzytkownika do sesji
     * @return
     */
    public String getName()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAME, null);
    }

    /**
     * Pobranie wartości tokena do sesji
     * @return
     */
    public User getToken()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(KEY_TOKEN, null)
        );
    }

    /**
     * Usunięcie sesji i wylogowanie uzytkownika
     */
    public void userLogout()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


}