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
public class TypeAsyncTask extends AsyncTask<Void, Void, ArrayList<String>> {

    private WeakReference<MainActivity> mWeakRefActivity;
    private SQLiteDatabase mReadOnlyDb;


    public TypeAsyncTask(MainActivity mainActivity, SQLiteDatabase readOnlyDb) {
        this.mWeakRefActivity = new WeakReference<>(mainActivity);
        this.mReadOnlyDb = readOnlyDb;

    }

    @Override
    protected ArrayList<String> doInBackground(Void...params) {

        ArrayList<String> types = new ArrayList<>();
        String[] projection = {
                RecipeBookContract.Recipes.COLUMN_NAME_TYPE
        };
        Cursor c = null;
        try {
            c = mReadOnlyDb.query(
                    RecipeBookContract.Recipes.TABLE_NAME,          // table name
                    projection,                   // The columns to return
                    null,                    // The columns for the WHERE clause
                    null,                    // The values for the WHERE clause
                    null,                         // don't group the rows
                    null,                         // don't filter by row groups
                    null                     // The sort order
            );
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                String type = c.getString(0);
                types.add(type);

            }
        } catch (Exception e) {
            types.add("Error Reading DB");
            // handle all exceptions as needed
        } finally {     // this guarantees the cursor is closed.
            if (c != null) {
                c.close();
            }
        }

        //test code
        HashSet<String> hs = new HashSet<>();
        hs.addAll(types);
        types.clear();
        types.addAll(hs);

        return types;
    }




    @Override
    protected void onPostExecute(ArrayList<String> types) {
        super.onPostExecute(types);
        MainActivity mainActivity = mWeakRefActivity.get();
        if (mainActivity == null)return;
        mainActivity.startSearchByType(types);
    }

}
