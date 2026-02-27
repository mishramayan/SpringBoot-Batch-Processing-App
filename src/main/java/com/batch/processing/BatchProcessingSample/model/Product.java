package com.batch.processing.BatchProcessingSample.model;

public class Product {
    private int product_id;
    private String title;
    private String description;
    private double price;
    private double discount;
    private double discounted_price;

    public Product(int product_id, String title, String description, double price, double discount, double discounted_price) {
        this.product_id = product_id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.discounted_price = discounted_price;
    }

    public Product(){

    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscounted_price() {
        return discounted_price;
    }

    public void setDiscounted_price(double discounted_price) {
        this.discounted_price = discounted_price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
