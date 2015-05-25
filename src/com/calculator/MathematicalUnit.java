package com.calculator;

/**
 * This class preforms all the mathematical operations.
 */
public  class  MathematicalUnit {

    private MathematicalUnit(){};

    /**
     * Preforms an addition operation
     * @param x Left operand.
     * @param y Right operand.
     * @return Result of the addition.
     */
    public static double addition(double x, double y) {
        return x + y;
    }

    /**
     * Preforms a substaction operation.
     * @param x Left operand.
     * @param y Right operand.
     * @return Result of the substraction.
     */
    public static double substraction(double x, double y) {
        return x - y;
    }

    /**
     * Preforms a multiplication operation.
     * @param x Left operand.
     * @param y Right oeprand.
     * @return Result of the multiplication.
     */
    public static double multiplication(double x, double y) {
        return x * y;
    }

    /**
     * Preforms a division operation.
     * @param x Numerator.
     * @param y Denominator.
     * @return Result of the division or IllegalArgumentException if a null denominator is detected.
     */
    public static double division(double x, double y) {
        if(y == 0) throw new IllegalArgumentException("It is impossible to divide by zero");
        else
            return x/y;
    }
}
