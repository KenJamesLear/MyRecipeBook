package edu.scranton.lear.myrecipebook;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;


public class MainMenuFragment extends Fragment {

    public interface OnAddRecipeBeginningListener{
        void startAddingRecipe();
    }
    public interface OnSearchByStyleListener{
        void searchByStyle();
    }
    public interface OnSearchByTypeListener{
        void searchByType();
    }
    public interface OnSearchByItemListener{
        void searchByItems();
    }
    public interface OnExitListener{
        void exitApp();
    }



    private OnAddRecipeBeginningListener mAddRecipeBeginningListener;
    private OnSearchByStyleListener mSearchByStyleListener;
    private OnSearchByTypeListener mSearchByTypeListener;
    private OnSearchByItemListener mSearchByItemListener;
    private OnExitListener mOnExitListener;

    public MainMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAddRecipeBeginningListener = (OnAddRecipeBeginningListener) context;
        mSearchByStyleListener = (OnSearchByStyleListener) context;
        mSearchByTypeListener = (OnSearchByTypeListener) context;
        mSearchByItemListener = (OnSearchByItemListener) context;
        mOnExitListener = (OnExitListener) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        //Buttons
        Button addRecipe = (Button) view.findViewById(R.id.addRecipeButton);
        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddRecipeBeginningListener.startAddingRecipe();
            }
        });
        Button searchByStyleButton = (Button) view.findViewById(R.id.searchByStyleButton);
        searchByStyleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchByStyleListener.searchByStyle();
            }
        });
        Button searchByTypeButton = (Button) view.findViewById(R.id.searchByTypeButton);
        searchByTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchByTypeListener.searchByType();
            }
        });
        Button searchBySuppliesButton = (Button) view.findViewById(R.id.searchBySuppliesButton);
        searchBySuppliesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchByItemListener.searchByItems();
            }
        });
        Button exitAppButton = (Button) view.findViewById(R.id.exitAppButton);
        exitAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mOnExitListener.exitApp();
            }
        });


        return view;
    }





}
