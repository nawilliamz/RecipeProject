package com.example.recipeholder;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import java.util.UUID;


//This is a controller
public class RecipeFragment extends Fragment {

    private Recipe mRecipe;
    private EditText mNameField;
    private Button mDateButton;
    private CheckBox mFavoriteCheckBox;

    public static final String ARG_RECIPE_ID = "crime_id";

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mRecipe = new Recipe();

        //This code below was originally used in MainActivity java to capture the UUID for each
        //Recipe, but it was moved here when we switched to use of SingleFragmentActivity
        //Here, we are retreiving the UUID that placed into the Recipe's bundle when it was
        //created
        UUID recipeId = (UUID) getArguments().getSerializable(ARG_RECIPE_ID);
        mRecipe = RecipeQueue.get(getActivity()).getRecipe(recipeId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Need to define a view below, which you will reference when binding the widgets in the view
        View v = inflater.inflate(R.layout.fragment_recipe, container, false);

        mNameField = (EditText) v.findViewById(R.id.recipe_title);
        mNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRecipe.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mDateButton = (Button) v.findViewById(R.id.recipe_date);
        mDateButton.setText(mRecipe.getDate().toString());
        mDateButton.setEnabled(false);

        mFavoriteCheckBox = (CheckBox) v.findViewById(R.id.recipe_favorite);
        mFavoriteCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isFavorite) {
                mRecipe.setFavorite(isFavorite);
            }

        });



        return v;
    }

    //Where is newInstance called? Or are using an intent instead at this point?
    //Recall Bundle arguments are only used to communcicate info between two fragments that are
    //contained in the same Activity (i.e. no intents needed). This doesn't happen in the
    //CriminalIntent example until the DatePicker is brought into the app

    public static RecipeFragment newInstance (UUID recipeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECIPE_ID, recipeId);

        RecipeFragment fragment = new RecipeFragment();
        fragment.setArguments(args);
        return fragment;

    }
}
