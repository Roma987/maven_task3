package com.prokopchik;

import java.util.Stack;

public class StringCalculator {

    public Double stringTtoInt(String expression) {
        try {
            char[] tokens = expression.toCharArray();
            Stack<Double> values = new
                    Stack<Double>();
            Stack<Character> operators = new
                    Stack<Character>();
            for (int i = 0; i < tokens.length; i++) {
                if (tokens[i] == ' ') {
                    continue;
                }
                if (tokens[i] >= '0' &&
                        tokens[i] <= '9') {
                    StringBuffer stringbuffer = new
                            StringBuffer();
                    while (i < tokens.length &&
                            tokens[i] >= '0' &&
                            tokens[i] <= '9')
                        stringbuffer.append(tokens[i++]);
                    values.push(Double.parseDouble(stringbuffer.
                            toString()));
                    i--;

                } else if (tokens[i] == '(') {
                    operators.push(tokens[i]);
                } else if (tokens[i] == ')') {
                    while (operators.peek() != '(') {
                        values.push(applyOperator(operators.pop(),
                                values.pop(),
                                values.pop()));
                    }
                    operators.pop();

                } else if (tokens[i] == '+' ||
                        tokens[i] == '-' ||
                        tokens[i] == '*' ||
                        tokens[i] == '/') {
                    while (!operators.empty() &&
                            precedence(tokens[i],
                                    operators.peek())) {
                        values.push(applyOperator(operators.pop(),
                                values.pop(),
                                values.pop()));
                    }
                    operators.push(tokens[i]);
                } else {
                    throw new UnsupportedOperationException("Entered values are incorrect");
                }
            }
            while (!operators.empty()) {
                values.push(applyOperator(operators.pop(),
                        values.pop(),
                        values.pop()));
            }
            return values.pop();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0.0;
    }

    public static boolean precedence(
            char operatorone, char operatortwo) {
        if (operatortwo == '(' || operatortwo == ')') {
            return false;
        }

        if ((operatorone == '*' || operatorone == '/') &&
                (operatortwo == '+' || operatortwo == '-'))
            return false;
        else {
            return true;
        }

    }

    public static double applyOperator(char operator,
                                       Double b, Double a) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new
                            UnsupportedOperationException(
                            "Cannot divide by zero");
                }
                return a / b;
        }
        return 0;
    }
}
