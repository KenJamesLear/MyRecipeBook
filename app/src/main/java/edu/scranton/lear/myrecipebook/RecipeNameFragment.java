package edu.scranton.lear.myrecipebook;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeNameFragment extends ListFragment {

    public interface OnNameListener{
        void recipeNameForInstructions(int position);
    }

    public static final String ARG_NAMES =
            "edu.scranton.lear.myrecipebook.ARG_NAMES";
    private ArrayList<String> names;
    private Activity mActivity = null;
    private OnNameListener mListener;

    public RecipeNameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        this.mActivity = (Activity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        names = new ArrayList<>();
        Bundle bundle = getArguments();
        if (bundle != null) names = bundle.getStringArrayList(RecipeNameFragment.ARG_NAMES);
        else names.add("Error getting argument");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, names);
        setListAdapter(adapter);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        try {
            mListener = (OnNameListener) mActivity;
        } catch (ClassCastException e) {
            throw new ClassCastException(mActivity.toString()
                    + " Error must implement Listener");
        }
        mListener.recipeNameForInstructions(position);
    }

}
