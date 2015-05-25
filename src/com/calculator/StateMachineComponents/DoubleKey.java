package com.calculator.StateMachineComponents;

import com.calculator.Engine;

/**
 * Created by user on 25/05/2015.
 */
public class DoubleKey {

    private Engine.State state;
    private Engine.Input input;

    public DoubleKey(Engine.Input input, Engine.State state) {
        this.input = input;
        this.state = state;
    }

    /**
     *
     * @param object The object to compare to this instance of DoubleKey.
     * @return True if the comparison is successful, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        // If object is exactly the same object as this, the comparison is successful.
        if(this == object) {
            return true;
        }
        // If the parameter is either null or is not an instance of DoubleKey, the comparison fails.
        if(object == null || !(object instanceof DoubleKey)) {
            return false;
        }
        /* If the object is an instance of DoubleKey and its state is equal to the state of the current instance
        and its input is equal to the input of the current instance. Then the comparison is a success. Otherwise,
        it fails.
         */
        DoubleKey dk = (DoubleKey)object;
        return (input.equals(dk.input) && state.equals(dk.state)) ? true : false;
    }

    /**
     * The hash code of the current instance is the concatenation of the hash code of its state and input.
     * @return The hash code of the current instance.
     */
    @Override
    public int hashCode() {
        return input.hashCode() + state.hashCode();
    }


}
