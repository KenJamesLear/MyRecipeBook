package edu.scranton.lear.myrecipebook;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchByItemFragment extends ListFragment implements View.OnClickListener {

    public interface OnItemsListener{
        void itemsChosen(ArrayList<String> itemsSelected);
    }

    public static final String ARG_INGREDIENTSLIST =
            "edu.scranton.lear.myrecipebook.ARG_INGREDIENTSLIST";
    private ArrayList<String> mItems;
    private Activity mActivity = null;
    private Button mButton;
    private ListView mListView;
    private ArrayList<String> mItemsSelected;
    private ArrayList<Boolean> mBooleanItems;
    private OnItemsListener mOnItemsListener;



    public SearchByItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        this.mActivity = (Activity) activity;
        mOnItemsListener =  (OnItemsListener) activity;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItems = new ArrayList<>();
        mItemsSelected = new ArrayList<>();
        Bundle bundle = getArguments();
        if (bundle != null) mItems = bundle.getStringArrayList(SearchByItemFragment.ARG_INGREDIENTSLIST);
        else mItems.add("Error getting argument");
        mBooleanItems = new ArrayList<>();
        for (int i = 0;i<mItems.size();i++){
            mBooleanItems.add(false);
        }


        /*RecipeItemArrayAdapter adapter = new RecipeItemArrayAdapter(getActivity(),items);
        setListAdapter(adapter);*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_by_item, container, false);
        mListView = (ListView) view.findViewById(android.R.id.list);
        RecipeItemArrayAdapter adapter = new RecipeItemArrayAdapter(getActivity(),mItems);
        mListView.setAdapter(adapter);
        mButton = (Button) view.findViewById(R.id.confirmCheckedButton);
        mButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        for (int i = 0;i<mItems.size();i++){
            if (mBooleanItems.get(i)){
                mItemsSelected.add(mItems.get(i));
            }
        }
        mOnItemsListener.itemsChosen(mItemsSelected);
    }


    public class RecipeItemArrayAdapter extends ArrayAdapter<String> {
        private final Activity context;
        private ArrayList<String> items;

        public class CheckBoxTag {
            CheckBox itemCheckBox;

        }

        public RecipeItemArrayAdapter(Activity context, ArrayList<String> items) {
            super(context, R.layout.item_ingredient, items);
            this.context = context;
            this.items = items;
        }

        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            View rowView = convertView;

            // makes sure each view is initialized only once
            if (rowView == null) {
                LayoutInflater inflater = context.getLayoutInflater();
                rowView = inflater.inflate(R.layout.item_ingredient, null);

                CheckBoxTag checkBoxTag = new CheckBoxTag();
                checkBoxTag.itemCheckBox = (CheckBox) rowView.findViewById(R.id.checkBox);
                rowView.setTag(checkBoxTag);
            }


            CheckBoxTag holder = (CheckBoxTag) rowView.getTag();
            String item = items.get(position);
            holder.itemCheckBox.setText(item);
            holder.itemCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   if (mBooleanItems.get(position)){
                       mBooleanItems.set(position,false);
                       //Toast.makeText(getActivity(),"false",Toast.LENGTH_SHORT).show();
                       }
                    else{
                       mBooleanItems.set(position,true);
                       //Toast.makeText(getActivity(),"true",Toast.LENGTH_SHORT).show();
                       }
                }
            });

            return rowView;
        }
    }
}
