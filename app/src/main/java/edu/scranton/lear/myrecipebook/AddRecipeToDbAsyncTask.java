package edu.scranton.lear.myrecipebook;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class AddRecipeToDbAsyncTask extends AsyncTask<Void, Void, AddRecipeBeginningFragmentHelper> {

    private WeakReference<MainActivity> mWeakRefActivity;
    private SQLiteDatabase mWritableDb;
    private AddRecipeBeginningFragmentHelper mAddRecipeBeginningFragmentHelper;

    public AddRecipeToDbAsyncTask(MainActivity mainActivity, SQLiteDatabase writableDb, AddRecipeBeginningFragmentHelper addRecipeBeginningFragmentHelper) {
            this.mWeakRefActivity = new WeakReference<>(mainActivity);
            this.mWritableDb = writableDb;
            this.mAddRecipeBeginningFragmentHelper = addRecipeBeginningFragmentHelper;
    }

    @Override
    protected AddRecipeBeginningFragmentHelper doInBackground(Void...params){
        AddRecipeBeginningFragmentHelper addRecipeBeginningFragmentHelper = mAddRecipeBeginningFragmentHelper;
        ArrayList<String> recipeInstructions = addRecipeBeginningFragmentHelper.getInstructions();
        ArrayList<String> recipeIngredients = addRecipeBeginningFragmentHelper.getIngredients();
        String recipeName = addRecipeBeginningFragmentHelper.getRecipeName();
        String recipeStyle = addRecipeBeginningFragmentHelper.getRecipeStyle();
        String recipeType = addRecipeBeginningFragmentHelper.getRecipeType();

        ContentValues recipe = new ContentValues();
        recipe.put(RecipeBookContract.Recipes.COLUMN_NAME_NAME,recipeName);
        recipe.put(RecipeBookContract.Recipes.COLUMN_NAME_STYLE,recipeStyle);
        recipe.put(RecipeBookContract.Recipes.COLUMN_NAME_TYPE,recipeType);
        long recipeId = mWritableDb.insert(RecipeBookContract.Recipes.TABLE_NAME, null, recipe);

        for (int i = 0; i < recipeInstructions.size();i++){
            ContentValues steps = new ContentValues();
            steps.put(RecipeBookContract.Steps.COLUMN_NAME_RECIPEID,recipeId);
            steps.put(RecipeBookContract.Steps.COLUMN_NAME_INSTRUCTION,recipeInstructions.get(i));
            //put picture
            mWritableDb.insert(RecipeBookContract.Steps.TABLE_NAME, null, steps);
        }

        for (int j = 0;j < recipeIngredients.size();j++){
            ContentValues ingredients = new ContentValues();
            ingredients.put(RecipeBookContract.Ingredients.COLUMN_NAME_RECIPEID,recipeId);
            ingredients.put(RecipeBookContract.Ingredients.COLUMN_NAME_INGREDIENT,recipeIngredients.get(j));
            mWritableDb.insert(RecipeBookContract.Ingredients.TABLE_NAME,null,ingredients);
        }

        return addRecipeBeginningFragmentHelper;
    }




    @Override
    protected void onPostExecute(AddRecipeBeginningFragmentHelper addRecipeBeginningFragmentHelper) {
        super.onPostExecute(addRecipeBeginningFragmentHelper);
        MainActivity mainActivity = mWeakRefActivity.get();
        if (mainActivity == null)return;
        mainActivity.resetMenu();
    }

}



