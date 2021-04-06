/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.dtos;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author DELL
 */
public class CartShopping {

    private String customerName;
    private HashMap<String, String> cart;

    public CartShopping() {
        this.customerName = "Guest";
        this.cart = new HashMap<>();
    }

    public CartShopping(String customerName) {
        this.customerName = customerName;
        this.cart = new HashMap<>();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public HashMap<String, String> getCart() {
        return cart;
    }

    public boolean addCart(String answerId, String answerSelect, List<String> listAnswerIdInRange) throws Exception {
        for (String answer : listAnswerIdInRange) {
            if (this.cart.containsKey(answer)) {
                remove(answer);
            }
        }
        this.cart.put(answerId, answerSelect);
        return true;
    }

    public void setCart(HashMap<String, String> cart) {
        this.cart = cart;
    }

    public boolean remove(String answerId) throws Exception {
        if (this.cart.containsKey(answerId)) {
            this.cart.remove(answerId);
            return true;
        }
        return false;
    }

}
