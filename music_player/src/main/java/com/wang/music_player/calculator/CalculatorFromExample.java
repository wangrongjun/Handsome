package com.wang.music_player.calculator;

import java.util.Stack;

public class CalculatorFromExample {
    public String suffix_expression(String expression) throws Exception// ��׺���ʽת����׺���ʽ���沨��ʽ��
    {
        // Stack<Double> s2=new Stack<Double>();//�������ջ
        Stack<Object> s3 = new Stack<Object>();// ��Ž��ջ
        Stack<Character> s4 = new Stack<Character>();// ��Ų�����ջ
        int len = expression.length();//

        char c1;
        double number;
        int m, n = -1;
        for (int i = 0; i < len; i++) {
            c1 = expression.charAt(i);
            if (isOprator(c1) || (i == len - 1))// ����������ǰ�����������s3ջ����������s4ջ
            {
                if (i == len - 1 && (!isOprator(c1)))// �����һλ�Ҳ��ǲ�����ʱ����ǰ�����ѹջ
                    m = i + 1;
                else
                    m = i;
                // ��������ջ,��ǰ������һ��������м���ַ�ת��Ϊdouble
                for (int j = i - 1; j >= 0; j--) {
                    if (isOprator(expression.charAt(j))) {
                        n = j;
                        break;
                    }
                    n = j - 1;
                }
                if (m != n + 1)// ֻ�е�������ֵ����ʱ�м�Ż��в�����
                {
                    number = Double.parseDouble(expression.substring(n + 1, m));
                    s3.push(number);
                }
                // �������ջ
                if (i == 0 && (c1 != '('))// �����ʽ��һ���ַ��Ϊ������Ҳ���������ʱ�����ر��ʽ����
                {
                    // return "���ʽ����";
                    throw new Exception();
                } else if (isOprator(c1))// ���ǲ�����ʱ
                {
                    while (true) {
                        if (s4.isEmpty() || s4.peek() == '(' || c1 == '(')// ���ջΪ�ջ���ջ��Ԫ��Ϊ������c1Ϊ��ʱ����ֱ�ӽ������ѹ��ջ��
                        {
                            s4.push(c1);
                            break;
                        } else if (c1 == ')')// ��c1Ϊ��ʱ�����ε���s4�е������ѹ��s3��ֱ������������һ������
                        {
                            while (s4.peek() != '(') {
                                s3.push(s4.pop());
                                if (s4.isEmpty())// �������в�Ϊ������֮���ջΪ�գ�����ʽ���Ϸ�
                                {
                                    // return "ȱ��������";
                                    throw new Exception();
                                }
                            }
                            s4.pop();// ������
                            break;
                        } else {
                            if (priorityCompare(c1, s4.peek()) == 1)// �ж����ȼ������ȼ�����ջ�����ȼ��ͽ�ջ�������ѹ��s3
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
        while (!s4.isEmpty())// ���ʽ��������ν�s4ʣ�µ������ѹ��s3
        {
            if ((char) s4.peek() == '(')
                // return "ȱ��������";
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

    // �ж��ַ��Ƿ�Ϊ�������Ϊ�棬����Ϊ��
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
        Stack<Object> s1 = new Stack<Object>();// ��׺���ʽջ
        Stack<Double> s2 = new Stack<Double>();// ������ջ
        // char c1;
        // Stack<Character> s3=new Stack<Character>();//������ջ

        while (!ob.isEmpty())// �������ջ����ѹ��
        {
            s1.push(ob.pop());
        }
        while (!s1.isEmpty()) {
            if (!isOprator(s1.peek()))// �����ǲ�����ѹ��s2ջ
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
