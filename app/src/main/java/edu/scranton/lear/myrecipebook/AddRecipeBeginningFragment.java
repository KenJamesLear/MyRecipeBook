package edu.scranton.lear.myrecipebook;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddRecipeBeginningFragment extends Fragment {

    public interface OnAddRecipeIngredientsStartListener{
        void startAddingRecipeIngredients(AddRecipeBeginningFragmentHelper
                                            addRecipeBeginningFragmentHelper);
    }

    private OnAddRecipeIngredientsStartListener mAddRecipeIngredientsStartListener;
    private EditText mEditTextRecipeName;
    private EditText mEditTextRecipeStyle;
    private EditText mEditTextRecipeIngredients;
    private EditText mEditTextRecipeType;
    private String mRecipeName;
    private String mRecipeStyle;
    private String mRecipeType;
    private int mNumberRecipeIngredients;


    public AddRecipeBeginningFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_recipe_beginning, container, false);
        mEditTextRecipeName = (EditText) view.findViewById(R.id.recipeName);
        mEditTextRecipeStyle = (EditText) view.findViewById(R.id.recipeStyle);
        mEditTextRecipeType = (EditText) view.findViewById(R.id.recipeType);
        mEditTextRecipeIngredients = (EditText) view.findViewById(R.id.recipeIngredients);


        Button next = (Button) view.findViewById(R.id.startAddingRecipeStepsButton);
        next.setOnClickListener(new View.OnClickListener() {
            /*if (mEditText.getText().toString().compareTo("")!=0)
            mQuantity = Integer.parseInt(mEditText.getText().toString());*/
            @Override
            public void onClick(View v) {
                if (mEditTextRecipeName.getText().toString().compareTo("") != 0 &&
                        mEditTextRecipeIngredients.toString().compareTo("") != 0 &&
                        mEditTextRecipeStyle.getText().toString().compareTo("") != 0 &&
                        mEditTextRecipeType.getText().toString().compareTo("") != 0){
                    mRecipeName = mEditTextRecipeName.getText().toString();
                    mRecipeStyle = mEditTextRecipeStyle.getText().toString();
                    mNumberRecipeIngredients = Integer.parseInt(mEditTextRecipeIngredients.getText().toString());
                    mRecipeType = mEditTextRecipeType.getText().toString();
                    startAddingRecipeStepsPreparation();
                }
                else{
                    Toast.makeText(getActivity(), "Please make sure all text fields are filled out",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAddRecipeIngredientsStartListener = (OnAddRecipeIngredientsStartListener) context;
    }
    public void startAddingRecipeStepsPreparation(){
        AddRecipeBeginningFragmentHelper addRecipeBeginningFragmentHelper =
                new AddRecipeBeginningFragmentHelper();
        addRecipeBeginningFragmentHelper.setNumberRecipeIngredients(mNumberRecipeIngredients);
        addRecipeBeginningFragmentHelper.setRecipeName(mRecipeName);
        addRecipeBeginningFragmentHelper.setRecipeStyle(mRecipeStyle);
        addRecipeBeginningFragmentHelper.setRecipeType(mRecipeType);
        mAddRecipeIngredientsStartListener.startAddingRecipeIngredients(addRecipeBeginningFragmentHelper);
    }
}
