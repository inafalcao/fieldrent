package br.com.fieldrent.documentation;

import br.com.fieldrent.FieldrentApplication;
import br.com.fieldrent.util.ByteUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import static junit.framework.Assert.assertEquals;

/**
 * Created by inafalcao on 3/18/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FieldrentApplication.class)
@WebAppConfiguration
@Transactional
public class UtilTests {

    @Test
    public void PrimitiveArrayAndboxedByteConversions() {
        String sourceBase64String = "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf";

        byte[] sourceArray = Base64Utils.decodeFromString(sourceBase64String);

        // Convert in and out
        Byte[] boxedConverted = ByteUtil.primitiveToBoxedArray(sourceArray);
        byte[] primiteveConverted = ByteUtil.boxedToPrimitiveArray(boxedConverted);

        String convertedBackString = Base64Utils.encodeToString(primiteveConverted);

        assertEquals(sourceBase64String, convertedBackString);
    }

}
