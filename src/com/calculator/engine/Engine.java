package com.calculator.engine;

/**
 * Created by user on 25/05/2015.
 */

import com.calculator.mathematicalUnit.MathematicalUnit;
import com.calculator.stateMachineComponents.DoubleKey;
import com.calculator.stateMachineComponents.Transition;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Handles all arithmetical operations entered by user via the command line or/and the
 * graphical interface.
 */
public class Engine {

    // The previous operation symbol pressed by the user.
    private char lastOperator;
    // The result of the operation.
    private double displayValue;
    // The operand of the operation.
    private double leftOperand;
    // Current state of the engine.
    private State state;
    // Contains all the state transitions of the FSM.
    private Transition transitions;
    // The non zero decimal digits number to be printed after the decimal point.
    private int fractionDigitsNumber;
    // The string after the decimal point.
    private String afterDecimal = "";
    // Enumeration of the states of the engine.
    public enum State {
        DONE, BUILDING_OPERAND, OPERATOR, ERROR, BUILDING_DECIMAL;
    }
    // Enumeration of the type of input symbols.
    public enum Input {
        NUMBER, OPERATION, EQUALS, DECIMAL_SEPARATOR;
    }

    /**
     * Instanciates an Engine.
     */
    public Engine() {
        setUpTransitions();
        clear();
        // By default, the number of non null digits to be printed after the decimal point is 3.
        setFractionDigitsNumber(3);
    }

    /**
     * @return The result of the operation or "0" if no operation has been preformed yet.
     */
    public String getDisplayValue() {
        /* If diplayValue can be cast to an integer without loss of precision,
        we print the integer value without the decimal part.
         */

        if(state == State.BUILDING_DECIMAL) {
            //final int d = (int)displayValue;
            return  afterDecimal;
        }

        /*if(displayValue == (int)displayValue)
            return "" + (int)displayValue + lastOperator;*/

        /* If the diplayValue cannot be cast into an integer without loss of precision,
        we print the double value with the specified number of fractions digits.
         */
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(fractionDigitsNumber);
        df.setMaximumFractionDigits(fractionDigitsNumber);
        String str = df.format(displayValue).replaceAll(",",".");
        if(state == State.OPERATOR)
            return str + lastOperator;
        else
            return str;
    }

    /**
     * Number button pressed.
     * @param number The on digit number related to the pressed button.
     */
    public void numberPressed(int number) {
        if(state != (State.OPERATOR)) {
            if(state.equals(State.DONE))
                clear();
            if(state != State.BUILDING_DECIMAL){
                // Insert the digit.
                displayValue = displayValue *10 + number;
            }
            else{
                afterDecimal += number;
                displayValue = Double.valueOf(afterDecimal);
                System.out.println(displayValue);
            }
        }
        else {
            displayValue = number;
        }
        state = transitions.get(Input.NUMBER, state);
    }

    public void decimalSeparatorPressed() {
        afterDecimal = (int)displayValue + ".";
        if(state != State.BUILDING_OPERAND)
            keySequenceError();
        state = transitions.get(Input.DECIMAL_SEPARATOR, state);
    }

    /**
     * The '+' button was pressed.
     */
    public void plus() {
            applyOperator('+');
    }

    /**
     * The '-' button was pressed.
     */
    public void minus() {
        applyOperator('-');
    }

    /**
     * The '*' button was pressed.
     */
    public void multiply() {
        applyOperator('*');
    }

    /**
     * The '/' button was pressed.
     */
    public void divide() {
        applyOperator('/');
    }

    /**
     * The '=' button was pressed.
     */
    public void applyEquals() {
        // State transition.
        state = transitions.get(Input.EQUALS, state);
        // If we are not in an error state, the engine preforms the operation.
        if(state != State.ERROR) {
            calculateResult();
            lastOperator = ' ';
        } else
            keySequenceError();
    }

    private void calculateResult() {
        if(state != State.OPERATOR) {
            switch(lastOperator) {
                case '+':
                    displayValue = MathematicalUnit.addition(leftOperand, displayValue);
                    leftOperand = displayValue;
                    break;
                case '-':
                    displayValue = MathematicalUnit.substraction(leftOperand, displayValue);
                    leftOperand = displayValue;
                    break;
                case '*':
                    displayValue = MathematicalUnit.multiplication(leftOperand, displayValue);
                    leftOperand = displayValue;
                    break;
                case '/':
                    displayValue = MathematicalUnit.division(leftOperand, displayValue);
                    leftOperand = displayValue;
                    break;
                default:
                    keySequenceError();
                    break;
            }
        }
    }

    /**
     * Apply an operator.
     * @param operator The operator to apply.
     */
    private void applyOperator(char operator) {
        // State transition when applying an operator.

        if(state == State.ERROR) {
            keySequenceError();
            return;
        }

        if(lastOperator != ' ')
            calculateResult();
        else
            leftOperand = displayValue;

        lastOperator = operator;
        state = transitions.get(Input.OPERATION, state);
    }

    /**
     * Prints an error message and resets the engine.
     */
    private void keySequenceError() {
        System.out.println("Key sequence error.");
        clear();
    }

    /**
     * The clear button has been pressed.
     */
    public void clear() {
        lastOperator = ' ';
        state = State.DONE;
        displayValue = 0;
        afterDecimal = "";
    }

    public void setFractionDigitsNumber(int digits) {
        fractionDigitsNumber = digits;
    }

    /**
     * Sets up all the transitions of the finite state machine.
     */
    private void setUpTransitions() {
        transitions = new Transition();
        transitions.put(Input.NUMBER, State.DONE, State.BUILDING_OPERAND);
        transitions.put(Input.NUMBER, State.BUILDING_OPERAND, State.BUILDING_OPERAND);
        transitions.put(Input.NUMBER, State.BUILDING_DECIMAL, State.BUILDING_DECIMAL);
        transitions.put(Input.NUMBER, State.OPERATOR, State.BUILDING_OPERAND);
        transitions.put(Input.OPERATION, State.DONE, State.ERROR);
        transitions.put(Input.OPERATION, State.BUILDING_OPERAND, State.OPERATOR);
        transitions.put(Input.OPERATION, State.BUILDING_DECIMAL, State.OPERATOR);
        transitions.put(Input.OPERATION, State.OPERATOR, State.OPERATOR);
        transitions.put(Input.EQUALS, State.DONE, State.ERROR);
        transitions.put(Input.EQUALS, State.BUILDING_OPERAND, State.DONE);
        transitions.put(Input.EQUALS, State.OPERATOR, State.ERROR);
        transitions.put(Input.EQUALS, State.BUILDING_DECIMAL, State.DONE);
        transitions.put(Input.DECIMAL_SEPARATOR, State.OPERATOR, State.ERROR);
        transitions.put(Input.DECIMAL_SEPARATOR, State.BUILDING_DECIMAL, State.ERROR);
        transitions.put(Input.DECIMAL_SEPARATOR, State.BUILDING_OPERAND, State.BUILDING_DECIMAL);
        transitions.put(Input.DECIMAL_SEPARATOR, State.DONE, State.DONE);
        transitions.put(Input.DECIMAL_SEPARATOR, State.ERROR, State.DONE);
    }

    public String getTitle() {
        return "Calculatrice";
    }

    public String getAuthor() {
        return "Student";
    }

    public String getVersion() {
        return "Version 1.0";
    }

}

