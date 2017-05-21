package com.wrjxjh.daqian.util.dbhelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.wang.android_lib.util.SqliteUtil;
import com.wang.db.SqlEntityUtil;
import com.wang.db.SqlUtil;
import com.wang.db.basis.DbType;
import com.wang.db.basis.Where;
import com.wang.java_util.DebugUtil;
import com.wrjxjh.daqian.bean.Comment;
import com.wrjxjh.daqian.bean.Sign;
import com.wrjxjh.daqian.bean.ThumbUp;
import com.wrjxjh.daqian.bean.User;
import com.wrjxjh.daqian.constant.SC;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

/**
 * by wangrongjun on 2017/3/7.
 */
public class SqliteDbHelper extends SQLiteOpenHelper implements DbHelper {

    private static final String dir = Environment.getExternalStorageDirectory() + File.separator;
    public static Context context;

    private SQLiteDatabase db;

    public SqliteDbHelper() {
        super(context, dir + "daqian.db", null, 1);
        SqlUtil.dbType = DbType.SQLITE;
    }

    /**
     * 如果不存在daqian.db这个文件，才会执行onCreate
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        String sql = SqlEntityUtil.createTableSql(User.class);
        DebugUtil.println(sql);
        db.execSQL(sql);

        sql = SqlEntityUtil.createTableSql(Sign.class);
        DebugUtil.println(sql);
        db.execSQL(sql);

        sql = SqlEntityUtil.createTableSql(Comment.class);
        DebugUtil.println(sql);
        db.execSQL(sql);

        sql = SqlEntityUtil.createTableSql(ThumbUp.class);
        DebugUtil.println(sql);
        db.execSQL(sql);
/*
        List<String> list = SqlEntityUtil.createReferenceSqlList(Sign.class);
        for (String s : list) {
            DebugUtil.println(s);
            db.execSQL(s);
        }

        list = SqlEntityUtil.createReferenceSqlList(Comment.class);
        for (String s : list) {
            DebugUtil.println(s);
            db.execSQL(s);
        }

        list = SqlEntityUtil.createReferenceSqlList(ThumbUp.class);
        for (String s : list) {
            DebugUtil.println(s);
            db.execSQL(s);
        }
*/
    }

    /**
     * @return SC.OK 登陆成功
     * SC.ERROR_LOGIN_PASSWORD 密码错误
     * SC.ERROR_LOGIN_USERNAME_NOT_EXISTS 用户名不存在
     * //TODO delete
     */
    public int login111(String username, String password) throws SQLException {
        String sql = SqlEntityUtil.querySql(User.class, "username", username);
        Cursor cursor = db.rawQuery(sql, null);
        List<User> userList = SqliteUtil.getResult(User.class, cursor);
        if (userList.size() > 0) {
            if (password.equals(userList.get(0).getPassword())) {
                return SC.OK;
            } else {
                return SC.ERROR_LOGIN_PASSWORD;
            }
        } else {
            return SC.ERROR_LOGIN_USERNAME_NOT_EXISTS;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    public <T> List<T> executeQuery(Class<T> entityClass, String sql) throws SQLException {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<T> result = SqliteUtil.getResult(entityClass, cursor);
        db.close();
        return result;
    }

    @Override
    public void execute(String sql) throws SQLException {
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL(sql);
        db.close();
    }

    @Override
    public int queryCount(String tableName, Where where) throws SQLException {
        String sql = SqlUtil.queryCountSql(tableName, where);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        int count = SqliteUtil.getCount(cursor);
        cursor.close();
        return count;
    }
}
