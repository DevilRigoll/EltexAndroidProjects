package com.example.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class CalculationProcess {

    private Boolean mValid = true;
    private Double mResult;

    private final String operators = "^*/+-";
    private final String delimiters = "() " + operators;

    public Boolean isValid() {
        return mValid;
    }

    public void compute(String formula) {
        mValid = true;

        if (formula.isEmpty()) {
            mValid = false;
            return;
        }

        List<String> notation = computeReversePolishNotation(formula);

        for (String x : notation) System.out.print(x + " ");

        if (!mValid) {
            return;
        }

        mResult = computeValue(notation);
    }

    public Double getResult() {
        return mResult;
    }

    private List<String> computeReversePolishNotation(String formula) {
        List<String>    res       = new ArrayList<>();
        Stack<String>   stack     = new Stack<>();
        StringTokenizer tokenizer = new StringTokenizer(formula, delimiters, true);

        String curr = "";
        String prev = "";

        mValid = true;

        while(tokenizer.hasMoreTokens()) {
            curr = tokenizer.nextToken();

            if (curr.equals(" ")) {
                continue;
            }

            if (!tokenizer.hasMoreTokens() && isOperator(curr)) {
                mValid = false;
                return res;
            }

            if (isDelimiter(curr)) {
                if (curr.equals("(")) {
                    stack.push(curr);
                } else if (curr.equals(")")) {
                    while (!stack.peek().equals("(")) {
                        res.add(stack.pop());

                        if (stack.isEmpty()) {
                            mValid = false;
                            return res;
                        }
                    }

                    stack.pop();
                } else {
                    if (curr.equals("-") && (prev.equals("") || (isDelimiter(prev) && !prev.equals(")")))) {
                        // unary minus
                        curr = "-x";
                    } else {
                        while (!stack.isEmpty() && (priority(curr) <= priority(stack.peek()))) {
                            res.add(stack.pop());
                        }
                    }

                    stack.push(curr);
                }

            } else {
                res.add(curr);
            }

            prev = curr;
        }

        while (!stack.isEmpty()) {
            if (isOperator(stack.peek())) {
                res.add(stack.pop());
            } else {
                mValid = false;
                return res;
            }
        }

        return res;
    }

    public static Double computeValue(List<String> postfix) {
        Stack<Double>  stack = new Stack<>();
        for (String x : postfix) {
            switch (x) {
                case "^": {
                    Double b = stack.pop(), a = stack.pop();
                    stack.push(Math.pow(a, b));
                    break;
                }
                case "*": {
                    Double b = stack.pop(), a = stack.pop();
                    stack.push(a * b);
                    break;
                }
                case "/": {
                    Double b = stack.pop(), a = stack.pop();
                    stack.push(a / b);
                    break;
                }
                case "+": {
                    Double b = stack.pop(), a = stack.pop();
                    stack.push(a + b);
                    break;
                }
                case "-": {
                    Double b = stack.pop(), a = stack.pop();
                    stack.push(a - b);
                    break;
                }
                case "-x": {
                    Double a = stack.pop();
                    stack.push(-a);
                    break;
                }
                default: {
                    stack.push(Double.valueOf(x));
                }
            }
        }

        return stack.pop();
    }

    private Integer priority(String token) {
        switch (token) {
            case "(":
                return 1;
            case "^":
                return 2;
            case "*":
            case "/":
                return 3;
            case "+":
            case "-":
                return 4;
            default:
                return 5;
        }
    }

    private Boolean isOperator(String token) {
        if (token.equals("-x")) {
            return true;
        }

        for (int i = 0; i < operators.length(); ++i) {
            if (token.charAt(0) == operators.charAt(i)) {
                return true;
            }
        }

        return false;
    }

    private Boolean isDelimiter(String token) {
        if (token.length() != 1) {
            return false;
        }

        for (int i = 0; i < delimiters.length(); ++i) {
            if (token.charAt(0) == delimiters.charAt(i)) {
                return true;
            }
        }

        return false;
    }
}
