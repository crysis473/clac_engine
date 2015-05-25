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
    private int valueOnScreen;
    // The operand of the operation.
    private int operand;
    // Current state of the engine.
    private State state;
    // Contains all the state transitions of the FSM.
    private Transition transitions;
    // Enumeration of the states of the engine.
    public enum State {
        DONE, BUILDING_OPERAND, OPERATOR, ERROR;
    }
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

    /**
     * The clear button has been pressed.
     */
    public void clear() {
        lastOperator = ' ';
        state = State.DONE;
        valueOnScreen = 0;
    }

    public void setUpTransitions() {
        transitions = new Transition();
        transitions.put(Input.NUMBER, State.DONE, State.BUILDING_OPERAND);
        transitions.put(Input.OPERATION, State.DONE, State.ERROR);
        transitions.put(Input.NUMBER, State.BUILDING_OPERAND, State.BUILDING_OPERAND);
        transitions.put(Input.OPERATION, State.BUILDING_OPERAND, State.OPERATOR);
    }

}

