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
package jp.group.android.atec.sf.migrate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class FixtureV1MigrationOpenHelper extends FixtureMigrationOpenHelper {

    public FixtureV1MigrationOpenHelper(Context context) {
        super(context, 1);
    }

    private static final String CREATE_USER_TABLE_SQL = "" +
        "create table user (" +
        "   _id integer primary key," +
        "   name text," +
        "   weight real," +
        "   image blob," +
        "   created integer" +
        ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // noop
    }
}
