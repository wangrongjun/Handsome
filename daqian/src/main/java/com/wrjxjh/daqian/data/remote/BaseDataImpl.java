package com.wrjxjh.daqian.data.remote;

import com.wang.db.SqlEntityUtil;
import com.wang.db.SqlUtil;
import com.wang.db.basis.ValueType;
import com.wang.db.basis.Where;
import com.wang.java_util.DateUtil;
import com.wang.java_util.DebugUtil;
import com.wang.java_util.TextUtil;
import com.wrjxjh.daqian.bean.Comment;
import com.wrjxjh.daqian.bean.Sign;
import com.wrjxjh.daqian.bean.ThumbUp;
import com.wrjxjh.daqian.bean.User;
import com.wrjxjh.daqian.bean.result.CommentListResult;
import com.wrjxjh.daqian.bean.result.SignListResult;
import com.wrjxjh.daqian.constant.SC;
import com.wrjxjh.daqian.util.dbhelper.DbHelper;
import com.wrjxjh.daqian.util.dbhelper.MysqlDbHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * by wangrongjun on 2017/3/7.
 */
public class BaseDataImpl implements IBaseData {

    private static void print(String s) {
        DebugUtil.println(s);
    }

    private DbHelper createDbHelper() {
//        return new SqliteDbHelper();
        return new MysqlDbHelper();
    }

    @Override
    public Result<User> login(String username, String password) {
        DbHelper dbHelper = createDbHelper();
        Result<User> result;
        try {
            String sql = SqlEntityUtil.querySql(User.class, "username", username);
            print(sql);
            List<User> userList = dbHelper.executeQuery(User.class, sql);

            if (userList.size() > 0) {
                if (password.equals(userList.get(0).getPassword())) {
                    result = new Result<>(SC.OK, userList.get(0));
                } else {
                    result = new Result<>(SC.ERROR_LOGIN_PASSWORD);
                }
            } else {
                result = new Result<>(SC.ERROR_LOGIN_USERNAME_NOT_EXISTS);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            result = new Result<>(SC.ERROR_SERVER_UNKNOWN);
        } finally {
            dbHelper.close();
        }
        return result;
    }

    @Override
    public Result register(User user) {
        DbHelper dbHelper = createDbHelper();
        Result result;
        try {
            String sql = SqlEntityUtil.querySql(User.class, "username", user.getUsername());
            print(sql);
            List<User> userList = dbHelper.executeQuery(User.class, sql);

            if (userList.size() > 0) {
                result = new Result(SC.ERROR_REGISTER_USER_EXISTS);
            } else {
                sql = SqlEntityUtil.insertSql(user);
                print(sql);
                dbHelper.execute(sql);
                result = new Result(SC.OK);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            result = new Result(SC.ERROR_SERVER_UNKNOWN);
        } finally {
            dbHelper.close();
        }
        return result;
    }

    @Override
    public Result<Boolean> isTodaySigned(int userId) {
        DbHelper dbHelper = createDbHelper();
        Result<Boolean> result;
        try {
            String sql = SqlEntityUtil.querySql(
                    Sign.class, "userId", userId + "", "signDate", DateUtil.getCurrentDate());
            List<Sign> signList = dbHelper.executeQuery(Sign.class, sql);

            if (signList.size() > 0) {
                result = new Result<>(SC.OK, true);
            } else {
                result = new Result<>(SC.OK, false);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            result = new Result<>(SC.ERROR_SERVER_UNKNOWN);
        } finally {
            dbHelper.close();
        }
        return result;
    }

    @Override
    public Result<List<SignListResult>> getTodaySignedList() {
        DbHelper dbHelper = createDbHelper();
        Result<List<SignListResult>> result = null;
        List<SignListResult> signListResultList = new ArrayList<>();
        try {
            String sql = SqlEntityUtil.querySql(Sign.class, "signDate", DateUtil.getCurrentDate());
            List<Sign> signList = dbHelper.executeQuery(Sign.class, sql);
            for (Sign sign : signList) {
                SignListResult signListResult;
                // 1.查询对应的签到者
                sql = SqlEntityUtil.querySql(User.class, "userId", sign.getUserId() + "");
                List<User> userList = dbHelper.executeQuery(User.class, sql);

                if (userList.size() > 0) {
                    // 2.查询点赞数目
                    Where where = new Where().add("signId", sign.getSignId() + "", ValueType.INT);
                    int thumbUpCount = dbHelper.queryCount(ThumbUp.class.getSimpleName(), where);

                    // 3.查询评论数目
                    int commentCount = dbHelper.queryCount(Comment.class.getSimpleName(), where);

                    // 4.创建结果集
                    signListResult = new SignListResult(sign, userList.get(0), thumbUpCount,
                            commentCount);

                } else {
                    DebugUtil.println("ERROR_USER_ID_NOT_EXISTS : " + sign.getUserId());
                    result = new Result<>(SC.ERROR_USER_ID_NOT_EXISTS, null);
                    break;
                }

                signListResultList.add(signListResult);
            }

            if (result == null) {//如果没有出现ERROR_USER_ID_NOT_EXISTS的错误
                result = new Result<>(SC.OK, signListResultList);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            result = new Result<>(SC.ERROR_SERVER_UNKNOWN);
        } finally {
            dbHelper.close();
        }
        return result;
    }

    @Override
    public Result<List<SignListResult>> getSignedList(int begin, int length) {
        DbHelper dbHelper = createDbHelper();
        Result<List<SignListResult>> result = null;
        List<SignListResult> signListResultList = new ArrayList<>();
        try {
            List<String> orderByList = Arrays.asList("-signDate", "-signTime");
            String sql =
                    SqlUtil.querySql(Sign.class.getSimpleName(), null, orderByList, begin, length);
            List<Sign> signList = dbHelper.executeQuery(Sign.class, sql);

            for (Sign sign : signList) {
                SignListResult signListResult;
                // 1.查询对应的签到者
                sql = SqlEntityUtil.querySql(User.class, "userId", sign.getUserId() + "");
                List<User> userList = dbHelper.executeQuery(User.class, sql);

                if (userList.size() > 0) {
                    // 2.查询点赞数目
                    Where where = new Where().add("signId", sign.getSignId() + "", ValueType.INT);
                    int thumbUpCount = dbHelper.queryCount(ThumbUp.class.getSimpleName(), where);

                    // 3.查询评论数目
                    int commentCount = dbHelper.queryCount(Comment.class.getSimpleName(), where);

                    signListResult = new SignListResult(sign, userList.get(0), thumbUpCount,
                            commentCount);

                } else {
                    DebugUtil.println("ERROR_USER_ID_NOT_EXISTS : " + sign.getUserId());
                    result = new Result<>(SC.ERROR_USER_ID_NOT_EXISTS, null);
                    break;
                }

                signListResultList.add(signListResult);
            }

            if (result == null) {//如果没有出现ERROR_USER_ID_NOT_EXISTS的错误
                result = new Result<>(SC.OK, signListResultList);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            result = new Result<>(SC.ERROR_SERVER_UNKNOWN);
        } finally {
            dbHelper.close();
        }
        return result;
    }

    @Override
    public Result sign(int userId, String signText) {
        DbHelper dbHelper = createDbHelper();
        Result result;
        try {
            if (!isTodaySigned(userId).entity) {
                Sign sign = new Sign(
                        userId, DateUtil.getCurrentDate(), DateUtil.getCurrentTime(), signText);
                String sql = SqlEntityUtil.insertSql(sign);
                dbHelper.execute(sql);
                result = new Result(SC.OK);

            } else {
                result = new Result(SC.ERROR_ALREADY_SIGNED);
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = new Result<>(SC.ERROR_SERVER_UNKNOWN);
        } finally {
            dbHelper.close();
        }
        return result;
    }

    @Override
    public Result<List<CommentListResult>> getCommentList(int signId) {
        DbHelper dbHelper = createDbHelper();
        Result<List<CommentListResult>> result = null;
        List<CommentListResult> commentListResultList = new ArrayList<>();
        try {
            Where where = new Where().add("signId", signId + "", ValueType.INT);
            List<String> orderByList = Arrays.asList("-commentDate", "-commentTime");
            String sql = SqlUtil.querySql(Comment.class.getSimpleName(), where, orderByList);

            List<Comment> commentList = dbHelper.executeQuery(Comment.class, sql);

            for (Comment comment : commentList) {
                sql = SqlEntityUtil.queryByIdSql(User.class,
                        comment.getUserId() + "");
                List<User> userList = dbHelper.executeQuery(User.class, sql);
                if (userList.size() > 0) {
                    User user = userList.get(0);
                    user.setPassword(null);
                    commentListResultList.add(new CommentListResult(comment, user));
                } else {
                    DebugUtil.println("ERROR_USER_ID_NOT_EXISTS : " + comment.getUserId());
                    result = new Result<>(SC.ERROR_USER_ID_NOT_EXISTS);
                    break;
                }
            }

            result = new Result<>(SC.OK, commentListResultList);

        } catch (SQLException e) {
            e.printStackTrace();
            result = new Result<>(SC.ERROR_SERVER_UNKNOWN);
        } finally {
            dbHelper.close();
        }
        return result;
    }

    @Override
    public Result<Integer> comment(int userId, int signId, String commentText) {
        DbHelper dbHelper = createDbHelper();
        Result<Integer> result = null;
        try {
            // 插入评论
            Comment comment = new Comment(userId, signId, DateUtil.getCurrentDate(),
                    DateUtil.getCurrentTime(), commentText);
            String sql = SqlEntityUtil.insertSql(comment);
            dbHelper.execute(sql);

            // 查询评论数目
            Where where = new Where().add("signId", signId + "", ValueType.INT);
            int commentCount = dbHelper.queryCount(Comment.class.getSimpleName(), where);

            result = new Result<>(SC.OK, commentCount);

        } catch (Exception e) {
            e.printStackTrace();
            result = new Result<>(SC.ERROR_SERVER_UNKNOWN);
        } finally {
            dbHelper.close();
        }
        return result;
    }

    @Override
    public Result<Integer> thumbUp(int userId, int signId) {
        DbHelper dbHelper = createDbHelper();
        Result<Integer> result;
        try {
            Where where = new Where().add("signId", signId + "", ValueType.INT);
            int count = dbHelper.queryCount(ThumbUp.class.getSimpleName(), where);
            String sql = SqlEntityUtil.insertSql(new ThumbUp(userId, signId));
            dbHelper.execute(sql);
            result = new Result<>(SC.OK, count + 1);

        } catch (SQLException e) {
            e.printStackTrace();
            result = new Result<>(SC.ERROR_SERVER_UNKNOWN);
        } finally {
            dbHelper.close();
        }
        return result;
    }

    @Override
    public Result updatePassword(int userId, String oldPassword, String newPassword) {
        DbHelper dbHelper = createDbHelper();
        Result result;
        try {

            if (TextUtil.isEmpty(oldPassword, newPassword)) {
                return new Result(SC.ERROR_APP_UNKNOWN);
            }

            String sql = SqlEntityUtil.queryByIdSql(User.class, userId + "");
            List<User> userList = dbHelper.executeQuery(User.class, sql);
            if (userList == null || userList.size() == 0) {
                result = new Result(SC.ERROR_USER_ID_NOT_EXISTS);
            } else if (!oldPassword.equals(userList.get(0).getPassword())) {
                result = new Result(SC.ERROR_UPDATE_USER_PASSWORD_PERMISSION);
            } else {
                sql = SqlEntityUtil.updateSql(User.class, userId + "", "password", newPassword);
                dbHelper.execute(sql);
                result = new Result(SC.OK);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            result = new Result<>(SC.ERROR_SERVER_UNKNOWN);
        } finally {
            dbHelper.close();
        }
        return result;
    }

    @Override
    public Result updateNickname(int userId, String nickname) {
        return null;
    }

    @Override
    public Result updateGender(int userId, int gender) {
        return null;
    }
}
