package com.example.recipeholder;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class RecipeFragmentNew extends Fragment implements IngredientListDialog.OnIngredientInputSelected,
        InstructionListDialog.OnInstructionInputSelected{


    public static final String TAG = "RecipeFragmentNew";
    public static final String DIALOG_INGREDIENTS = "DialogIngredients";

    private Recipe mRecipe;
    private EditText mNameField;
    private Button mIngredientAdd;
    private Button mIngredientDelete;
    private ListView mIngredientWindow;
    private Button mInstructionAdd;
    private Button mInstructionDelete;
    private ListView mInstructionWindow;

    private int mIngredientListViewPosition;
    private int mInstructionListViewPosition;
    private boolean isDeleteClicked;

    public static final String ARG_RECIPE_ID = "crime_id";

    private ArrayAdapter<Ingredient> mListAdapter;
    private ArrayAdapter<Instruction> mInstructionAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mRecipe = new Recipe();

        //Need to catch the recipe ID number from the parent Activity and use this here to show
        //the correct recipe view and also save the correct recipe name
        //Need to call newIntent here to retrieve the UUID???
        UUID recipeId = (UUID) getArguments().getSerializable(ARG_RECIPE_ID);
//        for (Recipe recipe : RecipeQueue.get(getContext()).getRecipes()) {
//            if (recipe.getID().equals(recipeId)){
//                mRecipe = recipe;
//            }
//        }

        mRecipe = RecipeQueue.get(getActivity()).getRecipe(recipeId);

    }

    @Override
    public void onPause() {
        super.onPause();

        RecipeQueue.get(getActivity()).updateRecipe(mRecipe);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lists_detail_view, container, false);

        //These two lines of code were moved from onCreate() method above
//        UUID recipeId = (UUID) getArguments().getSerializable(ARG_RECIPE_ID);
//        mRecipe = RecipeQueue.get(getActivity()).getRecipe(recipeId);

        mNameField = v.findViewById(R.id.ingredient_name_view);
        mNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRecipe.setName(s.toString());
//                mNameField.setText(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //INGREDIENT CODE

        mIngredientAdd = v.findViewById(R.id.add_ingredient_button);
        mIngredientAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening ingredient dialog");
                IngredientListDialog dialog = new IngredientListDialog();
                dialog.setTargetFragment(RecipeFragmentNew.this, 1);
                dialog.show(getFragmentManager(), "MyIngredientDialog");
            }
        });


        mIngredientDelete = v.findViewById(R.id.delete_ingredient_button);
        
        mIngredientDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < mRecipe.getIngredients().size(); i++) {
                    if (i == mIngredientListViewPosition) {
                       mRecipe.getIngredients().remove(i);


                    }
                    mListAdapter.notifyDataSetChanged();
                }

//                listAdapter.remove(listAdapter.getItem(listViewPosition));

            }
        });



        //Will need to set the text in this window from Dialog that captures input from user

        mListAdapter = new ArrayAdapter<Ingredient>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                mRecipe.getIngredients()
        );


        mIngredientWindow = v.findViewById(R.id.ingredients_window);
        mIngredientWindow.setAdapter(mListAdapter);

//        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                listViewPosition = position;
////                selectedItemFromList = (Ingredient) mIngredientWindow.getItemAtPosition(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        };

        AdapterView.OnItemClickListener onIngredientItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //isClicked is set to true whenever an item is clicked then used by the listener
                //on mIngredientDelete button to determine whether to allow a click or not
                mIngredientListViewPosition = position;
//                isDeleteClicked = true;

//                selectedItemFromList = (Ingredient) mIngredientWindow.getItemAtPosition(position);

            }
        };

        //This line of code is critical for attaching the listener to your IngredientWindow. Note that,
        //for some reason, it has to be after/below the line where the OnItemClicklistener is defined
        mIngredientWindow.setOnItemClickListener(onIngredientItemClickListener);



        //INSTRUCTION CODE

        mInstructionDelete = v.findViewById((R.id.delete_instruction_button));

        mInstructionDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < mRecipe.getInstructions().size(); i++) {
                    if (i == mInstructionListViewPosition) {
                        mRecipe.getInstructions().remove(i);


                    }
                    mInstructionAdapter.notifyDataSetChanged();
                }
            }
        });

        mInstructionAdd = v.findViewById(R.id.add_instruction_button);

        mInstructionAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening instruction dialog");
                InstructionListDialog dialog = new InstructionListDialog();
                dialog.setTargetFragment(RecipeFragmentNew.this, 2);
                dialog.show(getFragmentManager(), "MyInstructionDialog");
            }
        });


        mInstructionAdapter = new ArrayAdapter<Instruction>(
            getActivity(),
            android.R.layout.simple_list_item_1,
            mRecipe.getInstructions()
        );


        mInstructionWindow = v.findViewById(R.id.instructions_window);
        mInstructionWindow.setAdapter(mInstructionAdapter);

        AdapterView.OnItemClickListener onInstructionItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //isClicked is set to true whenever an item is clicked then used by the listener
                //on mIngredientDelete button to determine whether to allow a click or not
                mInstructionListViewPosition = position;
//                isDeleteClicked = true;

//                selectedItemFromList = (Ingredient) mIngredientWindow.getItemAtPosition(position);

            }
        };

        mInstructionWindow.setOnItemClickListener(onInstructionItemClickListener);



        return v;
    }





    @Override
    public void sendIngredientInput(String ingredientInput, String ingredientAmount) {
        Log.d(TAG, "sendInput: found incoming input");

        if ((!ingredientInput.equals("")) && (!ingredientAmount.equals(""))) {
            Ingredient ingredient = new Ingredient(ingredientInput, ingredientAmount);
            //If you use listAdapter.add(ingredients), the ingredient fills the list window immediately
//            mRecipe.getIngredients().add(ingredient);
            mListAdapter.add(ingredient);

        }
    }

    @Override
    public void sendInstructionInput(String instructionInput) {
        Log.d(TAG, "sendInput: found incoming input");

        if((!instructionInput.equals(""))) {
            Instruction instruction = new Instruction(instructionInput);
            mInstructionAdapter.add(instruction);
        }
    }




    public static RecipeFragmentNew newInstance(UUID recipeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECIPE_ID, recipeId);

        RecipeFragmentNew fragment = new RecipeFragmentNew();
        fragment.setArguments(args);
        return fragment;


    }



}