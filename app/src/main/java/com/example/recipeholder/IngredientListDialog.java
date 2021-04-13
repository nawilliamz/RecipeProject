package com.example.recipeholder;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class IngredientListDialog extends AppCompatDialogFragment {

    private static final String TAG = "IngredientListDialog";

    private TextView mIngredientHeading;
    private EditText mIngredientInput;
    private EditText mAmountInput;
    private TextView mActionOK;
    private TextView mActionCancel;

    public interface OnIngredientInputSelected {
        void sendIngredientInput(String ingredientInput, String amountInput);

    }
    public OnIngredientInputSelected mOnIngredientInputSelected;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflating the whole layout rather than on a per widget basis
        View view = inflater.inflate(R.layout.ingredient_list_dialog, container, false);

        mIngredientHeading = view.findViewById(R.id.instruction_title);
        mIngredientInput = view.findViewById(R.id.ingredient_view);
        mAmountInput = view.findViewById(R.id.ingredient_amount_view);
        mActionOK = view.findViewById(R.id.ok_selection);
        mActionCancel = view.findViewById(R.id.cancel_selection);

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing dialog");
                getDialog().dismiss();
            }
        });

        mActionOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: capturing input");
                //converts input from ingredient and amount TextViews into a String
                String ingredientInput = mIngredientInput.getText().toString();
                String amountInput = mAmountInput.getText().toString();


                if (!ingredientInput.equals("")&&!amountInput.equals("")){

                    mOnIngredientInputSelected.sendIngredientInput(ingredientInput, amountInput);

                }
                getDialog().dismiss();
            }

        });
        return view;
    }

    @Override
    public void onAttach (Context context) {
        super.onAttach(context);
        try{
            mOnIngredientInputSelected = (OnIngredientInputSelected) getTargetFragment();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException:" + e.getMessage());
        }
    }
}
