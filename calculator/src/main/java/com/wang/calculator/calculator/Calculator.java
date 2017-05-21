package com.wang.calculator.calculator;

import java.util.HashMap;
import java.util.Stack;

public class Calculator {
    // 对含括号的四则表达式进行求解
    public double calculate(String operationExpression) throws Exception {
        if (false == isBracketMatch(operationExpression)) {
            throw new Exception("括号不匹配！");
        }
        String s = fromInfixToPostfixExpression(operationExpression);
        return calculatePostfixExpression(s);
    }

    private String fromInfixToPostfixExpression(String infixExpression) {
        String s1[] = infixExpression.split("^[-+]?[0-9]+(\\.[0-9]+)?$");

        char expression[] = infixExpression.toCharArray();
        // s1为中缀表达式，s2为后缀表达式
        Stack s11, s2;
        for (int i = 0; i < expression.length; i++) {

        }

        return null;
    }

    private double calculatePostfixExpression(String postfixExpression) {

        return 0;
    }

    // 判断括号是否匹配
    public boolean isBracketMatch(String operationExpression) {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("(", ")");
        // hashMap.put("[", "]");
        // hashMap.put("{", "}");

        char expression[] = operationExpression.toCharArray();
        // 指向栈顶的下一元素
        int rear = 0;
        char bracket[] = new char[expression.length];

        for (int i = 0; i < expression.length; i++) {
            char c = expression[i];
            // 若是左括号，进栈
            if (hashMap.containsKey(c + "")) {
                bracket[rear] = c;
                rear++;
            }
            // 若是右括号
            else if (hashMap.containsValue(c + "")) {
                // 前面的所有括号都匹配成功（rear=0），多出一个右括号，表达式错误
                if (0 >= rear) {
                    return false;
                }
                // 若栈顶与c相对应，比如栈顶是'('， c是')'， 则退栈
                if (hashMap.get(bracket[rear - 1] + "").equals(c + "")) {
                    rear--;
                } else {
                    return false;
                }
            }

        }
        // 入栈和出栈的次数应该相同，rear应该为0，否则匹配不成功
        if (rear != 0) {
            return false;
        }
        return true;
    }
}
