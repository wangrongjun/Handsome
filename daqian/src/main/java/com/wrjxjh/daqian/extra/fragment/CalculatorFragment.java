package com.wrjxjh.daqian.extra.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wrjxjh.daqian.R;

public class CalculatorFragment extends Fragment {
    private TextView tv;
    private String sign;
    private double result;
    private boolean havePressSignBtn;
    private boolean isBegin;

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator, container,
                false);
        init(view);
        return view;
    }

    private void init(View view) {
        tv = (TextView) view.findViewById(R.id.tv_show_result);
        reset();
    }

    public void doClick(View v) {
        String text = ((TextView) v).getText().toString();
        switch (text.charAt(0)) {
            case 'C':
                reset();
                break;

            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                if (isBegin) {
                    reset();
                    isBegin = false;
                }
                if (havePressSignBtn) {
                    tv.setText(text);
                    havePressSignBtn = false;
                } else {
                    tv.append(text);
                }
                break;

            case '+':
            case '-':
            case '*':
            case '/':
            case '=':
                if (isBegin) {
                    reset();
                    isBegin = false;
                    break;
                }
                if (!text.equals("=")) {
                    havePressSignBtn = true;
                }
                double temp = 0;
                try {
                    temp = Double.parseDouble(tv.getText().toString());
                } catch (NumberFormatException e) {
                    reset();
                    tv.setText("表达式出错");
                    break;
                }
                if (sign.length() < 1) {
                    sign = text;
                    result = temp;
                } else {
                    int status = calculate(sign, temp);
                    sign = text;
                    if (text.equals("=")) {
                        if (havePressSignBtn) {
                            reset();
                            tv.setText("表达式出错");
                            break;
                        }
                        if (status == -1) {
                            tv.setText("错误: 除数为0");
                        } else {
                            tv.setText(result + "");
                        }
                        isBegin = true;
                    }
                }

                break;

        }
    }

    private int calculate(String sign, double temp) {
        Toast.makeText(getActivity(), sign, Toast.LENGTH_SHORT).show();
        if (sign.equals("+")) {
            Log.i("info", "+");
            result += temp;
        } else if (sign.equals("-")) {
            result -= temp;
        } else if (sign.equals("*")) {
            result *= temp;
        } else if (sign.equals("/")) {
            if (temp < 0.000000001) {
                return -1;
            }
            result /= temp;
        }

        return 0;
    }

    private void reset() {
        tv.setText("");
        sign = "";
        result = 0;
        havePressSignBtn = false;
        isBegin = true;
    }

}
