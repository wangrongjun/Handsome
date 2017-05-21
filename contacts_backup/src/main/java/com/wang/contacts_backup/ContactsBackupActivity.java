package com.wang.contacts_backup;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wang.android_lib.util.ContactUtil;
import com.wang.android_lib.util.M;
import com.wang.java_util.DateUtil;
import com.wang.java_util.FileUtil;
import com.wang.java_util.GsonUtil;
import com.wang.java_util.SortUtil;
import com.wang.java_util.TextUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * by wangrongjun on 2017/5/21.
 */

public class ContactsBackupActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String backupDir = Environment.getExternalStorageDirectory() +
            File.separator + "backups" + File.separator;
    private String contactListJson;
    private TextView tvShow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_backup);
        initView();
        List<ContactUtil.Contact> contacts = ContactUtil.readContacts(this);
        contacts = SortUtil.sortChina(contacts, "contactName", false);
        contactListJson = GsonUtil.formatJson(contacts);
        contactListJson = contactListJson.replace("\n", "\r\n");
        tvShow.setText(contactListJson);
    }

    private void initView() {
        tvShow = (TextView) findViewById(R.id.tv_show);
        Button btnBackup = (Button) findViewById(R.id.btn_backup);
        Button btnRecover = (Button) findViewById(R.id.btn_recover);
        btnBackup.setOnClickListener(this);
        btnRecover.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_backup) {
            backup();
        } else if (view.getId() == R.id.btn_recover) {
            M.t(this, "敬请期待");
        }
    }

    private void backup() {
        String fileName = "contacts backup " + DateUtil.getCurrentDateAndTime() + ".txt";
        fileName = TextUtil.correctFileName(fileName, "");
        String backupTxtPath = backupDir + fileName;
        try {
            FileUtil.write(contactListJson, backupTxtPath);
            M.tl(this, "备份成功，保存在：\n\n" + backupTxtPath);
        } catch (IOException e) {
            e.printStackTrace();
            M.tl(this, "备份失败，" + e.toString());
        }
    }

}
