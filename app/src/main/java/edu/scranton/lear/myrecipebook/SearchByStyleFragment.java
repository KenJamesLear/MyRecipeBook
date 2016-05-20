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

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchByStyleFragment extends ListFragment {

    public interface OnStyleListener{
        void stylePosition(int position);
    }

    public static final String ARG_STYLES =
            "edu.scranton.lear.myrecipebook.ARG_STYLES";
    private ArrayList<String> styles;
    private Activity mActivity = null;
    private OnStyleListener mListener;


    public SearchByStyleFragment() {

    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        this.mActivity = (Activity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        styles = new ArrayList<>();
        Bundle bundle = getArguments();
        if (bundle != null) styles = bundle.getStringArrayList(SearchByStyleFragment.ARG_STYLES);
        else styles.add("Error getting argument");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, styles);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        try {
            mListener = (OnStyleListener) mActivity;
        } catch (ClassCastException e) {
            throw new ClassCastException(mActivity.toString()
                    + " Error must implement Listener");
        }
        mListener.stylePosition(position);
    }

}
