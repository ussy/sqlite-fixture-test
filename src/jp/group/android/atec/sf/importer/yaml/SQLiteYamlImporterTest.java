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
package jp.group.android.atec.sf.importer.yaml;

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

public class SQLiteYamlImporterTest extends DatabaseTestCase {

    @Override
    protected SQLiteOpenHelper createSQLiteOpenHelper() {
        return new FixtureOpenHelper(getDatabaseContext());
    }

    public void testImportData() {
        importData(FileType.Yaml, "sqliteYamlImportTest/normal");

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

    public void testImportAnotherData() {
        importData(FileType.Yaml, "sqliteYamlImportTest/normal");

        List<User> icons1 = loadUsers();
        assertEquals(2, icons1.size());

        User first1 = icons1.get(0);
        assertEquals(1, first1.id);
        assertEquals("ussy", first1.name);
        assertEquals(85.5d, first1.weight);
        Bitmap image1 = BitmapFactory.decodeByteArray(first1.image, 0, first1.image.length);
        assertNotNull(image1);
        assertEquals(1306002629, first1.created);

        User second1 = icons1.get(1);
        assertEquals(2, second1.id);
        assertEquals("デモユーザーさん", second1.name);

        importData(FileType.Yaml, "sqliteYamlImportTest/another");

        List<User> icons2 = loadUsers();
        assertEquals(2, icons2.size());

        User first2 = icons2.get(0);
        assertEquals(1, first2.id);
        assertEquals("ussy_ano", first2.name);
        assertEquals(85.5d, first2.weight);
        Bitmap image2 = BitmapFactory.decodeByteArray(first2.image, 0, first2.image.length);
        assertNotNull(image2);
        assertEquals(1306002629, first2.created);

        User second2 = icons2.get(1);
        assertEquals(2, second2.id);
        assertEquals("デモユーザーさん_ano", second2.name);
    }

    public void testImportSJISData() {
        importData(FileType.Yaml, "sqliteYamlImportTest/encoding", "Shift_JIS");

        List<User> icons = loadUsers();
        assertEquals(2, icons.size());

        User second = icons.get(1);
        assertEquals(2, second.id);
        assertEquals("デモユーザーさん", second.name);
    }

    public void testImportErrorData() {
        try {
            importData(FileType.Yaml, "sqliteYamlImportTest/error");
            fail();
        } catch (ImportException e) {
            assertTrue("例外が発生すること", true);
        }
    }

    public void testImportNullData() {
        try {
            importData(FileType.Yaml, null);
            fail();
        } catch (ImportException e) {
            assertTrue("例外が発生すること", true);
        }
    }
    
    public void testImportEmptyStringData() {
        try {
            importData(FileType.Yaml, "");
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
