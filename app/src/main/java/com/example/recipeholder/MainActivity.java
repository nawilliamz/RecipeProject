package com.example.recipeholder;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class MainActivity extends SingleFragmentActivity {


//    private static final String TAG = "MainActivity";
//    private static final String KEY_INDEX = "index";
//    private static final int REQUEST_CODE_CHEAT = 0;

    private static final String EXTRA_RECIPE_ID = "com.example.recipeholder.crime_id";

    @Override
    protected Fragment createFragment() {
        //RecipeFragment class is a Fragment because it extends the Fragment Class and contains the
        //requisite onCreate() and onCreateView() methods
//        return new RecipeFragmentNew();


        //Use this code to start a new RecipeFragment in place of return new ReceipFragmentNew()
        //Retrieve the UUID stashed into the intent when MainActivity is started and store it in
        //a variable. Then use that variable as an argument in RecipeFragement.newInstance() to
        //retrieve it and use it in RecipeFragmentNew when it is committed/started

        UUID recipeId = (UUID) getIntent().getSerializableExtra(EXTRA_RECIPE_ID);
        return RecipeFragmentNew.newInstance(recipeId);

    }


    //This method is used in RecipeHolder in RecipeListFragment,passing in the crimeID
    public static Intent newIntent (Context packageContext, UUID crimeID) {
        Intent intent = new Intent (packageContext, MainActivity.class);
        intent.putExtra(EXTRA_RECIPE_ID, crimeID);
        return intent;
    }

    //This code below adds RecipeFragment to MainActivity

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fragment);
//
//        FragmentManager fm = getSupportFragmentManager();
//        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
//
//        if (fragment == null) {
//            fragment = new RecipeFragment();
//            fm.beginTransaction()
//                    .add(R.id.fragment_container, fragment)
//                    .commit();
//        }
//
//    }



//    private void addRecipeItem(Recipe recipe) {
//        mRecipeList.add(recipe);
//    }

//    private void updateRecipeList() {
//        int recipeResID = mRecipeList.get(mCurrentIndex).getTextResID();
//        mRecipeDescription.setText(recipeResID);
//    }

//    @Override
//    public void onStart(){
//        super.onStart();
//        Log.d(TAG, "onStart called");
//    }
//
//    @Override
//    public void onResume(){
//        super.onResume();
//        Log.d(TAG, "onResume called");
//    }
//
//    @Override
//    public void onPause(){
//        super.onPause();
//        Log.d(TAG, "onPause called");
//    }


    //this code saves the mCurrentIndex integer variable value into savedInstanceState using KEY_INDEX
//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState){
//        super.onSaveInstanceState(savedInstanceState);
//        Log.i(TAG, "onSaveInstanceState");
//        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
//    }

//    @Override
//    public void onStop(){
//        super.onStop();
//        Log.d(TAG, "onStop called");
//    }
//
//    @Override
//    public void onDestroy(){
//        super.onDestroy();
//        Log.d(TAG, "onDestroy called");
//    }
}