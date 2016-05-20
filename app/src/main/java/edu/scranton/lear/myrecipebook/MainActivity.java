package edu.scranton.lear.myrecipebook;

import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        MainMenuFragment.OnAddRecipeBeginningListener,
        MainMenuFragment.OnSearchByStyleListener,
        MainMenuFragment.OnSearchByTypeListener,
        MainMenuFragment.OnSearchByItemListener,
        AddRecipeBeginningFragment.OnAddRecipeIngredientsStartListener,
        AddRecipeIngredientsFragment.OnAddRecipeInstructionsStartListener,
        AddRecipeInstructionFragment.OnRecipeDoneListener,
        SearchByStyleFragment.OnStyleListener,
        ReadRecipeFragment.OnDoneReadingRecipeListener,
        RecipeNameFragment.OnNameListener,
        SearchByTypeFragment.OnTypeListener,
        SearchByItemFragment.OnItemsListener,
        MainMenuFragment.OnExitListener{

    private AddRecipeBeginningFragmentHelper mAddRecipeBeginningFragmentHelper;
    private SQLiteDatabase mReadOnlyDb;
    private SQLiteDatabase mWritableDb;
    private RetainedFragment mDataFragment;
    private AddRecipeToDbAsyncTask mAddRecipeToDbAsyncTask;
    private StyleAsyncTask mStyleAsyncTask;
    private RecipeByStyleAsyncTask mRecipeByStyleAsyncTask;
    private RecipeInstructionsByNameAsyncTask mRecipeInstructionsByNameAsyncTask;
    private TypeAsyncTask mTypeAsyncTask;
    private RecipeByTypeAsyncTask mRecipeByTypeAsyncTask;
    private IngredientAsyncTask mIngredientAsyncTask;
    private RecipeByIngredientAsyncTask mRecipeByIngredientAsyncTask;
    private ArrayList<String> mStyles;
    private ArrayList<String> mNames;
    private ArrayList<String> mTypes;
    private ArrayList<String> mAllItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAddRecipeBeginningFragmentHelper = null;

        FragmentManager fm = getSupportFragmentManager();
        mDataFragment = (RetainedFragment) fm.findFragmentByTag("retained_data");

        if (mDataFragment == null) {
            mDataFragment = new RetainedFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(mDataFragment, "retained_data");
            ft.commit();

            RecipeBookDbOpenHelper dbHelper = new RecipeBookDbOpenHelper(this);
            mReadOnlyDb = dbHelper.getReadableDatabase();
            mWritableDb = dbHelper.getWritableDatabase();
            mDataFragment.setReadOnlyDb(mReadOnlyDb);
            mDataFragment.setWritableDb(mWritableDb);
        }
        mReadOnlyDb = mDataFragment.getReadOnlyDb();
        mWritableDb = mDataFragment.getWritableDb();
        mStyles = new ArrayList<>();
        mNames = new ArrayList<>();
        mTypes = new ArrayList<>();



        if (savedInstanceState != null){
                return;
        }

        MainMenuFragment mainMenu = new MainMenuFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frame_container, mainMenu);
        ft.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startAddingRecipe(){
        AddRecipeBeginningFragment addRecipeBeginning = new AddRecipeBeginningFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_container, addRecipeBeginning);
        ft.commit();
    }

    public void startAddingRecipeIngredients(AddRecipeBeginningFragmentHelper
                                                     addRecipeBeginningFragmentHelper){
        mAddRecipeBeginningFragmentHelper = addRecipeBeginningFragmentHelper;
        AddRecipeIngredientsFragment addRecipeIngredientsFragment = new AddRecipeIngredientsFragment();
        Bundle args = new Bundle();
        args.putInt(AddRecipeIngredientsFragment.ARG_NUMBEROFINGREDIENTS, addRecipeBeginningFragmentHelper.getNumberRecipeIngredients());
        addRecipeIngredientsFragment.setArguments(args);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_container, addRecipeIngredientsFragment);
        ft.commit();
    }

    public void startAddingRecipeSteps(ArrayList<String> ingredients){
        mAddRecipeBeginningFragmentHelper.setIngredients(ingredients);
        AddRecipeInstructionFragment addRecipeInstructionFragment = new AddRecipeInstructionFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_container, addRecipeInstructionFragment);
        ft.commit();
    }

    public void recipeDone(ArrayList<String> instructions){
        mAddRecipeBeginningFragmentHelper.setInstructions(instructions);
        AddRecipeToDbAsyncTask addRecipe = new AddRecipeToDbAsyncTask(this,mWritableDb,mAddRecipeBeginningFragmentHelper);
        mAddRecipeToDbAsyncTask = addRecipe;
        addRecipe.execute();
    }

    public void resetMenu(){
        mStyleAsyncTask = null;
        mAddRecipeBeginningFragmentHelper = null;
        mAddRecipeToDbAsyncTask = null;//do I need this line?
        MainMenuFragment mainMenu = new MainMenuFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_container, mainMenu);
        ft.commit();
    }

    public void searchByStyle(){
        StyleAsyncTask styleAsync = new StyleAsyncTask(this,mReadOnlyDb);
        mStyleAsyncTask = styleAsync;
        styleAsync.execute();
    }

    public void startSearchByStyle(ArrayList<String> styles){
        mStyles = styles;
        SearchByStyleFragment searchByStyleFragment = new SearchByStyleFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(SearchByStyleFragment.ARG_STYLES, styles);
        searchByStyleFragment.setArguments(args);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_container, searchByStyleFragment);
        ft.commit();
    }

    public void stylePosition(int position){
        RecipeByStyleAsyncTask recipeByStyleAsyncTask = new RecipeByStyleAsyncTask(this,mReadOnlyDb,mStyles.get(position));
        mRecipeByStyleAsyncTask = recipeByStyleAsyncTask;
        recipeByStyleAsyncTask.execute();
    }

    public void gotRecipeNames(ArrayList<String> names){
        mNames = names;
        RecipeNameFragment recipeNameFragment = new RecipeNameFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(RecipeNameFragment.ARG_NAMES, names);
        recipeNameFragment.setArguments(args);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_container, recipeNameFragment);
        ft.commit();
    }

    public void recipeNameForInstructions(int position){
        RecipeInstructionsByNameAsyncTask recipeInstructionsByNameAsyncTask =
                new RecipeInstructionsByNameAsyncTask(this,mReadOnlyDb,mNames.get(position));
        mRecipeInstructionsByNameAsyncTask = recipeInstructionsByNameAsyncTask;
        recipeInstructionsByNameAsyncTask.execute();
    }

    public void readRecipe(ArrayList<String> instructions){
        ReadRecipeFragment readRecipeFragment = new ReadRecipeFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ReadRecipeFragment.ARG_INSTRUCTIONS, instructions);
        readRecipeFragment.setArguments(args);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_container, readRecipeFragment);
        ft.commit();
    }

    public void searchByType(){
        TypeAsyncTask typeAsyncTask = new TypeAsyncTask(this,mReadOnlyDb);
        mTypeAsyncTask = typeAsyncTask;
        typeAsyncTask.execute();
    }

    public void startSearchByType(ArrayList<String> types){
        mTypes = types;
        SearchByTypeFragment searchByTypeFragment = new SearchByTypeFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(SearchByTypeFragment.ARG_TYPES, types);
        searchByTypeFragment.setArguments(args);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_container, searchByTypeFragment);
        ft.commit();
    }

    public void typePosition(int position){
        RecipeByTypeAsyncTask recipeByTypeAsyncTask = new RecipeByTypeAsyncTask(this,mReadOnlyDb,mTypes.get(position));
        mRecipeByTypeAsyncTask = recipeByTypeAsyncTask;
        recipeByTypeAsyncTask.execute();
    }

    public void searchByItems(){
        IngredientAsyncTask ingredientAsyncTask = new IngredientAsyncTask(this,mReadOnlyDb);
        mIngredientAsyncTask = ingredientAsyncTask;
        ingredientAsyncTask.execute();
    }

    public void startSearchByItems(ArrayList<String> items){
        mAllItems = items;
        SearchByItemFragment searchByItemFragment = new SearchByItemFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(SearchByItemFragment.ARG_INGREDIENTSLIST, items);
        searchByItemFragment.setArguments(args);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_container, searchByItemFragment);
        ft.commit();
    }

    public void itemsChosen(ArrayList<String> itemSelected ){
        RecipeByIngredientAsyncTask recipeByIngredientAsyncTask = new RecipeByIngredientAsyncTask(this,mReadOnlyDb,itemSelected);
        mRecipeByIngredientAsyncTask = recipeByIngredientAsyncTask;
        recipeByIngredientAsyncTask.execute();
    }

    public void exitApp(){
        super.finish();
    }

    public void onDestroy() {
        super.onDestroy();
        if(isFinishing()) {
            mReadOnlyDb.close();
            mWritableDb.close();
        }
    }









}
