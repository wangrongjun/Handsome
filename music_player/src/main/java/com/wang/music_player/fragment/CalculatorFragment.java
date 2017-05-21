package com.wang.music_player.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.wang.music_player.R;
import com.wang.music_player.calculator.CalculatorListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//第2界面：计算器界面
public class CalculatorFragment extends Fragment {

    private View view;

    private SimpleAdapter adapter;
    private GridView gridView;
    private TextView showText;
    // private Dialog dialog;
    // private Intent intent;
    private List<Map<String, Object>> dataList;
    String sign[] = {"(", ")", "Del", "CE", "1", "2", "3", "+", "4", "5", "6",
            "-", "7", "8", "9", "*", ".", "0", "=", "/"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.calculator_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        init();
    }

    private void init() {
        gridView = (GridView) view.findViewById(R.id.gridView);
        showText = (TextView) view.findViewById(R.id.showText);

        dataList = new ArrayList<Map<String, Object>>();

        adapter = new SimpleAdapter(getContext(), getData(),
                R.layout.calculator_button_layout, new String[]{"button"},
                new int[]{R.id.item});
        gridView.setAdapter(adapter);
        CalculatorListener listener = new CalculatorListener();
        listener.setAdapter(adapter);
        listener.setTextView(showText);
        listener.setSigns(sign);
        gridView.setOnItemClickListener(listener);

        // intent = getIntent();
        // showMessageDialog(intent.getStringExtra("username"));
    }

    private List<Map<String, Object>> getData() {
        for (int i = 0; i < sign.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("button", sign[i]);
            dataList.add(map);
        }

        return dataList;
    }

    // private void showMessageDialog(String username) {
    // dialog = new Dialog(getActivity());
    // dialog.setTitle("用户名：" + username);
    // dialog.setContentView(R.layout.user_message_dialog);
    // Button btn = (Button) findViewById(R.id.userMesDiaBtn);
    // TextView text = (TextView) findViewById(R.id.userMesDiaText);
    // dialog.show();
    // }
}
