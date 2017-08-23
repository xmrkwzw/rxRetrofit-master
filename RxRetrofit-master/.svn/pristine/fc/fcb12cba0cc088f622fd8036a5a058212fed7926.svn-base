package com.xmjj.rxretrofit_master.db;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;

/**
 * Created by Somewereb on 2017/8/18.
 */
@Database(name = AppDataBase.NAME, version = AppDataBase.VERSION)
public class AppDataBase {

    //数据库名称
    public static final String NAME = "AppDatabase";
    //数据库版本号
    public static final int VERSION = 3;


    /**
     * 数据库的修改：
     * 1、PatientSession 表结构的变化
     * 2、增加表字段，考虑到版本兼容性，老版本不建议删除字段
     */
    @Migration(version = VERSION, database = AppDataBase.class)
    public static class Migration2UserData extends AlterTableMigration<DBFlowModel> {

        public Migration2UserData(Class<DBFlowModel> table) {
            super(table);
        }

        /**
         * 每次增加字段需要版本加一并且只能加一个字段
         */
        @Override
        public void onPreMigrate() {
            addColumn(SQLiteType.INTEGER, "grade");
        }
    }



}
