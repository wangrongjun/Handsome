package com.wrjxjh.daqian.extra.activity;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.WebView;

import com.wang.android_lib.util.SqliteUtil;
import com.wang.java_util.FileUtil;
import com.wang.java_util.HtmlCreateUtil;
import com.wrjxjh.daqian.R;
import com.wrjxjh.daqian.bean.Comment;
import com.wrjxjh.daqian.bean.Sign;
import com.wrjxjh.daqian.bean.ThumbUp;
import com.wrjxjh.daqian.bean.User;
import com.wrjxjh.daqian.util.dbhelper.SqliteDbHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * by wangrongjun on 2017/3/9.
 */
public class ShowDatabaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_database);
        initView();
    }

    private void initView() {
        WebView wvTable = (WebView) findViewById(R.id.wv_table);
        String path = Environment.getExternalStorageDirectory() + File.separator + "db_table.html";
        try {
            FileUtil.write(getHtmlFromDb(), path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        wvTable.loadUrl(String.valueOf(Uri.fromFile(new File(path))));
    }

    private String getHtmlFromDb() {
        SqliteDbHelper dbHelper = new SqliteDbHelper();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        List<HtmlCreateUtil.Table> tableList = new ArrayList<>();
        tableList.add(SqliteUtil.createHtmlTable(User.class, db));
        tableList.add(SqliteUtil.createHtmlTable(Sign.class, db));
        tableList.add(SqliteUtil.createHtmlTable(Comment.class, db));
        tableList.add(SqliteUtil.createHtmlTable(ThumbUp.class, db));

        db.close();
        dbHelper.close();

        return HtmlCreateUtil.createHtml(tableList);
    }

}
