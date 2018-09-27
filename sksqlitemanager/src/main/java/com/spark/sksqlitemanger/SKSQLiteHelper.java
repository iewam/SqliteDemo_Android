package com.spark.sksqlitemanger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Spark
 * @package com.spark.sksqlitemanger
 * @fileName SKSQLiteHelper
 * @date 2018/8/31
 * @describe
 */
public class SKSQLiteHelper extends SQLiteOpenHelper {

    private String tableSql;

    public SKSQLiteHelper(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version, String tableSql) {
        super(context, dbName, factory, version);
        this.tableSql = tableSql;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.e(SKSQLiteConst.TAG, "oncreate --" + sqLiteDatabase.toString() + " version:" + sqLiteDatabase.getVersion());
//        String sql = "CREATE TABLE " + SKSQLiteConst.statTableName + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone TEXT)";
        sqLiteDatabase.execSQL(tableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.e(SKSQLiteConst.TAG, "onUpgrade --" + " oldVersion:" + oldVersion + "-- newVersion:" + newVersion);
        if (oldVersion == 1 && newVersion == 2) {
            sqLiteDatabase.execSQL("ALTER TABLE " + SKSQLiteConst.statTableName + " ADD note TEXT");
        }
    }
}
