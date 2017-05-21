package com.wrjxjh.daqian.data.remote;

/**
 * by wangrongjun on 2017/3/7.
 */
public class BaseDataSqliteCopy /*implements IBaseData*/ {
    
/*
    @Override
    public Result<User> login(String username, String password) {
        Result<User> result;
        SQLiteDatabase db = null;
        try {
            SqliteDbHelper dbHelper = new SqliteDbHelper();
            db = dbHelper.getReadableDatabase();
            String sql = SqlEntityUtil.querySql(User.class, "username", username, false);
            DebugUtil.println(sql);
            Cursor cursor = db.rawQuery(sql, null);
            List<User> userList = SqliteUtil.getResult(User.class, cursor);
            cursor.close();
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
            if (db != null) {
                db.close();
            }
        }
        return result;
    }

    @Override
    public Result register(User user) {
        Result result;
        SQLiteDatabase db = null;
        try {
            SqliteDbHelper dbHelper = new SqliteDbHelper();
            db = dbHelper.getWritableDatabase();

            String sql = SqlEntityUtil.querySql(User.class, "username", user.getUsername(), false);
            DebugUtil.println(sql);
            Cursor cursor = db.rawQuery(sql, null);
            List<User> userList = SqliteUtil.getResult(User.class, cursor);
            cursor.close();
            if (userList.size() > 0) {
                result = new Result(SC.ERROR_REGISTER_USER_EXISTS);
            } else {
                sql = SqlEntityUtil.insertSql(user);
                DebugUtil.println(sql);
                db.execSQL(sql);
                result = new Result(SC.OK);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            result = new Result(SC.ERROR_SERVER_UNKNOWN);
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return result;
    }

    @Override
    public Result<Boolean> isTodaySigned(int userId) {
        Result<Boolean> result;
        SQLiteDatabase db = null;
        try {
            SqliteDbHelper dbHelper = new SqliteDbHelper();
            db = dbHelper.getReadableDatabase();
            String sql = SqlEntityUtil.querySql(
                    Sign.class, "userId", userId + "", "signDate", DateUtil.getCurrentDate());
            Cursor cursor = db.rawQuery(sql, null);
            List<Sign> signList = SqliteUtil.getResult(Sign.class, cursor);
            if (signList.size() > 0) {
                result = new Result<>(SC.OK, true);
            } else {
                result = new Result<>(SC.OK, false);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            result = new Result<>(SC.ERROR_SERVER_UNKNOWN);
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return result;
    }

    @Override
    public Result<List<SignListResult>> getTodaySignedList() {
        Result<List<SignListResult>> result = null;
        List<SignListResult> signListResultList = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            SqliteDbHelper dbHelper = new SqliteDbHelper();
            db = dbHelper.getReadableDatabase();
            String sql = SqlEntityUtil.querySql(Sign.class, "signDate", DateUtil.getCurrentDate(), false);
            Cursor cursor = db.rawQuery(sql, null);
            List<Sign> signList = SqliteUtil.getResult(Sign.class, cursor);
            cursor.close();
            for (Sign sign : signList) {
                SignListResult signListResult;
                // 1.查询对应的签到者
                sql = SqlEntityUtil.querySql(User.class, "userId", sign.getUserId() + "", false);
                cursor = db.rawQuery(sql, null);
                List<User> userList = SqliteUtil.getResult(User.class, cursor);
                cursor.close();

                if (userList.size() > 0) {
                    // 2.查询点赞数目
                    sql = "select count(*) from " + ThumbUp.class.getSimpleName() +
                            " where signId=" + sign.getSignId() + " and userId=" + sign.getUserId();
                    cursor = db.rawQuery(sql, null);
                    cursor.moveToNext();
                    int thumbUpCount = cursor.getInt(0);
                    cursor.close();

                    // 3.查询评论数目
                    sql = "select count(*) from " + Comment.class.getSimpleName() +
                            " where signId=" + sign.getSignId() + " and userId=" + sign.getUserId();
                    cursor = db.rawQuery(sql, null);
                    cursor.moveToNext();
                    int commentCount = cursor.getInt(0);
                    cursor.close();

                    signListResult = new SignListResult(sign, userList.get(0), thumbUpCount,
                            commentCount);

                } else {
                    DebugUtil.println("ERROR_USER_ID_NOT_EXISTS : " + sign.getUserId());
                    result = new Result<>(SC.ERROR_USER_ID_NOT_EXISTS, null);
                    break;
                }
                cursor.close();
                signListResultList.add(signListResult);
            }

            if (result == null) {//如果没有出现ERROR_USER_ID_NOT_EXISTS的错误
                result = new Result<>(SC.OK, signListResultList);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            result = new Result<>(SC.ERROR_SERVER_UNKNOWN);
        } finally {
            db.close();
        }
        return result;
    }

    @Override
    public Result<List<SignListResult>> getSignedList(int begin, int length) {
        Result<List<SignListResult>> result = null;
        List<SignListResult> signListResultList = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            SqliteDbHelper dbHelper = new SqliteDbHelper();
            db = dbHelper.getReadableDatabase();
//            String sql = SqlEntityUtil.querySql(Sign.class, "signDate", DateUtil.getCurrentDate(), false);
            String sql = "select * from " + Sign.class.getSimpleName()
                    + " order by signDate desc,signTime desc limit " + begin + "," + length + ";";
            Cursor cursor = db.rawQuery(sql, null);
            List<Sign> signList = SqliteUtil.getResult(Sign.class, cursor);
            cursor.close();
            for (Sign sign : signList) {
                SignListResult signListResult;
                // 1.查询对应的签到者
                sql = SqlEntityUtil.querySql(User.class, "userId", sign.getUserId() + "", false);
                cursor = db.rawQuery(sql, null);
                List<User> userList = SqliteUtil.getResult(User.class, cursor);
                cursor.close();

                if (userList.size() > 0) {
                    // 2.查询点赞数目
                    sql = "select count(*) from " + ThumbUp.class.getSimpleName() +
                            " where signId=" + sign.getSignId() + " and userId=" + sign.getUserId();
                    cursor = db.rawQuery(sql, null);
                    cursor.moveToNext();
                    int thumbUpCount = cursor.getInt(0);
                    cursor.close();

                    // 3.查询评论数目
                    sql = "select count(*) from " + Comment.class.getSimpleName() +
                            " where signId=" + sign.getSignId() + " and userId=" + sign.getUserId();
                    cursor = db.rawQuery(sql, null);
                    cursor.moveToNext();
                    int commentCount = cursor.getInt(0);
                    cursor.close();

                    signListResult = new SignListResult(sign, userList.get(0), thumbUpCount,
                            commentCount);

                } else {
                    DebugUtil.println("ERROR_USER_ID_NOT_EXISTS : " + sign.getUserId());
                    result = new Result<>(SC.ERROR_USER_ID_NOT_EXISTS, null);
                    break;
                }
                cursor.close();
                signListResultList.add(signListResult);
            }

            if (result == null) {//如果没有出现ERROR_USER_ID_NOT_EXISTS的错误
                result = new Result<>(SC.OK, signListResultList);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            result = new Result<>(SC.ERROR_SERVER_UNKNOWN);
        } finally {
            db.close();
        }
        return result;
    }

    @Override
    public Result sign(int userId, String signText) {
        Result result;
        SqliteDbHelper dbHelper = null;
        SQLiteDatabase db = null;
        try {
            dbHelper = new SqliteDbHelper();
            db = dbHelper.getReadableDatabase();

            if (!isTodaySigned(userId).entity) {
                Sign sign = new Sign(
                        userId, DateUtil.getCurrentDate(), DateUtil.getCurrentTime(), signText);
                String sql = SqlEntityUtil.insertSql(sign);
                db.execSQL(sql);
                result = new Result(SC.OK);

            } else {
                result = new Result(SC.ERROR_ALREADY_SIGNED);
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = new Result<>(SC.ERROR_SERVER_UNKNOWN);
        } finally {
            db.close();
            dbHelper.close();
        }
        return result;
    }

    @Override
    public Result<List<CommentListResult>> getCommentList(int signId) {
        Result<List<CommentListResult>> result = null;
        SQLiteDatabase db = null;
        List<CommentListResult> commentListResultList = new ArrayList<>();
        try {
            SqliteDbHelper dbHelper = new SqliteDbHelper();
            db = dbHelper.getReadableDatabase();
//            String sql = SqlEntityUtil.querySql(Comment.class, "signId", signId + "", false);
            String sql = "select * from Comment where signId=" + signId +
                    " order by commentDate desc,commentTime desc;";
            Cursor cursor = db.rawQuery(sql, null);
            List<Comment> commentList = SqliteUtil.getResult(Comment.class, cursor);
            cursor.close();

            for (Comment comment : commentList) {
                sql = SqlEntityUtil.queryByIdSql(User.class,
                        comment.getUserId() + "");
                cursor = db.rawQuery(sql, null);
                List<User> userList = SqliteUtil.getResult(User.class, cursor);
                cursor.close();
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
            db.close();
        }
        return result;
    }

    @Override
    public Result<Integer> comment(int userId, int signId, String commentText) {
        Result<Integer> result = null;
        SQLiteDatabase db = null;
        try {
            SqliteDbHelper dbHelper = new SqliteDbHelper();
            db = dbHelper.getReadableDatabase();

            // 插入评论
            Comment comment = new Comment(userId, signId, DateUtil.getCurrentDate(),
                    DateUtil.getCurrentTime(), commentText);
            String sql = SqlEntityUtil.insertSql(comment);
            db.execSQL(sql);

            // 查询评论数目
            sql = "select count(*) from " + Comment.class.getSimpleName() +
                    " where signId=" + signId;
            Cursor cursor = db.rawQuery(sql, null);
            cursor.moveToNext();
            int commentCount = cursor.getInt(0);
            cursor.close();

            result = new Result<>(SC.OK, commentCount);

        } catch (Exception e) {
            e.printStackTrace();
            result = new Result<>(SC.ERROR_SERVER_UNKNOWN);
        } finally {
            db.close();
        }
        return result;
    }

    @Override
    public Result<Integer> thumbUp(int userId, int signId) {
        return null;
    }

    @Override
    public Result updatePassword(int userId, String oldPassword, String newPassword) {
        return null;
    }

    @Override
    public Result updateNickname(int userId, String nickname) {
        return null;
    }

    @Override
    public Result updateGender(int userId, int gender) {
        return null;
    }
*/
}
