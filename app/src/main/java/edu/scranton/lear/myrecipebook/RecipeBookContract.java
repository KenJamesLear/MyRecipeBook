package edu.scranton.lear.myrecipebook;

import android.provider.BaseColumns;


public final class RecipeBookContract {
    public RecipeBookContract(){}

    //Recipes(_ID, style, type, name)
    public static abstract class Recipes implements BaseColumns {
        public static final String TABLE_NAME = "recipes";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_STYLE = "style";

    }

    //Steps(_ID, RecipeId, instruction)
    public static abstract class Steps implements BaseColumns {
        public static final String TABLE_NAME = "steps";
        public static final String COLUMN_NAME_RECIPEID = "recipeId";
        public static final String COLUMN_NAME_INSTRUCTION = "instruction";
    }

    //Ingredients(_ID, RecipeId, ingredient)
    public static abstract class Ingredients implements BaseColumns {
        public static final String TABLE_NAME = "ingredients";
        public static final String COLUMN_NAME_RECIPEID = "recipeId";
        public static final String COLUMN_NAME_INGREDIENT = "ingredient";
    }

}




