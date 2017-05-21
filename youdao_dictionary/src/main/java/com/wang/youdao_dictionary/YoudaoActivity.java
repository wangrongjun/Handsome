package com.wang.youdao_dictionary;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.java_util.DebugUtil;
import com.wang.java_util.HttpUtil;
import com.wang.java_util.TextUtil;

/**
 * by wangrongjun on 2017/3/2.
 */
public class YoudaoActivity extends Activity implements View.OnClickListener {

    private EditText etInputWord;
    private Button btnQuery;
    private ProgressBar progressBar;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youdao);
        initView();
    }

    private void initView() {
        etInputWord = (EditText) findViewById(R.id.et_input_word);
        Button btnClear = (Button) findViewById(R.id.btn_clear);
        btnQuery = (Button) findViewById(R.id.btn_query);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        tvResult = (TextView) findViewById(R.id.tv_result);
        btnClear.setOnClickListener(this);
        btnQuery.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_clear) {
            etInputWord.setText("");
            tvResult.setText("");

        } else if (view.getId() == R.id.btn_query) {
            String word = etInputWord.getText().toString().trim();
            if (!TextUtil.isEmpty(word)) {
                startSearch(word);
            } else {
                Toast.makeText(this, "请输入单词", Toast.LENGTH_SHORT).show();
            }
        }
    }
    
/*
    private void parseXMLResult(Document paramDocument) {

        String result = "基本释义:\n\n";

        try {
            Element localElement = paramDocument.getDocumentElement();
            NodeList basic = ((Element) ((Element) localElement.getElementsByTagName("basic").item(0)).getElementsByTagName("explains").item(0)).getElementsByTagName("ex");
            NodeList localNodeList2 = ((Element) ((Element) localElement.getElementsByTagName("web").item(0)).getElementsByTagName("explain").item(0)).getElementsByTagName("ex");
            int i = 0;
            if (i >= basic.getLength()) {
                tvResult.append("\n\n\n\n\n网络释义:\n\n");
            }
            for (int j = 0; ; j++) {
                if (j >= localNodeList2.getLength()) {
//                    return;
                    result += basic.item(i).getTextContent() + "\n\n";
                    i++;
                    break;
                }
                tvResult.append(localNodeList2.item(j).getTextContent() + "\n\n");
            }
            return;
        } catch (Exception localException) {
            Toast.makeText(this, "该单词不存在", Toast.LENGTH_SHORT).show();
        }
    }*/

    private void startSearch(final String word) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
                btnQuery.setClickable(false);
            }

            @Override
            protected String doInBackground(Void... params) {
                HttpUtil.Result r = HttpUtil.request(Youdao.getUrl(word));
                if (r.state == HttpUtil.OK) {
                    return r.result;
                } else {
                    DebugUtil.println(r.result);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String json) {
                progressBar.setVisibility(View.INVISIBLE);
                btnQuery.setClickable(true);
                if (!TextUtil.isEmpty(json)) {
                    String showText = Youdao.parseJson(json);
                    tvResult.setText(showText);
                } else {
                    Toast.makeText(YoudaoActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                }
            }

        }.execute();

    }

}
