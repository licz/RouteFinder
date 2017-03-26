package org.test.byhiras.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by leszek on 26/03/17.
 */
public class FileHelperImplTest {

    private FileHelperImpl fileHelper = new FileHelperImpl();

    @Test
    public void shouldReadFromFile() {
        Assert.assertEquals("testINPUT", fileHelper.readFromFile("input.txt").get(0));
    }
}