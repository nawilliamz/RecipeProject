package com.example.recipeholder.database;

public class RecipeDBSchema {

    //RecipeTable class exists to define the String constants needed to describe the recipe table
    public static final class RecipeTable {

        public static final String NAME = "recipes";

        public static final class Columns {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String DATE = "date";
            public static final String FAVORITE = "favorite";


        }


    }
    
}
