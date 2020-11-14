package com.rosdesign.productsapp;

/**
 * Tworzenie modelu uzytkownika zgodnego z danymi z restAPI
 */
public class User
{

    private String name, token;

    public User(String token)
    {
        this.token = token;
    }

    public User(String name, String token)
    {
        this.name = name;
        this.token = token;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }
}
