package com.wrjxjh.daqian.util.dbhelper;

import com.wang.db.basis.Where;

import java.sql.SQLException;
import java.util.List;

/**
 * by wangrongjun on 2017/3/26.
 */
public interface DbHelper {

    void close();

    <T> List<T> executeQuery(Class<T> entityClass, String sql) throws SQLException;

    void execute(String sql) throws SQLException;

    int queryCount(String tableName, Where where) throws SQLException;

}
