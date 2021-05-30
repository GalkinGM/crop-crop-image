package image.resizer.Commands;


import marvin.image.MarvinImage;
import org.marvinproject.image.transform.scale.Scale;
import picocli.CommandLine;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;


//TODO Задаем необходимый размер для изображения (resize width height)


@CommandLine.Command(name = "resize", mixinStandardHelpOptions = true, description = "Задаем необходимый размер для изображения.")

public class ResizeImage implements Runnable{

    @CommandLine.Option(
            names = {"--image"},
            description = "Укажите путь к изображению"
    )
    protected String inImage;

    @CommandLine.Option(
            names = {"--tW"},
            description = "Укажите требуемую ширину"
    )
    protected int targetWidth;

    @CommandLine.Option(
            names = {"--tH"},
            description = "Укажите требуемую высоту"
    )
    protected int targetHeight;


    @Override
    public void run() {
        try {
            BufferedImage originalImage = ImageIO.read(new File(inImage));
            BufferedImage outputImage = resizeImage (originalImage, targetWidth, targetHeight);
            inImage = inImage.substring(0, inImage.length() - 4);
            ImageIO.write(outputImage, "jpg", new File(inImage + "_copy.jpg"));

            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }


    //Уменьшает, увеличивает картинку или задает необходимый размер для изображения (resize width height)
    static BufferedImage resizeImage (BufferedImage originalImage, int targetWidth, int targetHeight) {
        MarvinImage imageIn = new MarvinImage(originalImage);
        Scale scale = new Scale();
        scale.load();
        scale.setAttribute("newWidth", targetWidth);
        scale.setAttribute("newHeight", targetHeight);
        scale.process(imageIn.clone(), imageIn, null, null, false);
        return imageIn.getBufferedImageNoAlpha();
    }

}

