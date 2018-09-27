package com.spark.sksqlitemanger;

/**
 * @author Spark
 * @package com.spark.sksqlitemanger
 * @fileName SKSQLiteConst
 * @date 2018/8/31
 * @describe
 */
public class SKSQLiteConst {
    public static final String TAG = "SKSQLITE";

    // stat统计
    public static final int statDBVersion = 1;
    public static final String statDBName = "stat.db";
    public static final String statTableName = "stat";

    public static final String statTableColumn_Name = "name";
    public static final String statTableColumn_Phone = "phone";

    public static final String statTableSql = "CREATE TABLE " + SKSQLiteConst.statTableName + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone TEXT)";



}
