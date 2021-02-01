import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;


public class Tools {

    static void takeSnapShot(JPanel panel) {
        BufferedImage bufImage = new BufferedImage(panel.getSize().width, panel.getSize().height, BufferedImage.TYPE_INT_RGB);
        panel.paint(bufImage.createGraphics());
        File imageFile = new File("saved_file.jpg");
        try {
            imageFile.createNewFile();
            ImageIO.write(bufImage, "BMP", imageFile);
        } catch (Exception ex) {
            System.out.println("File Saving Exception- Screenshot");
        }
    }

}

