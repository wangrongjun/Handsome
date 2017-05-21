package com.wang.music_player.calculator;

import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CalculatorListener implements OnItemClickListener {

    private SimpleAdapter adapter;
    private TextView showText;
    String sign;
    String number;
    String signs[];
    double result = 0;
    boolean hadClickEqual = false;

    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // TODO Auto-generated method stub
        String pressingButtonText = adapter.getItem(position).toString();
        pressingButtonText = pressingButtonText.substring(8,
                pressingButtonText.length() - 1);

        if (pressingButtonText.equals("CE")) {
            showText.setText("");

        } else if (pressingButtonText.equals("=")) {
            String expression = "";
            String result = null;
            try {
                expression = showText.getText().toString();
                result = calculate(expression);
                hadClickEqual = true;
            } catch (Exception e) {
                // TODO Auto-generated catch block
                result = null;
                if (hadClickEqual == false && expression.length() > 0) {
                    showText.setText("表达式出错");
                }
            } finally {
                if (result != null)
                    showText.append(" = " + result);
            }

        } else if (pressingButtonText.equals("Del")) {
            String s = showText.getText().toString();
            if (s != null && s.length() - 1 >= 0 && hadClickEqual == false) {
                showText.setText(s.substring(0, s.length() - 1));
            }

        } else {
            if (hadClickEqual == false) {
                showText.append(pressingButtonText);
            } else {
                hadClickEqual = false;
                showText.setText(pressingButtonText);
            }
        }
    }

    private String calculate(String expression) throws Exception {
        CalculatorFromExample calculatorFromExample = new CalculatorFromExample();
        String result = calculatorFromExample.suffix_expression(expression);
        return result;
    }

    public void setAdapter(SimpleAdapter adapter) {
        this.adapter = adapter;
    }

    public void setTextView(TextView showText) {
        this.showText = showText;
    }

    public void setSigns(String[] signs) {
        this.signs = signs;
    }

    private boolean isBracketMatch() {
        return true;
    }

}
