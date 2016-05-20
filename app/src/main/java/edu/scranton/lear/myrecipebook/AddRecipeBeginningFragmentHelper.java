package edu.scranton.lear.myrecipebook;

import java.util.ArrayList;

/**
 * Created by teddylear on 5/9/2016.
 */
public class AddRecipeBeginningFragmentHelper {
    private String mRecipeName;
    private String mRecipeStyle;
    private String mRecipeType;
    private int mNumberRecipeIngredients;
    private ArrayList<String> mIngredients;
    private ArrayList<String> mInstructions;

    public AddRecipeBeginningFragmentHelper(){
        mIngredients = new ArrayList<>();
        mInstructions = new ArrayList<>();
    }

    public void setRecipeName(String recipeName){mRecipeName = recipeName;}
    public String getRecipeName() {return mRecipeName;}
    public void setRecipeStyle(String recipeStyle) {mRecipeStyle = recipeStyle;}
    public String getRecipeStyle() {return mRecipeStyle;}
    public void setNumberRecipeIngredients(int recipeIngredients)
        {mNumberRecipeIngredients = recipeIngredients;}
    public int getNumberRecipeIngredients() {return mNumberRecipeIngredients;}
    public void setIngredients(ArrayList<String> ingredients) {mIngredients = ingredients;}
    public ArrayList<String> getIngredients() {return mIngredients;}
    public void setInstructions(ArrayList<String> instructions) {mInstructions = instructions;}
    public ArrayList<String> getInstructions() {return mInstructions;}
    public void setRecipeType(String type){mRecipeType = type;}
    public String getRecipeType(){return mRecipeType;}
}
