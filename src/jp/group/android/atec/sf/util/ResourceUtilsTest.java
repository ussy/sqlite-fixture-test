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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.test.AndroidTestCase;

public class ResourceUtilsTest extends AndroidTestCase {

    public void testListFromAsset() {
        String[] files = ResourceUtils.listFromAsset(getContext(), "resourceUtilsTest/testListFromAsset1");
        assertEquals("ファイル数が一致すること", 3, files.length);
        assertEquals("a.txt", files[0]);
        assertEquals("b.txt", files[1]);
        assertEquals("c.txt", files[2]);
    }
    
    public void testListFromAssetAppendSlash() {
        String[] files = ResourceUtils.listFromAsset(getContext(), "resourceUtilsTest/testListFromAsset1/");
        assertEquals("ファイル数が一致すること", 3, files.length);
        assertEquals("a.txt", files[0]);
        assertEquals("b.txt", files[1]);
        assertEquals("c.txt", files[2]);
    }

    /**
     * ディレクトリが含まれる場合は、そのディレクトリはカウントされる。
     * ただしサブディレクトリ以下のファイルはカウントしない。
     */
    public void testListFromAssetContainsDirectory() {
        String[] files = ResourceUtils.listFromAsset(getContext(), "resourceUtilsTest/testListFromAsset2");
        assertEquals("ファイル数が一致すること", 4, files.length);
        assertEquals("a.txt", files[0]);
        assertEquals("b.txt", files[1]);
        assertEquals("c.txt", files[2]);
        assertEquals("d", files[3]);
    }

    public void testListFromAssetNotExsitsDirectory() {
        String[] files = ResourceUtils.listFromAsset(getContext(), "resourceUtilsTest/null");
        assertEquals("存在しないディレクトリを指定した場合は、空であること", 0, files.length);
    }

    public void testOpenFileFromAsset() {
        InputStream is = null;
        BufferedReader reader = null;
        try {
            is = ResourceUtils.openFromAsset(getContext(), "resourceUtilsTest/testListFromAsset1/a.txt");
            reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();
            assertEquals("ファイルが取得できていること", "aaa", line);
        } catch (IOException e) {
            fail();
        } finally {
            ResourceUtils.close(reader);
            ResourceUtils.close(is);
        }
    }

    public void testOpenDirectoryFromAsset() {
        InputStream is = null;
        try {
            is = ResourceUtils.openFromAsset(getContext(), "resourceUtilsTest/testListFromAsset1/");
            assertNull("ディレクトリの場合 null であること", is);
        } finally {
            ResourceUtils.close(is);
        }
    }

    public void testOpenFromAssetNotFound() {
        InputStream is = null;
        try {
            is = ResourceUtils.openFromAsset(getContext(), "resourceUtilsTest/testListFromAsset1/null");
            assertNull("ファイルが存在しない場合 null であること", is);
        } finally {
            ResourceUtils.close(is);
        }
    }
}
