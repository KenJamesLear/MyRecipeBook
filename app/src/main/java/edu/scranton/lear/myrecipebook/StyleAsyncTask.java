package edu.scranton.lear.myrecipebook;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;


public class StyleAsyncTask extends AsyncTask<Void, Void, ArrayList<String>> {

    private WeakReference<MainActivity> mWeakRefActivity;
    private SQLiteDatabase mReadOnlyDb;


    public StyleAsyncTask(MainActivity mainActivity, SQLiteDatabase readOnlyDb) {
        this.mWeakRefActivity = new WeakReference<>(mainActivity);
        this.mReadOnlyDb = readOnlyDb;

    }

    @Override
    protected ArrayList<String> doInBackground(Void...params) {

        ArrayList<String> styles = new ArrayList<>();
        String[] projection = {
                RecipeBookContract.Recipes.COLUMN_NAME_STYLE
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
                String style = c.getString(0);
                styles.add(style);

            }
        } catch (Exception e) {
            styles.add("Error Reading DB");
            // handle all exceptions as needed
        } finally {     // this guarantees the cursor is closed.
            if (c != null) {
                c.close();
            }
        }

        //test code
        HashSet<String> hs = new HashSet<>();
        hs.addAll(styles);
        styles.clear();
        styles.addAll(hs);

        return styles;
    }

    @Override
    protected void onPostExecute(ArrayList<String> styles) {
        super.onPostExecute(styles);
        MainActivity mainActivity = mWeakRefActivity.get();
        if (mainActivity == null)return;
        mainActivity.startSearchByStyle(styles);
    }

}
