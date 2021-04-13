package com.example.recipeholder;

public class Ingredient {

    private String name;
    private String amount;


    public Ingredient(String name, String amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return this.name + "  " + this.amount;
    }
}
