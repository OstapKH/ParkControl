package es.uca.dss.ParkControl.core;

import es.uca.dss.ParkControl.core.QRCodeGeneration.QRCodeGenerator;
import org.junit.After;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;

import static junit.framework.TestCase.assertNotNull;

public class QRCodeGenerationTest {
    File outputfile;

    @Test
    public void testGenerateQRCodeImage() throws Exception {
        UUID testUUID = UUID.randomUUID();
        BufferedImage qrCodeImage = QRCodeGenerator.generateQRCodeImage(testUUID);
        assertNotNull(qrCodeImage);

        // Write the image to a file
        outputfile = new File("saved.png");
        ImageIO.write(qrCodeImage, "png", outputfile);
    }

    @After
    public void cleanup() {
        if (outputfile != null) {
            outputfile.delete();
        }
    }
}
