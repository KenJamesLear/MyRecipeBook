package edu.scranton.lear.myrecipebook;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddRecipeIngredientsFragment extends Fragment implements View.OnClickListener {

    public interface OnAddRecipeInstructionsStartListener{
        void startAddingRecipeSteps(ArrayList<String> ingredients);
    }

    public static final String ARG_NUMBEROFINGREDIENTS =
            "edu.scranton.lear.myrecipebook.ARG_NUMBEROFINGREDIENTS";
    private int mNumberOfIngredients;
    private ArrayList<String> mIngredients;
    private LinearLayout mLinearContainer;
    private ArrayList<TextView> mTextViews;
    private ArrayList<Integer> mViewIds;
    private ArrayList<EditText> mEditTexts;
    private ArrayList<Integer> mEditTextIds;
    private ArrayList<Button> mButtons;
    private ArrayList<Integer> mButtonIds;
    private OnAddRecipeInstructionsStartListener mOnAddRecipeInstructionsStartListener;



    public AddRecipeIngredientsFragment() {
        // Required empty public constructor
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        //mCancelListener = (OnCartCancelListener) context;
        mOnAddRecipeInstructionsStartListener = (OnAddRecipeInstructionsStartListener) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) mNumberOfIngredients = bundle.getInt(AddRecipeIngredientsFragment.ARG_NUMBEROFINGREDIENTS, 0);
        else mNumberOfIngredients = -1;

        mIngredients = new ArrayList<>();
        mTextViews = new ArrayList<>();
        mViewIds = new ArrayList<>();
        mEditTexts = new ArrayList<>();
        mEditTextIds = new ArrayList<>();
        mButtons = new ArrayList<>();
        mButtonIds = new ArrayList<>();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View view = inflater.inflate(R.layout.fragment_confirmation, container, false);
        //mLinearContainer = (LinearLayout) view.findViewById(R.id.my_linearlayout_confirmation);
        View view = inflater.inflate(R.layout.fragment_add_recipe_ingredients, container, false);
        mLinearContainer = (LinearLayout) view.findViewById(R.id.my_linearLayout_Ingredients);
        inflateLinearContainer();
        return view;
    }

    private void inflateLinearContainer() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView recipeIngredientTitle;
        EditText recipeIngredient;
        int id;

        for (int i = 0;i<mNumberOfIngredients;i++)
        {

            recipeIngredientTitle = new TextView(getActivity());
            id = View.generateViewId();
            recipeIngredientTitle.setId(id);
            mTextViews.add(recipeIngredientTitle);
            mViewIds.add(id);
            recipeIngredientTitle.setText("Insert Ingredient " + (i + 1));
            recipeIngredientTitle.setFreezesText(true);
            recipeIngredientTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            recipeIngredientTitle.setLayoutParams(params);
            mLinearContainer.addView(recipeIngredientTitle);

            recipeIngredient = new EditText(getActivity());
            id = View.generateViewId();
            recipeIngredient.setId(id);
            mEditTexts.add(recipeIngredient);
            mEditTextIds.add(id);
            recipeIngredientTitle.setLayoutParams(params);
            mLinearContainer.addView(recipeIngredient);

        }


        // -- add a button to the linearyLayout container

        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        Button button  = new Button(getActivity());
        button.setText("Next");
        button.setOnClickListener(this);
        id = View.generateViewId();
        button.setId(id);
        mButtons.add(button);
        mButtonIds.add(id);
        button.setLayoutParams(buttonParams);

        mLinearContainer.addView(button);
    }

    public void onClick(View v) {
        boolean complete = true;
        for (int i = 0; i < mNumberOfIngredients; i++){
            if (mEditTexts.get(i).getText().toString().compareTo("") == 0)
                complete = false;
        }
        if (complete){
            for (int i = 0; i < mNumberOfIngredients; i++){
              mIngredients.add(mEditTexts.get(i).getText().toString());
            }
            mOnAddRecipeInstructionsStartListener.startAddingRecipeSteps(mIngredients);
        }
        else{
            Toast.makeText(getActivity(),"Please fill out all fields",Toast.LENGTH_SHORT).show();
        }
    }

}
