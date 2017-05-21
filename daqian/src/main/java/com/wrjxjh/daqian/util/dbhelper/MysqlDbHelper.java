package com.wrjxjh.daqian.util.dbhelper;

import com.wang.db.Dao;
import com.wang.db.SqlUtil;
import com.wang.db.basis.Where;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * by wangrongjun on 2017/3/26.
 */
public class MysqlDbHelper implements DbHelper {

    private static final String driver = "com.mysql.jdbc.Driver";

    private String url;
    private String username;
    private String password;
    private Connection connection;

    public MysqlDbHelper() {
        this.username = "root";
        this.password = "21436587";
        String mysqlDbName = "daqian";
        this.url = "jdbc:mysql://wangrongjun.cn:3306/" + mysqlDbName;
    }

    private Connection getConnection() throws SQLException {
        if (connection == null) {
            try {
                Class.forName(driver);
                connection = DriverManager.getConnection(url, username, password);
            } catch (Exception e) {
                throw new SQLException(e);
            }
        }
        return connection;
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection = null;
    }

    @Override
    public <T> List<T> executeQuery(Class<T> entityClass, String sql) throws SQLException {
        Statement statement = getConnection().createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<T> result = Dao.getResult(entityClass, rs);
        statement.close();
        return result;
    }

    @Override
    public void execute(String sql) throws SQLException {
        Statement statement = getConnection().createStatement();
        statement.execute(sql);
        statement.close();
    }

    @Override
    public int queryCount(String tableName, Where where) throws SQLException {
        String sql = SqlUtil.queryCountSql(tableName, where);
        Statement statement = getConnection().createStatement();
        ResultSet rs = statement.executeQuery(sql);
        return Dao.getCount(rs);
    }

}
