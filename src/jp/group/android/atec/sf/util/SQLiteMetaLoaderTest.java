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
package jp.group.android.atec.sf.util;

import jp.group.android.atec.sf.FixtureOpenHelper;
import jp.group.android.atec.sf.data.ColumnInfo;
import jp.group.android.atec.sf.data.ColumnType;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

public class SQLiteMetaLoaderTest extends AndroidTestCase {
    
    private SQLiteDatabase db;
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        db = getDatabase();
        db.beginTransaction();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        db.endTransaction();
        db.close();
    }
    
    protected SQLiteDatabase getDatabase() {
        FixtureOpenHelper helper = new FixtureOpenHelper(new RenamingDelegatingContext(getContext(), "test_"));
        return helper.getWritableDatabase();
    }

    public void testLoad() {
        SQLiteMetaLoader loader = new SQLiteMetaLoader(db);
        ColumnInfo[] columns = loader.load("icon");
        assertEquals(5, columns.length);

        assertEquals(0, columns[0].getIndex());
        assertEquals("_id", columns[0].getName());
        assertTrue(ColumnType.INTEGER == columns[0].getType());
        assertEquals(false, columns[0].isNotNull());
        assertEquals(null, columns[0].getDefalutValue());
        assertEquals(true, columns[0].isPrimaryKey());

        assertEquals(1, columns[1].getIndex());
        assertEquals("url", columns[1].getName());
        assertTrue(ColumnType.TEXT == columns[1].getType());
        assertEquals(true, columns[1].isNotNull());
        assertEquals(null, columns[1].getDefalutValue());
        assertEquals(false, columns[1].isPrimaryKey());

        assertEquals(2, columns[2].getIndex());
        assertEquals("image", columns[2].getName());
        assertTrue(ColumnType.NONE == columns[2].getType());
        assertEquals(false, columns[2].isNotNull());
        assertEquals(null, columns[2].getDefalutValue());
        assertEquals(false, columns[2].isPrimaryKey());
    }

    public void testLoadNotFoundTable() {
        SQLiteMetaLoader loader = new SQLiteMetaLoader(db);
        ColumnInfo[] columns = loader.load("notexsits");
        assertEquals(0, columns.length);
    }
}
