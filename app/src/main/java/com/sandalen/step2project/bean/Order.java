package com.sandalen.step2project.bean;

import com.sandalen.step2project.ui.vo.ProductItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order implements Serializable{
    public static class ProductVo implements Serializable {
        public Product product;
        public int count;
    }

    private int id;
    private Date date;
    private List<Product> products = new ArrayList<>();
    public Map<Product,Integer> productsMap = new HashMap<>();
    public List<ProductVo> ps;
    private Restaurant restaurant;
    private int count;
    private float price;

    public void addProduct(ProductItem product){
        Integer count = productsMap.get(product);
        if (count == null || count <= 0) {
            productsMap.put(product, 1);
        } else {
            productsMap.put(product, count + 1);
        }
    }

    public void removeProduct(ProductItem product) {
        Integer count = productsMap.get(product);
        if (count == null || count <= 0) {
            return;
        } else {
            productsMap.put(product, count - 1);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Map<Product, Integer> getProductsMap() {
        return productsMap;
    }

    public void setProductsMap(Map<Product, Integer> productsMap) {
        this.productsMap = productsMap;
    }

    public List<ProductVo> getPs() {
        return ps;
    }

    public void setPs(List<ProductVo> ps) {
        this.ps = ps;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", products=" + products +
                ", productsMap=" + productsMap +
                ", ps=" + ps +
                ", restaurant=" + restaurant +
                ", count=" + count +
                ", price=" + price +
                '}';
    }
}
