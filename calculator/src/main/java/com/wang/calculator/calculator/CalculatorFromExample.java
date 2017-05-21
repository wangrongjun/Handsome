package com.wang.calculator.calculator;

import java.util.Stack;

public class CalculatorFromExample {
    public String suffix_expression(String expression) throws Exception// 锟斤拷缀锟斤拷锟绞阶拷锟斤拷锟阶猴拷锟斤拷式锟斤拷锟芥波锟斤拷式锟斤拷
    {
        // Stack<Double> s2=new Stack<Double>();//锟斤拷锟斤拷锟斤拷锟秸�
        Stack<Object> s3 = new Stack<Object>();// 锟斤拷沤锟斤拷栈
        Stack<Character> s4 = new Stack<Character>();// 锟斤拷挪锟斤拷锟斤拷锟秸�
        int len = expression.length();//

        char c1;
        double number;
        int m, n = -1;
        for (int i = 0; i < len; i++) {
            c1 = expression.charAt(i);
            if (isOprator(c1) || (i == len - 1))// 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷前锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟絪3栈锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷s4栈
            {
                if (i == len - 1 && (!isOprator(c1)))// 锟斤拷锟斤拷锟揭晃伙拷也锟斤拷遣锟斤拷锟斤拷锟绞憋拷锟斤拷锟角帮拷锟斤拷锟斤拷压栈
                    m = i + 1;
                else
                    m = i;
                // 锟斤拷锟斤拷锟斤拷锟斤拷栈,锟斤拷前锟斤拷锟斤拷锟斤拷一锟斤拷锟斤拷锟斤拷锟斤拷屑锟斤拷锟街凤拷转锟斤拷为double
                for (int j = i - 1; j >= 0; j--) {
                    if (isOprator(expression.charAt(j))) {
                        n = j;
                        break;
                    }
                    n = j - 1;
                }
                if (m != n + 1)// 只锟叫碉拷锟斤拷锟斤拷锟斤拷值锟斤拷锟斤拷时锟叫硷拷呕锟斤拷胁锟斤拷锟斤拷锟�
                {
                    number = Double.parseDouble(expression.substring(n + 1, m));
                    s3.push(number);
                }
                // 锟斤拷锟斤拷锟斤拷锟秸�
                if (i == 0 && (c1 != '('))// 锟斤拷锟斤拷锟绞斤拷锟揭伙拷锟斤拷址锟斤拷为锟斤拷锟斤拷锟斤拷也锟斤拷锟斤拷锟斤拷锟斤拷锟绞憋拷锟斤拷锟斤拷乇锟斤拷式锟斤拷锟斤拷
                {
                    // return "锟斤拷锟绞斤拷锟斤拷锟�";
                    throw new Exception();
                } else if (isOprator(c1))// 锟斤拷锟角诧拷锟斤拷锟斤拷时
                {
                    while (true) {
                        if (s4.isEmpty() || s4.peek() == '(' || c1 == '(')// 锟斤拷锟秸晃拷栈锟斤拷锟秸伙拷锟皆拷锟轿拷锟斤拷锟斤拷锟絚1为锟斤拷时锟斤拷锟斤拷直锟接斤拷锟斤拷锟斤拷锟窖癸拷锟秸伙拷锟�
                        {
                            s4.push(c1);
                            break;
                        } else if (c1 == ')')// 锟斤拷c1为锟斤拷时锟斤拷锟斤拷锟轿碉拷锟斤拷s4锟叫碉拷锟斤拷锟斤拷锟窖癸拷锟絪3锟斤拷直锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷一锟斤拷锟斤拷锟斤拷
                        {
                            while (s4.peek() != '(') {
                                s3.push(s4.pop());
                                if (s4.isEmpty())// 锟斤拷锟斤拷锟斤拷锟叫诧拷为锟斤拷锟斤拷锟斤拷之锟斤拷锟秸晃拷眨锟斤拷锟斤拷锟绞斤拷锟斤拷戏锟�
                                {
                                    // return "缺锟斤拷锟斤拷锟斤拷锟斤拷";
                                    throw new Exception();
                                }
                            }
                            s4.pop();// 锟斤拷锟斤拷锟斤拷
                            break;
                        } else {
                            if (priorityCompare(c1, s4.peek()) == 1)// 锟叫讹拷锟斤拷锟饺硷拷锟斤拷锟斤拷锟饺硷拷锟斤拷锟斤拷栈锟斤拷锟斤拷锟饺硷拷锟酵斤拷栈锟斤拷锟斤拷锟斤拷锟窖癸拷锟絪3
                            {
                                s4.push(c1);
                                break;
                            } else {
                                s3.push(s4.pop());
                            }
                        }
                    }
                }
            } else
                continue;

        }
        while (!s4.isEmpty())// 锟斤拷锟绞斤拷锟斤拷锟斤拷锟斤拷锟轿斤拷s4剩锟铰碉拷锟斤拷锟斤拷锟窖癸拷锟絪3
        {
            if ((char) s4.peek() == '(')
                // return "缺锟斤拷锟斤拷锟斤拷锟斤拷";
                throw new Exception();
            s3.push(s4.pop());
        }
        return count_result(s3);
    }

    private int priorityCompare(char c1, char c2) {
        switch (c1) {
            case '+':
            case '-':
                return (c2 == '*' || c2 == '/' ? -1 : 0);
            case '*':
            case '/':
                return (c2 == '+' || c2 == '-' ? 1 : 0);
        }
        return 1;
    }

    // 锟叫讹拷锟街凤拷锟角凤拷为锟斤拷锟斤拷锟斤拷锟轿拷妫拷锟斤拷锟轿拷锟�
    private boolean isOprator(Object c) {
        // TODO Auto-generated method stub
        try {
            String s = c.toString();
            char c1 = s.charAt(0);
            if (c1 == '+' || c1 == '-' || c1 == '*' || c1 == '/' || c1 == '('
                    || c1 == ')')
                return true;

        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return false;
    }

    private String count_result(Stack<Object> ob) {
        // TODO Auto-generated method stub
        Stack<Object> s1 = new Stack<Object>();// 锟斤拷缀锟斤拷锟绞秸�
        Stack<Double> s2 = new Stack<Double>();// 锟斤拷锟斤拷锟斤拷栈
        // char c1;
        // Stack<Character> s3=new Stack<Character>();//锟斤拷锟斤拷锟斤拷栈

        while (!ob.isEmpty())// 锟斤拷锟斤拷锟斤拷锟秸伙拷锟斤拷锟窖癸拷锟�
        {
            s1.push(ob.pop());
        }
        while (!s1.isEmpty()) {
            if (!isOprator(s1.peek()))// 锟斤拷锟斤拷锟角诧拷锟斤拷锟斤拷压锟斤拷s2栈
            {
                s2.push((Double) s1.pop());
            } else {
                Object c = s1.pop();
                String s = c.toString();
                char c1 = s.charAt(0);
                s2.push(cout(s2.pop(), s2.pop(), c1));
            }
        }
        return Double.toString(s2.peek());

    }

    private Double cout(double s1, double s2, char s3) {
        double result = 0;
        switch (s3) {
            case '+':
                result = s2 + s1;
                break;
            case '-':
                result = s2 - s1;
                break;
            case '*':
                result = s2 * s1;
                break;
            case '/':
                result = s2 / s1;
                break;
        }
        return result;
    }
}
