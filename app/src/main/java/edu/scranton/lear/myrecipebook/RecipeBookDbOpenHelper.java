package edu.scranton.lear.myrecipebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class RecipeBookDbOpenHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "recipebook.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA = ", ";


    //Recipes(_ID, name , type, style)
    private static final String SQL_CREATE_RECIPES =
            "CREATE TABLE " + RecipeBookContract.Recipes.TABLE_NAME + "(" +
                    RecipeBookContract.Recipes._ID + " INTEGER PRIMARY KEY , " +
                    RecipeBookContract.Recipes.COLUMN_NAME_NAME + TEXT_TYPE + COMMA +
                    RecipeBookContract.Recipes.COLUMN_NAME_TYPE + TEXT_TYPE + COMMA +
                    RecipeBookContract.Recipes.COLUMN_NAME_STYLE + TEXT_TYPE +
                    ")";

    //Steps(_ID, RecipeId, instruction)
    private static final String SQL_CREATE_STEPS =
            "CREATE TABLE " + RecipeBookContract.Steps.TABLE_NAME + "(" +
                    RecipeBookContract.Steps._ID + " INTEGER PRIMARY KEY , " +
                    RecipeBookContract.Steps.COLUMN_NAME_RECIPEID + INTEGER_TYPE + COMMA +
                    RecipeBookContract.Steps.COLUMN_NAME_INSTRUCTION + TEXT_TYPE +
                    ")";

    //Ingredients(_ID, RecipeId, ingredient)
    private static final String SQL_CREATE_INGREDIENTS =
            "CREATE TABLE " + RecipeBookContract.Ingredients.TABLE_NAME + "(" +
                    RecipeBookContract.Ingredients._ID + " INTEGER PRIMARY KEY , " +
                    RecipeBookContract.Ingredients.COLUMN_NAME_RECIPEID + INTEGER_TYPE + COMMA +
                    RecipeBookContract.Ingredients.COLUMN_NAME_INGREDIENT + TEXT_TYPE +
                    ")";

    // compose the drop statements for tables
    private static final String SQL_DROP_RECIPES =
            "DROP TABLE IF EXISTS " + RecipeBookContract.Recipes.TABLE_NAME;
    private static final String SQL_DROP_STEPS =
            "DROP TABLE IF EXISTS " + RecipeBookContract.Steps.TABLE_NAME;
    private static final String SQL_DROP_INGREDIENTS =
            "DROP TABLE IF EXISTS " + RecipeBookContract.Ingredients.TABLE_NAME;

    public RecipeBookDbOpenHelper(Context context) {
        // call the super constructor which will call the callbacks
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        // create all tables
        db.execSQL(SQL_CREATE_RECIPES);
        db.execSQL(SQL_CREATE_STEPS);
        db.execSQL(SQL_CREATE_INGREDIENTS);

        // initialize the database
        initialize(db);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // we simply drop all the tables and create them
        db.execSQL(SQL_DROP_RECIPES);
        db.execSQL(SQL_DROP_STEPS);
        db.execSQL(SQL_DROP_INGREDIENTS);

        onCreate(db);
    }

    private void initialize(SQLiteDatabase db) {

    }
}
