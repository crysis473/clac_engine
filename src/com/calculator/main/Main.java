package com.calculator.main;

import com.calculator.engine.Engine;
import com.calculator.gui.UserInterface;

public class Main {

    public static void main(String[] args) {
        UserInterface ui = new UserInterface(new Engine());
    }
}
