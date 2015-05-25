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

    public void put(Engine.Input input, Engine.State currentState, Engine.State nextState) {
        map.put(new DoubleKey(input, currentState), nextState);
    }

    private Engine.State get(Engine.Input input, Engine.State currentState) {
         return map.get(new DoubleKey(input, currentState));
    }
}
