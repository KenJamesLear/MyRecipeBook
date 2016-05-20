package edu.scranton.lear.myrecipebook;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by teddylear on 5/19/2016.
 */
public class IngredientAsyncTask extends AsyncTask<Void, Void, ArrayList<String>> {

    private WeakReference<MainActivity> mWeakRefActivity;
    private SQLiteDatabase mReadOnlyDb;


    public IngredientAsyncTask(MainActivity mainActivity, SQLiteDatabase readOnlyDb) {
        this.mWeakRefActivity = new WeakReference<>(mainActivity);
        this.mReadOnlyDb = readOnlyDb;

    }

    @Override
    protected ArrayList<String> doInBackground(Void...params) {

        ArrayList<String> items = new ArrayList<>();
        String[] projection = {
                RecipeBookContract.Ingredients.COLUMN_NAME_INGREDIENT
        };
        Cursor c = null;
        try {
            c = mReadOnlyDb.query(
                    RecipeBookContract.Ingredients.TABLE_NAME,          // table name
                    projection,                   // The columns to return
                    null,                    // The columns for the WHERE clause
                    null,                    // The values for the WHERE clause
                    null,                         // don't group the rows
                    null,                         // don't filter by row groups
                    null                     // The sort order
            );
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                String item = c.getString(0);
                items.add(item);

            }
        } catch (Exception e) {
            items.add("Error Reading DB");
            // handle all exceptions as needed
        } finally {     // this guarantees the cursor is closed.
            if (c != null) {
                c.close();
            }
        }

        //test code
        HashSet<String> hs = new HashSet<>();
        hs.addAll(items);
        items.clear();
        items.addAll(hs);

        return items;
    }




    @Override
    protected void onPostExecute(ArrayList<String> items) {
        super.onPostExecute(items);
        MainActivity mainActivity = mWeakRefActivity.get();
        if (mainActivity == null)return;
        mainActivity.startSearchByItems(items);
    }

}

