package com.spark.sksqlitemanger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author Spark
 * @package com.spark.sksqlitemanger
 * @fileName SKStatDao
 * @date 2018/9/6
 * @describe 统计sqlite的dao层
 */
public class SKStatDao {

    private static SKStatDao instance;
    private SKSQLiteHelper sqlHelper;

    public SKStatDao(Context ctx) {
        sqlHelper = new SKSQLiteHelper(ctx, SKSQLiteConst.statDBName,null, SKSQLiteConst.statDBVersion, SKSQLiteConst.statTableSql);
    }

    public static synchronized SKStatDao getInstance(Context ctx) {
        if (instance == null) {
            instance = new SKStatDao(ctx);
        }
        return instance;
    }

    public Cursor getAll(String where, String orderBy) {
        StringBuilder buf = new StringBuilder("SELECT * FROM " + SKSQLiteConst.statTableName);
        if (where != null) {
            buf.append(" WHERE ");
            buf.append(where);
        }
        if (orderBy != null) {
            buf.append(" ORDER BY ");
            buf.append(orderBy);
        }
        return sqlHelper.getReadableDatabase().rawQuery(buf.toString(), null);
    }

    public void insertToStatTable(ContentValues cv) {

//        getWritableDatabase().execSQL("insert into " + SKSQLiteConst.statTableName + "(name, phone) values (?,?)",
//                new String[]{(String) cv.get("name"), (String) cv.get("phone")});
        sqlHelper.getReadableDatabase().insert(SKSQLiteConst.statTableName, null, cv);
    }

    public void updateStatTable(ContentValues cv, String name) {
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        db.update(SKSQLiteConst.statTableName, cv, "name=?", new String[]{name});
    }


    public void deleteAll() {
        sqlHelper.getReadableDatabase().execSQL("delete from " + SKSQLiteConst.statTableName);
    }
}
