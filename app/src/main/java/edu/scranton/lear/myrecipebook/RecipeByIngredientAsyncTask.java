package edu.scranton.lear.myrecipebook;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by teddylear on 5/19/2016.
 */
public class RecipeByIngredientAsyncTask extends AsyncTask<Void, Void, ArrayList<String>> {

    private WeakReference<MainActivity> mWeakRefActivity;
    private SQLiteDatabase mReadOnlyDb;
    private ArrayList<String> mIngredients;


    public RecipeByIngredientAsyncTask(MainActivity mainActivity, SQLiteDatabase readOnlyDb, ArrayList<String> ingredients) {
        this.mIngredients = new ArrayList<>();
        this.mWeakRefActivity = new WeakReference<>(mainActivity);
        this.mReadOnlyDb = readOnlyDb;
        this.mIngredients = ingredients;

    }

    @Override
    protected ArrayList<String> doInBackground(Void...params) {

        int i = 0;
        int end = mIngredients.size();
        Log.d("CHECKIDS", "END = " + end);
        ArrayList<Integer> recipeIds = new ArrayList<>();
        for(i = 0;i<(end-1);i++){
            ArrayList<Integer> recipeIdsOne = new ArrayList<>();
            String[] projection = {
                    RecipeBookContract.Ingredients.COLUMN_NAME_RECIPEID
            };
            String selection =  RecipeBookContract.Ingredients.COLUMN_NAME_INGREDIENT + " =?";
            String[] selectionArgs = {mIngredients.get(i)};

            Cursor c = null;
            try {
                c = mReadOnlyDb.query(
                        RecipeBookContract.Ingredients.TABLE_NAME,          // table name
                        projection,                   // The columns to return
                        selection,                    // The columns for the WHERE clause
                        selectionArgs,                // The values for the WHERE clause
                        null,                         // don't group the rows
                        null,                         // don't filter by row groups
                        null                     // The sort order
                );

                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    recipeIdsOne.add(c.getInt(0));
                }
                // get data out of cursor, i.e.,
                // firstName = cursor.getString(1);
            } catch (Exception e) {
                Log.d("ERROR", "Error in getting recipe Id");

                // handle all exceptions as needed
            } finally {     // this guarantees the cursor is closed.
                if (c != null) {
                    c.close();
                }
            }

            for (int k = 0;k<recipeIdsOne.size();k++){
                Log.d("CHECKIDS", recipeIdsOne.get(k) + "");
            }

            ArrayList<Integer> recipeIdsTwo = new ArrayList<>();
            String[] projectionTwo = {
                    RecipeBookContract.Ingredients.COLUMN_NAME_RECIPEID
            };
            String selectionTwo =  RecipeBookContract.Ingredients.COLUMN_NAME_INGREDIENT + " =?";
            String[] selectionArgsTwo = {mIngredients.get(i+1)};

            c = null;
            try {
                c = mReadOnlyDb.query(
                        RecipeBookContract.Ingredients.TABLE_NAME,          // table name
                        projectionTwo,                   // The columns to return
                        selectionTwo,                    // The columns for the WHERE clause
                        selectionArgsTwo,                // The values for the WHERE clause
                        null,                         // don't group the rows
                        null,                         // don't filter by row groups
                        null                     // The sort order
                );

                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    recipeIdsTwo.add(c.getInt(0));
                }
                // get data out of cursor, i.e.,
                // firstName = cursor.getString(1);
            } catch (Exception e) {
                Log.d("ERROR", "Error in getting recipe Id");

                // handle all exceptions as needed
            } finally {     // this guarantees the cursor is closed.
                if (c != null) {
                    c.close();
                }
            }

            for (int n = 0;n<recipeIdsTwo.size();n++){
                Log.d("CHECKIDS", recipeIdsTwo.get(n) + "");
            }

            recipeIdsOne.retainAll(recipeIdsTwo);
            for (int j = 0; j < recipeIdsOne.size();j++)
                recipeIds.add(recipeIdsOne.get(j));

        }

        HashSet<Integer> hs = new HashSet<>();
        hs.addAll(recipeIds);
        recipeIds.clear();
        recipeIds.addAll(hs);

        for (i = 0;i<recipeIds.size();i++){
            Log.d("CHECKIDS", recipeIds.get(i) + "");
        }

        ArrayList<String> names = new ArrayList<>();
        for (i = 0; i<recipeIds.size();i++){
            String[] projection = {
                    RecipeBookContract.Recipes.COLUMN_NAME_NAME
            };
            String selection =  RecipeBookContract.Recipes._ID + " =?";
            String[] selectionArgs = {recipeIds.get(i) + ""};

            Cursor c = null;
            try {
                c = mReadOnlyDb.query(
                        RecipeBookContract.Recipes.TABLE_NAME,          // table name
                        projection,                   // The columns to return
                        selection,                    // The columns for the WHERE clause
                        selectionArgs,                // The values for the WHERE clause
                        null,                         // don't group the rows
                        null,                         // don't filter by row groups
                        null                     // The sort order
                );

                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    names.add(c.getString(0));
                }
                // get data out of cursor, i.e.,
                // firstName = cursor.getString(1);
            } catch (Exception e) {
                Log.d("ERROR", "Error in getting recipe Name");

                // handle all exceptions as needed
            } finally {     // this guarantees the cursor is closed.
                if (c != null) {
                    c.close();
                }
            }

        }


        return names;
    }




    @Override
    protected void onPostExecute(ArrayList<String> names) {
        super.onPostExecute(names);
        MainActivity mainActivity = mWeakRefActivity.get();
        if (mainActivity == null)return;
        mainActivity.gotRecipeNames(names);
    }

}
