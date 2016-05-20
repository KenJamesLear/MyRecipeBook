package edu.scranton.lear.myrecipebook;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by teddylear on 5/19/2016.
 */
public class RecipeInstructionsByNameAsyncTask extends AsyncTask<Void, Void, ArrayList<String>> {

    private WeakReference<MainActivity> mWeakRefActivity;
    private SQLiteDatabase mReadOnlyDb;
    private String mName;


    public RecipeInstructionsByNameAsyncTask(MainActivity mainActivity, SQLiteDatabase readOnlyDb, String name) {
        this.mWeakRefActivity = new WeakReference<>(mainActivity);
        this.mReadOnlyDb = readOnlyDb;
        this.mName = name;

    }

    @Override
    protected ArrayList<String> doInBackground(Void...params) {


        ArrayList<String> instructions = new ArrayList<>();
        long id= -1;
        String[] projection = {
               RecipeBookContract.Recipes._ID
        };
        String selection = RecipeBookContract.Recipes.COLUMN_NAME_NAME + " =?";
        String[] selectionArgs = { mName };

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
                id = c.getLong(0);
            }
            // get data out of cursor, i.e.,
            // firstName = cursor.getString(1);
        } catch (Exception e) {
           id = -1;

            // handle all exceptions as needed
        } finally {     // this guarantees the cursor is closed.
            if(c != null) {
                c.close();
            }
        }

        String[] projectionInstruction = {
                RecipeBookContract.Steps.COLUMN_NAME_INSTRUCTION
        };
        String selectionInstruction = RecipeBookContract.Steps.COLUMN_NAME_RECIPEID + " =?";
        String[] selectionArgsInstruction = { id + "" };
        c = null;
        try {
            c = mReadOnlyDb.query(
                    RecipeBookContract.Steps.TABLE_NAME,          // table name
                    projectionInstruction,                   // The columns to return
                    selectionInstruction,                    // The columns for the WHERE clause
                    selectionArgsInstruction,                // The values for the WHERE clause
                    null,                         // don't group the rows
                    null,                         // don't filter by row groups
                    null                     // The sort order
            );
            for( c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                instructions.add(c.getString(0));
            }
            // get data out of cursor, i.e.,
            // firstName = cursor.getString(1);
        } catch (Exception e) {
            instructions.add("Error Reading Instructions");

            // handle all exceptions as needed
        } finally {     // this guarantees the cursor is closed.
            if(c != null) {
                c.close();
            }
        }
        return instructions;
    }




    @Override
    protected void onPostExecute(ArrayList<String> instructions) {
        super.onPostExecute(instructions);
        MainActivity mainActivity = mWeakRefActivity.get();
        if (mainActivity == null)return;
        mainActivity.readRecipe(instructions);
    }

}
