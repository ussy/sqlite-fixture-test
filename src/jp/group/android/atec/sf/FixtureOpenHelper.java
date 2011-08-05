/*******************************************************************************
 * Copyright 2011 Android Test and Evaluation Club
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package jp.group.android.atec.sf;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * ライブラリテスト用の {@link SQLiteOpenHelper} 実装クラスです。
 * 
 * テストに使用するテーブル情報を記述します。
 * 
 * @author ussy
 * 
 */
public class FixtureOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "fixture.db";

    public static final int DATABASE_VERSION = 3;

    private static final String CREATE_ICON_TABLE_SQL = "" +
        "create table icon (" +
        "   _id integer primary key," +
        "   url text not null," +
        "   image blob," +
        "   created integer," +
        "   updated integer" +
        ")";

    private static final String CREATE_USER_TABLE_SQL = "" +
        "create table user (" +
        "   _id integer primary key," +
        "   name text," +
        "   weight real," +
        "   image blob," +
        "   created integer," +
        "   updated integer" +
        ")";

    private static final String DROP_ICON_TABLE_SQL = "drop table if exists icon";
    private static final String DROP_USER_TABLE_SQL = "drop table if exists user";

    public FixtureOpenHelper(Context context) {
        this(context, DATABASE_VERSION);
    }
    
    public FixtureOpenHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ICON_TABLE_SQL);
        db.execSQL(CREATE_USER_TABLE_SQL);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_ICON_TABLE_SQL);
        db.execSQL(DROP_USER_TABLE_SQL);
        onCreate(db);
    }
}
