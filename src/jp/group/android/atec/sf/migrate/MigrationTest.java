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

import java.util.ArrayList;
import java.util.List;

import jp.group.android.atec.sf.data.ColumnInfo;
import jp.group.android.atec.sf.importer.FileType;
import jp.group.android.atec.sf.unit.DatabaseTestCase;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MigrationTest extends DatabaseTestCase {

    @Override
    protected SQLiteOpenHelper createSQLiteOpenHelper() {
        // 最初のバージョンのデータベースでテストを始める
        return new FixtureV1MigrationOpenHelper(getDatabaseContext());
    }

    public void testV1Migration() {
        importData(FileType.Csv, "migrationTest");

        ColumnInfo[] infos = loadColumnInfos("user");
        assertEquals(5, infos.length);
        assertEquals("_id", infos[0].getName());
        assertEquals("name", infos[1].getName());
        assertEquals("weight", infos[2].getName());
        assertEquals("image", infos[3].getName());
        assertEquals("created", infos[4].getName());

        // 最新のデータベースを開いてアップグレード
        replaceSQLiteOpenHelper(new FixtureMigrationOpenHelper(getDatabaseContext()));

        infos = loadColumnInfos("user");
        assertEquals(6, infos.length);
        assertEquals("_id", infos[0].getName());
        assertEquals("name", infos[1].getName());
        assertEquals("weight", infos[2].getName());
        assertEquals("image", infos[3].getName());
        assertEquals("created", infos[4].getName());
        assertEquals("updated", infos[5].getName()); // updated ができてる

        List<User> users = findAllUser(getSQLiteDatabase());
        assertEquals(2, users.size());
        assertEquals(1, users.get(0).id);
        assertEquals("created と updated が同じ値になっていること", 1306002629, users.get(0).updated);
        assertEquals("created と updated が同じ値になっていること", 1306012629, users.get(1).updated);
    }

    protected List<User> findAllUser(SQLiteDatabase db) {
        Cursor cursor =
            db.query(
                "user",
                new String[] { "_id", "name", "weight", "image", "created", "updated" },
                null,
                null,
                null,
                null,
                "_id");

        List<User> users = new ArrayList<User>();
        try {
            while (cursor.moveToNext()) {
                User user = new User();
                user.id = cursor.getInt(0);
                user.name = cursor.getString(1);
                user.weight = cursor.getDouble(2);
                user.image = cursor.getBlob(3);
                user.created = cursor.getInt(4);
                user.updated = cursor.getInt(5);

                users.add(user);
            }
        } finally {
            cursor.close();
        }

        return users;
    }

    private static class User {
        public int id;
        @SuppressWarnings("unused")
        public String name;
        @SuppressWarnings("unused")
        public double weight;
        @SuppressWarnings("unused")
        public byte[] image;
        @SuppressWarnings("unused")
        public int created;
        public int updated;
    }
}
