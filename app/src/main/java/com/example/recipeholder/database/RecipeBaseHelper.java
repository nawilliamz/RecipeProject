package com.example.recipeholder.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.recipeholder.database.RecipeDBSchema.RecipeTable;

public class RecipeBaseHelper extends SQLiteOpenHelper {

    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "recipeBase.db";

    public RecipeBaseHelper (Context context) {
        super(context,DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //This is essentially executing SQL code to create a new table
        db.execSQL("create table " + RecipeTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                RecipeTable.Columns.UUID + ", " +
                RecipeTable.Columns.NAME + ", " +
                RecipeTable.Columns.DATE + ", " +
                RecipeTable.Columns.FAVORITE +
                ")"

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
