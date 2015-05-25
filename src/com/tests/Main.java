package com.tests;

import com.calculator.Engine;

/**
 * Created by user on 25/05/2015.
 */
public class Main {

    public static void main(String args[]) {
        Engine engine = new Engine();
        engine.numberPressed(4);
        engine.numberPressed(6);
        System.out.println(engine.getValueOnScreen());
        engine.plus();
        engine.numberPressed(3);
        System.out.println(engine.getValueOnScreen());
        engine.plus();
        System.out.println(engine.getValueOnScreen());
    }
}
