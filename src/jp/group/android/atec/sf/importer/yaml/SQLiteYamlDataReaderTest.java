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

import java.io.InputStream;

import jp.group.android.atec.sf.data.ColumnInfo;
import jp.group.android.atec.sf.data.ColumnType;
import jp.group.android.atec.sf.data.Row;
import jp.group.android.atec.sf.importer.SQLiteDataReader.Callback;
import jp.group.android.atec.sf.util.ResourceUtils;
import android.test.AndroidTestCase;

public class SQLiteYamlDataReaderTest extends AndroidTestCase {

    public void testRead() {
        InputStream in = ResourceUtils.openFromAsset(getContext(), "sqliteYamlDataReaderTest/test.yaml");

        YamlDataReader reader = new YamlDataReader();
        ColumnInfo[] columns = new ColumnInfo[] {
            new ColumnInfo(0, "_id", ColumnType.INTEGER, true, null, true),
            new ColumnInfo(1, "name", ColumnType.TEXT, false, null, false)
        };
        int count = reader.read(in, columns, new Callback() {

            @Override
            public void handleRow(Row row) {
                assertTrue("type", row.getColumn(0).getType() == ColumnType.INTEGER);
                assertTrue("type", row.getColumn(1).getType() == ColumnType.TEXT);

                if (row.getIndex() == 1) {
                    assertEquals("1", row.getValue(0));
                    assertEquals("test", row.getValue(1));
                } else if (row.getIndex() == 2) {
                    assertEquals("2", row.getValue(0));
                    assertEquals("テスト", row.getValue(1));
                }
            }
        });

        assertEquals("count", 2, count);

        ResourceUtils.close(in);
    }

    public void testIgnoreRead() {
        InputStream in = ResourceUtils.openFromAsset(getContext(), "sqliteYamlDataReaderTest/ignore.yaml");

        YamlDataReader reader = new YamlDataReader();
        ColumnInfo[] columns = new ColumnInfo[] {
            new ColumnInfo(0, "_id", ColumnType.INTEGER, true, null, true),
            new ColumnInfo(1, "name", ColumnType.TEXT, false, null, false)
        };
        int count = reader.read(in, columns, new Callback() {

            @Override
            public void handleRow(Row row) {
                assertTrue("type", row.getColumn(0).getType() == ColumnType.INTEGER);
                assertTrue("type", row.getColumn(1).getType() == ColumnType.TEXT);

                if (row.getIndex() == 1) {
                    assertEquals("1", row.getValue(0));
                    assertEquals("test", row.getValue(1));
                } else if (row.getIndex() == 2) {
                    assertEquals("2", row.getValue(0));
                    assertEquals("テスト", row.getValue(1));
                } else if (row.getIndex() == 3) {
                    assertEquals("4", row.getValue(0));
                    assertEquals("hello", row.getValue(1));
                }
            }
        });

        assertEquals("count", 3, count);

        ResourceUtils.close(in);
    }
}
