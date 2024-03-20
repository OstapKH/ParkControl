package es.uca.dss.ParkControl.core.QRCodeGeneration;

import net.glxn.qrgen.javase.QRCode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class QRCodeGenerator {
    public static BufferedImage generateQRCodeImage(UUID barcodeUUID) throws Exception {
        String barcodeText = barcodeUUID.toString();
        ByteArrayOutputStream stream = QRCode
                .from(barcodeText)
                .withSize(250, 250)
                .stream();
        ByteArrayInputStream bis = new ByteArrayInputStream(stream.toByteArray());

        return ImageIO.read(bis);
    }

    public static ByteArrayOutputStream generateQRCodeByteOutput(UUID barcodeUUID) throws Exception {
        String barcodeText = barcodeUUID.toString();
        ByteArrayOutputStream stream = QRCode
                .from(barcodeText)
                .withSize(250, 250)
                .stream();
//        ByteArrayInputStream bis = new ByteArrayInputStream(stream.toByteArray());
        return stream;
    }
}
