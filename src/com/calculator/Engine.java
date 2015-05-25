package com.calculator;

/**
 * Created by user on 25/05/2015.
 */

/**
 * Handles and preforms arithmetical operations entered by user via the command line or/and the
 * graphical interface.
 */
public class Engine {

    // The symbole of the operation to preform.
    private char operationSymbol;
    // The result of the operation.
    private int valueOnScreen;
    // The operand of the operation.
    private int operand;

    /**
     * Instanciates an Engine.
     */
    public Engine() {
        valueOnScreen = 0;
        operationSymbol = ' ';
        operand = 0;
    }

    /**
     * @return The result of the operation or "0" if no operation has been preformed yet.
     */
    public int getValueOnScreen() {
        return valueOnScreen;
    }

    // Ok for the commit.

    /**
     * Number button pressed.
     * @param number The on digit number related to the pressed button.
     */
    public void numberPressed(int number) {
        valueOnScreen = valueOnScreen*10 + number;
    }

}

