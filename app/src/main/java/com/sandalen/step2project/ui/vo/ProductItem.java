package com.sandalen.step2project.ui.vo;

import com.sandalen.step2project.bean.Product;

import java.io.Serializable;

public class ProductItem extends Product implements Serializable {
    public int count;

    public ProductItem(Product product){
        this.id = product.getId();
        this.name = product.getName();
        this.label = product.getLabel();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.icon = product.getIcon();
        this.restaurant = product.getRestaurant();
    }
}
