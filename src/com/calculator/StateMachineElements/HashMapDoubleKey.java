package com.calculator.StateMachineElements;

import com.calculator.Engine;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 25/05/2015.
 */
public class HashMapDoubleKey {
    // This HashMap modelize a state transition.
    private Map<DoubleKey, Engine.State> map = new HashMap<>();

    /**
     * Put a new DoubleKey associated with a new state in the map.
     * @param input The input element of the new DoubleKey.
     * @param currentState The currentState of the new DoubleKey.
     * @param nextState The new State to associate with the new DoubleKey.
     */
    public void put(Engine.Input input, Engine.State currentState, Engine.State nextState) {
        map.put(new DoubleKey(input, currentState), nextState);
    }

    /**
     * Gets the state corresponding to a given DoubleKey.
     * @param input The input element of the DoubleKey.
     * @param currentState The state element of the DoubleKey.
     * @return The state correponding to the double key whose instance variables are the two parameters.
     */
    private Engine.State get(Engine.Input input, Engine.State currentState) {
         return map.get(new DoubleKey(input, currentState));
    }
}
