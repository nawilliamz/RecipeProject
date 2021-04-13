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

public class InstructionListDialog extends AppCompatDialogFragment {

    private TextView mInstructionHeading;
    private EditText mInstructionInput;
    private TextView mActionOK;
    private TextView mActionCancel;

    private static final String TAG = "InstructionListDialog";


    //Interface to be used for sending the instruction input from mInstructionInput over to
    //the TextView in InstructionListDialog
    public interface OnInstructionInputSelected {
        void sendInstructionInput(String instructionInput);

    }

    public OnInstructionInputSelected mOnInstructionInputSelected;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.instruction_list_dialog, container, false);

        mInstructionHeading = view.findViewById(R.id.instruction_title);
        mInstructionInput = view.findViewById(R.id.instruction_view);


        mActionOK = view.findViewById(R.id.ok_selection);

        mActionOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: capturing input");
                String instructionInput = mInstructionInput.getText().toString();

                if (!instructionInput.equals("")){

                    mOnInstructionInputSelected.sendInstructionInput(instructionInput);
                }
                getDialog().dismiss();
            }
        });


        mActionCancel = view.findViewById(R.id.cancel_selection);

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing dialog");
                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mOnInstructionInputSelected = (OnInstructionInputSelected) getTargetFragment();

        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException:" + e.getMessage());
        }
    }
}
