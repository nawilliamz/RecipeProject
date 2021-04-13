package com.example.recipeholder;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Recipe {

    private UUID mID;
    private String mName;
    private Date mDate;
    private boolean mIsFavorite;

    private final ArrayList<Ingredient> ingredients;
    public final ArrayList<Instruction> instructions;

//    int textResID;
//    boolean isAdded;



    public Recipe(UUID id) {
        mID = id;
        mDate = new Date();
        this.ingredients = new ArrayList<>();
        this.instructions = new ArrayList<>();
    }


    //Use of this constructor forms new instance of recipe and gives it a new UUID
    public Recipe() {
        this(UUID.randomUUID());
    }

    public UUID getID() {
        return mID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isFavorite() {
        return mIsFavorite;
    }

    public void setFavorite(boolean favorite) {
        mIsFavorite = favorite;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<Instruction> getInstructions() {
        return instructions;
    }

    //    @NonNull
//    @Override
//    public String toString() {
//        return name;
//    }
}
