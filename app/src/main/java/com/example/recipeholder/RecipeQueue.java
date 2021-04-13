package com.example.recipeholder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.recipeholder.database.RecipeBaseHelper;
import com.example.recipeholder.database.RecipeCursorWrapper;
import com.example.recipeholder.database.RecipeDBSchema.RecipeTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecipeQueue {

    private static RecipeQueue sRecipeQueue;

//    private List<Recipe> mRecipes;

    //These two variables part of database code
    private Context mContext;
    private SQLiteDatabase mDatabase;


    private RecipeQueue(Context context) {

//        mRecipes = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            Recipe recipe = new Recipe();
//            recipe.setName("Recipe #" + i);
//            recipe.setFavorite(i % 2 == 0);
//            mRecipes.add(recipe);
//        }

        //The following code is part of database code
        //context.tetApplicationContext() returns the context of the single, global Application object
        //of the current process
        //context is stored in the instance variable because the variable will be used in upcoming
        //chapter
        mContext = context.getApplicationContext();
        //mContext is, therefore, the context with which the new database is opened
        mDatabase = new RecipeBaseHelper(mContext).getWritableDatabase();
        //Note: The database is created in RecipeQueue constructor so that it is available as soon
        //as the singleton is created
    }

    public static RecipeQueue get(Context context) {
        if (sRecipeQueue == null) {
            sRecipeQueue = new RecipeQueue(context);
            //You will make use of this context object in Chapter 14
        }
        return sRecipeQueue;

    }

    public void addRecipe (Recipe r) {
//        mRecipes.add(r);
        //getContentValues() is defined below
        ContentValues values = getContentValues(r);

        //This code results in
        mDatabase.insert(RecipeTable.NAME, null, values);

    }

    public List<Recipe> getRecipes() {
//        return mRecipes;
//        return new ArrayList<>();

        //Need to change our code here to retrieve our Recipes list from my database
        List<Recipe> recipes = new ArrayList<>();

        RecipeCursorWrapper cursor = queryRecipes(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                //getRecipe() needs to be updated to retrieve the Recipe from my database
                recipes.add(cursor.getRecipe());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return recipes;

    }

    public Recipe getRecipe(UUID id) {
//        for (Recipe recipe : mRecipes) {
//            if (recipe.getID().equals(id)) {
//                return recipe;
//            }
//        }
        //Need to change this code to retrieves the first Recipe in our database. The other Recipes,
        //if there are any, will be retrieved by the getRecipes() method above
        RecipeCursorWrapper cursor = queryRecipes(
                RecipeTable.Columns.UUID + " = ?",
                new String[] {id.toString()}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            //Note: this getRecipe() method is from RecipeCursorWrapper. It pulls a Recipe out of
            //the database using the Cursor object
            return cursor.getRecipe();
        } finally {
            cursor.close();
        }

    }


    private static ContentValues getContentValues(Recipe recipe) {
        ContentValues values = new ContentValues();
        values.put(RecipeTable.Columns.UUID, recipe.getID().toString());
        values.put(RecipeTable.Columns.NAME, recipe.getName());
        values.put(RecipeTable.Columns.DATE, recipe.getDate().getTime());
        values.put(RecipeTable.Columns.FAVORITE, recipe.isFavorite() ? 1 : 0);

        return values;
    }

    //This method was added
    public void updateRecipe(Recipe recipe) {
        String uuidString = recipe.getID().toString();
        ContentValues values = getContentValues(recipe);

        mDatabase.update(RecipeTable.NAME, values,
                RecipeTable.Columns.UUID + " = ?",
                new String[] { uuidString });
    }

    private RecipeCursorWrapper queryRecipes(String whereClause, String[] whereArgs) {
//    private Cursor queryRecipes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                RecipeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new RecipeCursorWrapper(cursor);
    }
}
