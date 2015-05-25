package com.calculator;

/**
 * Created by user on 25/05/2015.
 */

import com.calculator.StateMachineComponents.Transition;

/**
 * Handles all arithmetical operations entered by user via the command line or/and the
 * graphical interface.
 */
public class Engine {

    // The previous operation symbol pressed by the user.
    private char lastOperator;
    // The result of the operation.
    private double valueOnScreen;
    // The operand of the operation.
    private double leftOperand;
    // Current state of the engine.
    private State state;
    // Contains all the state transitions of the FSM.
    private Transition transitions;

    // Enumeration of the states of the engine.
    public enum State {
        DONE, BUILDING_OPERAND, OPERATOR, ERROR;
    }
    // Enumeration of the type of input symbols.
    public enum Input {
        NUMBER, OPERATION, EQUALS;
    }

    /**
     * Instanciates an Engine.
     */
    public Engine() {
        setUpTransitions();
        clear();
    }

    /**
     * @return The result of the operation or "0" if no operation has been preformed yet.
     */
    public double getValueOnScreen() {
        return valueOnScreen;
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

    /**
     * Number button pressed.
     * @param number The on digit number related to the pressed button.
     */
    public void numberPressed(int number) {
        if(!state.equals(State.OPERATOR))
            // Insert the digit.
            valueOnScreen = valueOnScreen*10 + number;
        else {
            valueOnScreen = number;
        }
        state = transitions.get(Input.NUMBER, state);
    }

    private void calculateResult() {
        switch(lastOperator) {
            case '+':
                valueOnScreen = MathematicalUnit.addition(leftOperand, valueOnScreen);
                leftOperand = valueOnScreen;
                break;
            case '-':
                valueOnScreen = MathematicalUnit.substraction(leftOperand, valueOnScreen);
                leftOperand = valueOnScreen;
            case '*':
                valueOnScreen = MathematicalUnit.multiplication(leftOperand, valueOnScreen);
                leftOperand = valueOnScreen;
                break;
            case '/':
                valueOnScreen = MathematicalUnit.division(leftOperand, valueOnScreen);
                leftOperand = valueOnScreen;
                break;
            default:
                keySequenceError();
                break;
        }
    }

    /**
     * Apply an operator.
     * @param operator The operator to apply.
     */
    private void applyOperator(char operator) {
        // State transition when applying an operator.
        state = transitions.get(Input.OPERATION, state);
        System.out.println(state);
        if(state.equals(State.ERROR)) {
            keySequenceError();
            return;
        }

        if(lastOperator != ' ')
            calculateResult();
        else {
            leftOperand = valueOnScreen;
        }

        lastOperator = operator;
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
        valueOnScreen = 0;
    }

    /**
     * Sets up all the transitions of the finite state machine.
     */
    private void setUpTransitions() {
        transitions = new Transition();
        transitions.put(Input.NUMBER, State.DONE, State.BUILDING_OPERAND);
        transitions.put(Input.NUMBER, State.BUILDING_OPERAND, State.BUILDING_OPERAND);
        transitions.put(Input.NUMBER, State.OPERATOR, State.BUILDING_OPERAND);
        transitions.put(Input.OPERATION, State.DONE, State.ERROR);
        transitions.put(Input.OPERATION, State.BUILDING_OPERAND, State.OPERATOR);
        transitions.put(Input.OPERATION, State.OPERATOR, State.ERROR);
        transitions.put(Input.EQUALS, State.DONE, State.ERROR);
        transitions.put(Input.EQUALS, State.BUILDING_OPERAND, State.DONE);
        transitions.put(Input.EQUALS, State.OPERATOR, State.ERROR);
    }

}

