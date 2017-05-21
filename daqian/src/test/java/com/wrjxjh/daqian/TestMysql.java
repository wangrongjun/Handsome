package com.wrjxjh.daqian;

import com.wang.db.SqlEntityUtil;
import com.wang.db.SqlUtil;
import com.wang.java_util.DateUtil;
import com.wang.java_util.DebugUtil;
import com.wang.java_util.MathUtil;
import com.wrjxjh.daqian.bean.Comment;
import com.wrjxjh.daqian.bean.Sign;
import com.wrjxjh.daqian.bean.ThumbUp;
import com.wrjxjh.daqian.bean.User;
import com.wrjxjh.daqian.util.dbhelper.MysqlDbHelper;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;

/**
 * by wangrongjun on 2017/3/26.
 */
public class TestMysql {

    private static MysqlDbHelper dbHelper;

    @BeforeClass
    public static void initMysqlDatabase() {
        dbHelper = new MysqlDbHelper();
    }

    @AfterClass
    public static void closeMysqlDatabase() {
        dbHelper.close();
    }

    @Test
    public void testDropAndCreateTable() throws SQLException {
        // 删除User
        String sql = SqlUtil.dropTableSql(User.class.getSimpleName());
        DebugUtil.println(sql);
        dbHelper.execute(sql);
        // 创建User
        sql = SqlEntityUtil.createTableSql(User.class);
        DebugUtil.println(sql);
        dbHelper.execute(sql);

        // 删除Sign
        sql = SqlUtil.dropTableSql(Sign.class.getSimpleName());
        DebugUtil.println(sql);
        dbHelper.execute(sql);
        // 创建Sign
        sql = SqlEntityUtil.createTableSql(Sign.class);
        DebugUtil.println(sql);
        dbHelper.execute(sql);

        // 删除Comment
        sql = SqlUtil.dropTableSql(Comment.class.getSimpleName());
        DebugUtil.println(sql);
        dbHelper.execute(sql);
        // 创建Comment
        sql = SqlEntityUtil.createTableSql(Comment.class);
        DebugUtil.println(sql);
        dbHelper.execute(sql);

        // 删除ThumbUp
        sql = SqlUtil.dropTableSql(ThumbUp.class.getSimpleName());
        DebugUtil.println(sql);
        dbHelper.execute(sql);
        // 创建ThumbUp
        sql = SqlEntityUtil.createTableSql(ThumbUp.class);
        DebugUtil.println(sql);
        dbHelper.execute(sql);
    }

    @Test
    public void testInsert() throws SQLException {

        // 创建用户wang和rong
        String sql = SqlEntityUtil.insertSql(new User("wang", "123", 0, "nini"));
        dbHelper.execute(sql);
        sql = SqlEntityUtil.insertSql(new User("rong", "123", 1, "xixi"));
        dbHelper.execute(sql);

        // 创建签到记录若干条
        for (int i = 0; i < 10; i++) {
            int userId = i % 2 + 1;
            String username = userId == 1 ? "wang" : "rong";
            String date = DateUtil.getCurrentDate();
            String time = "22:22:" + (i < 10 ? "0" : "") + i;
            Sign sign = new Sign(userId, date, time, "我是" + username + " " + (i + 1));
            sql = SqlEntityUtil.insertSql(sign);
            dbHelper.execute(sql);
        }

        // 创建若干条评论
        for (int i = 0; i < 30; i++) {
            int userId = i % 2 + 1;
            int signId = i / 3 + 1;
            String username = userId == 1 ? "wang" : "rong";
            String date = DateUtil.getCurrentDate();
            String time = "11:11:" + (i < 10 ? "0" : "") + i;

            Comment comment = new Comment(userId, signId, date, time, "我爱" + username + (i + 1));
            sql = SqlEntityUtil.insertSql(comment);

            DebugUtil.println(sql);

            dbHelper.execute(sql);
        }

        // 创建若干点赞
        for (int i = 0; i < 10; i++) {
            int random = MathUtil.random(0, 6);
            for (int j = 0; j < random; j++) {
                int userId = i % 2 + 1;
                ThumbUp thumbUp = new ThumbUp(userId, i + 1);
                sql = SqlEntityUtil.insertSql(thumbUp);
                dbHelper.execute(sql);
            }
        }

    }

    @Test
    public void testAll() throws SQLException {
        testDropAndCreateTable();
        testInsert();
    }

}
