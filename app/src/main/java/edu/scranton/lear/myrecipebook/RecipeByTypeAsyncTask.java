package edu.scranton.lear.myrecipebook;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by teddylear on 5/19/2016.
 */
public class RecipeByTypeAsyncTask extends AsyncTask<Void, Void, ArrayList<String>> {

    private WeakReference<MainActivity> mWeakRefActivity;
    private SQLiteDatabase mReadOnlyDb;
    private String mType;


    public RecipeByTypeAsyncTask(MainActivity mainActivity, SQLiteDatabase readOnlyDb, String type) {
        this.mWeakRefActivity = new WeakReference<>(mainActivity);
        this.mReadOnlyDb = readOnlyDb;
        this.mType = type;

    }

    @Override
    protected ArrayList<String> doInBackground(Void...params) {

        ArrayList<String> names = new ArrayList<>();
        String[] projection = {
                RecipeBookContract.Recipes.COLUMN_NAME_NAME
        };
        String selection = RecipeBookContract.Recipes.COLUMN_NAME_TYPE + " =?";
        String[] selectionArgs = { mType };

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

            for( c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                names.add(c.getString(0));
            }
            // get data out of cursor, i.e.,
            // firstName = cursor.getString(1);
        } catch (Exception e) {
            names.add("Error getting Names");

            // handle all exceptions as needed
        } finally {     // this guarantees the cursor is closed.
            if(c != null) {
                c.close();
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
