package com.example.recipeholder.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.recipeholder.Recipe;
import com.example.recipeholder.database.RecipeDBSchema.RecipeTable;

import java.util.Date;
import java.util.UUID;

public class RecipeCursorWrapper extends CursorWrapper {

    public RecipeCursorWrapper(Cursor cursor) {
        super(cursor);

    }

    public Recipe getRecipe () {

        String uuidString = getString(getColumnIndex(RecipeTable.Columns.UUID));
        String name = getString(getColumnIndex(RecipeTable.Columns.NAME));
        long date = getLong(getColumnIndex(RecipeTable.Columns.DATE));
        int isFavorite = getInt(getColumnIndex(RecipeTable.Columns.FAVORITE));

        Recipe recipe  = new Recipe(UUID.fromString(uuidString));
        recipe.setName(name);
        recipe.setDate(new Date(date));
        recipe.setFavorite(isFavorite != 0);

        return recipe;
    }

}
