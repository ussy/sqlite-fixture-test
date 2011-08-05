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
package jp.group.android.atec.sf.data;

import java.io.InputStream;

import jp.group.android.atec.sf.importer.SQLiteDataReader.Callback;
import jp.group.android.atec.sf.importer.csv.CsvDataReader;
import jp.group.android.atec.sf.util.ResourceUtils;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.AndroidTestCase;

public class RowConverterTest extends AndroidTestCase {

    public void testToContentValues() {
        InputStream in = ResourceUtils.openFromAsset(getContext(), "rowConverterTest/user.csv");

        CsvDataReader reader = new CsvDataReader();
        ColumnInfo[] columns = new ColumnInfo[] {
            new ColumnInfo(0, "_id", ColumnType.INTEGER, true, null, true),
            new ColumnInfo(1, "name", ColumnType.TEXT, false, null, false),
            new ColumnInfo(2, "weight", ColumnType.REAL, false, null, false),
            new ColumnInfo(3, "icon", ColumnType.NONE, false, null, false)
        };

        reader.read(in, columns, new Callback() {

            @Override
            public void handleRow(Row row) {
                RowConverter converter = new RowConverter();
                ContentValues values = converter.toContentValues(row);
                Integer id = values.getAsInteger("_id");
                String name = values.getAsString("name");
                Double weight = values.getAsDouble("weight");
                byte[] icon = values.getAsByteArray("icon");
                if (row.getIndex() == 1) {
                    assertEquals(1, id.intValue());
                    assertEquals("ussy", name);
                    assertEquals(60.5, weight);

                    Bitmap bitmap = BitmapFactory.decodeByteArray(icon, 0, icon.length);
                    assertNotNull("画像ができること", bitmap);
                } else if (row.getIndex() == 2) {
                    assertEquals(2, id.intValue());
                    assertEquals("デモ", name);
                    assertNull(weight);
                    assertNull(icon);
                }
            }
        });

        ResourceUtils.close(in);
    }

    public void testIntegerError() {
        InputStream in = ResourceUtils.openFromAsset(getContext(), "rowConverterTest/integerError.csv");

        CsvDataReader reader = new CsvDataReader();
        ColumnInfo[] columns = new ColumnInfo[] {
            new ColumnInfo(0, "_id", ColumnType.INTEGER, true, null, true),
            new ColumnInfo(1, "name", ColumnType.TEXT, false, null, false),
            new ColumnInfo(2, "weight", ColumnType.REAL, false, null, false),
            new ColumnInfo(3, "icon", ColumnType.NONE, false, null, false)
        };

        reader.read(in, columns, new Callback() {

            @Override
            public void handleRow(Row row) {
                RowConverter converter = new RowConverter();
                try {
                    converter.toContentValues(row);
                    if (row.getIndex() == 1) {
                        fail();
                    } else if (row.getIndex() == 2) {
                        assertTrue("正しく読み込めている", true);
                    }
                } catch (ConvertException e) {
                    if (row.getIndex() == 1) {
                        assertEquals("a", e.getValue());
                    } else if (row.getIndex() == 2) {
                        fail();
                    }
                }
            }
        });

        ResourceUtils.close(in);
    }

    public void testRealError() {
        InputStream in = ResourceUtils.openFromAsset(getContext(), "rowConverterTest/realError.csv");

        CsvDataReader reader = new CsvDataReader();
        ColumnInfo[] columns = new ColumnInfo[] {
            new ColumnInfo(0, "_id", ColumnType.INTEGER, true, null, true),
            new ColumnInfo(1, "name", ColumnType.TEXT, false, null, false),
            new ColumnInfo(2, "weight", ColumnType.REAL, false, null, false),
            new ColumnInfo(3, "icon", ColumnType.NONE, false, null, false)
        };

        reader.read(in, columns, new Callback() {

            @Override
            public void handleRow(Row row) {
                RowConverter converter = new RowConverter();
                try {
                    converter.toContentValues(row);
                    if (row.getIndex() == 1) {
                        fail();
                    }
                } catch (ConvertException e) {
                    if (row.getIndex() == 1) {
                        assertEquals("a", e.getValue());
                    } else if (row.getIndex() == 2) {
                        fail();
                    }
                }
            }
        });

        ResourceUtils.close(in);
    }

    public void testBlobError() {
        InputStream in = ResourceUtils.openFromAsset(getContext(), "rowConverterTest/blobError.csv");

        CsvDataReader reader = new CsvDataReader();
        ColumnInfo[] columns = new ColumnInfo[] {
            new ColumnInfo(0, "_id", ColumnType.INTEGER, true, null, true),
            new ColumnInfo(1, "name", ColumnType.TEXT, false, null, false),
            new ColumnInfo(2, "weight", ColumnType.REAL, false, null, false),
            new ColumnInfo(3, "icon", ColumnType.NONE, false, null, false)
        };

        reader.read(in, columns, new Callback() {

            @Override
            public void handleRow(Row row) {
                RowConverter converter = new RowConverter();
                try {
                    converter.toContentValues(row);
                    if (row.getIndex() == 1) {
                        fail();
                    }
                } catch (ConvertException e) {
                    if (row.getIndex() == 1) {
                        assertEquals("iVB", e.getValue());
                    } else if (row.getIndex() == 2) {
                        fail();
                    }
                }
            }
        });

        ResourceUtils.close(in);
    }

    public void testNullable() {
        InputStream in = ResourceUtils.openFromAsset(getContext(), "rowConverterTest/empty.csv");

        CsvDataReader reader = new CsvDataReader();
        ColumnInfo[] columns = new ColumnInfo[] {
            new ColumnInfo(0, "_id", ColumnType.INTEGER, false, null, false),
            new ColumnInfo(1, "name", ColumnType.TEXT, false, null, false),
            new ColumnInfo(2, "weight", ColumnType.REAL, false, null, false),
            new ColumnInfo(3, "icon", ColumnType.NONE, false, null, false)
        };

        reader.read(in, columns, new Callback() {

            @Override
            public void handleRow(Row row) {
                RowConverter converter = new RowConverter();
                ContentValues values = converter.toContentValues(row);
                Integer id = values.getAsInteger("_id");
                String name = values.getAsString("name");
                Double weight = values.getAsDouble("weight");
                byte[] icon = values.getAsByteArray("icon");
                if (row.getIndex() == 1) {
                    assertNull(id);
                    assertNull(name);
                    assertNull(weight);
                    assertNull(icon);
                }
            }
        });

        ResourceUtils.close(in);
    }

    public void testNullableException() {
        InputStream in = ResourceUtils.openFromAsset(getContext(), "rowConverterTest/emptyException.csv");

        CsvDataReader reader = new CsvDataReader();
        ColumnInfo[] columns = new ColumnInfo[] {
            new ColumnInfo(0, "_id", ColumnType.INTEGER, true, null, true),
            new ColumnInfo(1, "name", ColumnType.TEXT, false, null, false),
            new ColumnInfo(2, "weight", ColumnType.REAL, true, null, false),
            new ColumnInfo(3, "icon", ColumnType.NONE, false, null, false)
        };

        reader.read(in, columns, new Callback() {

            @Override
            public void handleRow(Row row) {
                RowConverter converter = new RowConverter();
                try {
                    converter.toContentValues(row);
                    fail("例外が発生すること");
                } catch (SQLNotNullException e) {
                    assertEquals("例外が発生したカラム名", "weight", e.getColumnName());
                }
            }
        });

        ResourceUtils.close(in);
    }
}
