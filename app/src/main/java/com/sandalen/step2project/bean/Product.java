package com.sandalen.step2project.bean;

import java.io.Serializable;

public class Product implements Serializable {
    protected int id;
    protected String name;
    protected String label;
    protected String description;
    protected float price;
    protected String icon;
    protected Restaurant restaurant ;

    public Product() {
    }

    public Product(String name, String label, String desc, float price, String url) {
        this.name = name;
        this.label = label;
        this.description = desc;
        this.price = price;
        this.icon = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getIcon() {
        return icon;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setIcon(String url) {
        this.icon = url;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", icon='" + icon + '\'' +
                ", restaurant=" + restaurant +
                '}';
    }
}
