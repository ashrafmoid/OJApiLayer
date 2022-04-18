package com.ashraf.executor.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class FileReaderUtilTest {

    @Test
    public void testGetFileAsString() throws IOException {
        String result = FileReaderUtil.getFileAsString("/Users/ashrafmoid/Desktop/PersonalProject/OJApiLayer/codeExecutor/src/test/java/com/ashraf/executor/file/SampleFile.txt");
        System.out.println(result);
    }
}
