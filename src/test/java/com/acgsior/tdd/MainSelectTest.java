package com.acgsior.tdd;

import com.acgsior.factory.ImagePathFactory;
import com.acgsior.image.ImageType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Yove on 16/07/01.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-context.xml")
public class MainSelectTest {

    @Autowired
    private ImagePathFactory imagePathFactory;

    @Test
    public void imagePathFactoryWorkTest() {
        String extension = imagePathFactory.getImageExtension("http//s.timepill.net/user_icon/20015/b100079421.jpg?v=43");
        Assert.assertEquals("jpg", extension);

        String path = imagePathFactory.getPathWithoutExtension(ImageType.IMAGE, "100079421");
        Assert.assertEquals("/Users/Yove/Temp/timepill/image/100079421", path);
    }

}
