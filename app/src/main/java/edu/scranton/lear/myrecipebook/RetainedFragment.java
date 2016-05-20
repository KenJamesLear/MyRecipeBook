package edu.scranton.lear.myrecipebook;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class RetainedFragment extends Fragment {

    private SQLiteDatabase mReadOnlyDb;
    private SQLiteDatabase mWritableDb;

    public RetainedFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // make the instance retained
        setRetainInstance(true);
    }

    public void setReadOnlyDb(SQLiteDatabase ReadOnlyDb){this.mReadOnlyDb = ReadOnlyDb;}
    public SQLiteDatabase getReadOnlyDb() {return mReadOnlyDb;}
    public void setWritableDb(SQLiteDatabase WritableDb){this.mWritableDb = WritableDb;}
    public SQLiteDatabase getWritableDb() {return mWritableDb;}




}
