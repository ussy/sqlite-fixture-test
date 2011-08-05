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
package jp.group.android.atec.sf.importer.csv;

import java.util.ArrayList;
import java.util.List;

import jp.group.android.atec.sf.FixtureOpenHelper;
import jp.group.android.atec.sf.importer.FileType;
import jp.group.android.atec.sf.importer.ImportException;
import jp.group.android.atec.sf.unit.DatabaseTestCase;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class SQLiteCsvImporterTest extends DatabaseTestCase {

    @Override
    protected SQLiteOpenHelper createSQLiteOpenHelper() {
        return new FixtureOpenHelper(getDatabaseContext());
    }

    public void testImportData() {
        importData(FileType.Csv, "sqliteCsvImportTest/normal");

        List<User> icons = loadUsers();
        assertEquals(2, icons.size());

        User first = icons.get(0);
        assertEquals(1, first.id);
        assertEquals("ussy", first.name);
        assertEquals(85.5d, first.weight);
        Bitmap image = BitmapFactory.decodeByteArray(first.image, 0, first.image.length);
        assertNotNull(image);
        assertEquals(1306002629, first.created);

        User second = icons.get(1);
        assertEquals(2, second.id);
        assertEquals("デモユーザーさん", second.name);
    }

    public void testImportSJISData() {
        importData(FileType.Csv, "sqliteCsvImportTest/encoding", "Shift_JIS");

        List<User> icons = loadUsers();
        assertEquals(2, icons.size());

        User second = icons.get(1);
        assertEquals(2, second.id);
        assertEquals("デモユーザーさん", second.name);
    }

    public void testImportErrorData() {
        try {
            importData(FileType.Csv, "sqliteCsvImportTest/error");
            fail();
        } catch (ImportException e) {
            assertTrue("例外が発生すること", true);
        }
    }

    List<User> loadUsers() {
        Cursor cursor = getSQLiteDatabase().query(
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
                user.id = cursor.getLong(0);
                user.name = cursor.getString(1);
                user.weight = cursor.getDouble(2);
                user.image = cursor.getBlob(3);
                user.created = cursor.getLong(4);
                user.updated = cursor.getLong(5);

                users.add(user);
            }
        } finally {
            cursor.close();
        }

        return users;
    }

    public static class User {
        long id;
        String name;
        double weight;
        byte[] image;
        long created;
        long updated;
    }
}
