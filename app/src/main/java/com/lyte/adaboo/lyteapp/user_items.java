package com.lyte.adaboo.lyteapp;

/**
 * Created by adaboo on 5/16/17.
 */

public class user_items {

    String img;
    String product;
    String price;
    String qnty;

    public user_items(String product,String price,String qnty, String img) {
        // TODO Auto-generated constructor stub
        this.product = product;
        this.price = price;
        this.qnty = qnty;
        this.img = img;
    }


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQnty() {
        return qnty;
    }

    public void setQnty(String qnty) {
        this.qnty = qnty;
    }
}
