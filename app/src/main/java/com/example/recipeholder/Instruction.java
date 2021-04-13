package com.example.recipeholder;

public class Instruction {

    private String instructionText;

    public Instruction(String instructionText) {
        this.instructionText = instructionText;
    }


    public String getInstructionText() {
        return instructionText;
    }

    @Override
    public String toString() {
        return this.instructionText;
    }
}
