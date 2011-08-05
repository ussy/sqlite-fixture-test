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

import jp.group.android.atec.sf.data.ColumnType;
import android.test.AndroidTestCase;

public class ColumnTypeTest extends AndroidTestCase {

    public void testParseInteger() {
        assertTrue("Integer", ColumnType.INTEGER == ColumnType.parse("int"));
        assertTrue("Integer", ColumnType.INTEGER == ColumnType.parse("Int"));
        assertTrue("Integer", ColumnType.INTEGER == ColumnType.parse("inti"));
        assertTrue("Integer", ColumnType.INTEGER == ColumnType.parse("integer"));
        assertTrue("Integer", ColumnType.INTEGER == ColumnType.parse("INTEGER"));
        assertFalse("Integer", ColumnType.INTEGER == ColumnType.parse("in"));
    }

    public void testParseText() {
        assertTrue("Text", ColumnType.TEXT == ColumnType.parse("char"));
        assertTrue("Text", ColumnType.TEXT == ColumnType.parse("chari"));
        assertTrue("Text", ColumnType.TEXT == ColumnType.parse("character"));
        assertTrue("Text", ColumnType.TEXT == ColumnType.parse("Char"));
        assertTrue("Text", ColumnType.TEXT == ColumnType.parse("CHARCTER"));
        assertFalse("Text", ColumnType.TEXT == ColumnType.parse("cha"));

        assertTrue("Text", ColumnType.TEXT == ColumnType.parse("clob"));
        assertTrue("Text", ColumnType.TEXT == ColumnType.parse("clobi"));
        assertTrue("Text", ColumnType.TEXT == ColumnType.parse("Clob"));
        assertTrue("Text", ColumnType.TEXT == ColumnType.parse("CLOB"));
        assertFalse("Text", ColumnType.TEXT == ColumnType.parse("clo"));

        assertTrue("Text", ColumnType.TEXT == ColumnType.parse("text"));
        assertTrue("Text", ColumnType.TEXT == ColumnType.parse("texti"));
        assertTrue("Text", ColumnType.TEXT == ColumnType.parse("Text"));
        assertTrue("Text", ColumnType.TEXT == ColumnType.parse("TEXT"));
        assertFalse("Text", ColumnType.TEXT == ColumnType.parse("tex"));
    }

    public void testParseNone() {
        assertTrue("None", ColumnType.NONE == ColumnType.parse(null));
        assertTrue("None", ColumnType.NONE == ColumnType.parse(""));
        assertTrue("None", ColumnType.NONE == ColumnType.parse(" "));
        assertTrue("None", ColumnType.NONE == ColumnType.parse("blob"));
        assertTrue("None", ColumnType.NONE == ColumnType.parse("blobi"));
        assertTrue("None", ColumnType.NONE == ColumnType.parse("Blob"));
        assertTrue("None", ColumnType.NONE == ColumnType.parse("BLOB"));
        assertFalse("None", ColumnType.NONE == ColumnType.parse("blo"));
    }

    public void testParseReal() {
        assertTrue("Real", ColumnType.REAL == ColumnType.parse("Real"));
        assertTrue("Real", ColumnType.REAL == ColumnType.parse("Reali"));
        assertTrue("Real", ColumnType.REAL == ColumnType.parse("Real"));
        assertTrue("Real", ColumnType.REAL == ColumnType.parse("Real"));
        assertFalse("Real", ColumnType.REAL == ColumnType.parse("rea"));

        assertTrue("Real", ColumnType.REAL == ColumnType.parse("floa"));
        assertTrue("Real", ColumnType.REAL == ColumnType.parse("floai"));
        assertTrue("Real", ColumnType.REAL == ColumnType.parse("float"));
        assertTrue("Real", ColumnType.REAL == ColumnType.parse("Floa"));
        assertTrue("Real", ColumnType.REAL == ColumnType.parse("FLOAT"));
        assertFalse("Real", ColumnType.REAL == ColumnType.parse("flo"));

        assertTrue("Real", ColumnType.REAL == ColumnType.parse("doub"));
        assertTrue("Real", ColumnType.REAL == ColumnType.parse("doubi"));
        assertTrue("Real", ColumnType.REAL == ColumnType.parse("double"));
        assertTrue("Real", ColumnType.REAL == ColumnType.parse("DOUBLE"));
        assertFalse("Real", ColumnType.REAL == ColumnType.parse("dou"));
    }

    public void testParseNumeric() {
        assertTrue("Numeric", ColumnType.NUMERIC == ColumnType.parse("aaa"));
    }
}
