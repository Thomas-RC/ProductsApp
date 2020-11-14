package com.rosdesign.productsapp;

public class Product
{
    private int id;
    private String name, desc, price, created_at, updated_at;


    public Product()
    {
    }

    public Product(String name, String desc)
    {
        this.name = name;
        this.desc = desc;
    }

    public Product(int id, String name, String desc, String price, String created_at, String updated_at)
    {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getCreated_at()
    {
        return created_at;
    }

    public void setCreated_at(String created_at)
    {
        this.created_at = created_at;
    }

    public String getUpdated_at()
    {
        return updated_at;
    }

    public void setUpdated_at(String updated_at)
    {
        this.updated_at = updated_at;
    }
}
