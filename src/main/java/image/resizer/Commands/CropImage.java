package image.resizer.Commands;


import marvin.image.MarvinImage;
import marvin.image.MarvinImageMask;
import marvinplugins.MarvinPluginCollection;
import net.coobird.thumbnailator.Thumbnails;
import org.marvinproject.image.blur.gaussianBlur.GaussianBlur;
import picocli.CommandLine;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


//TODO Вырезаем прямоугольную область изображения (crop width height x y)


@CommandLine.Command(name = "crop", mixinStandardHelpOptions = true, description = "Вырезаем прямоугольную область изображения.")

public class CropImage implements Runnable{
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

    @CommandLine.Option(
            names = {"--x"},
            description = "координата по X-су для определения точки начала обрезания изображения"
    )
    protected int x = 0;

    @CommandLine.Option(
            names = {"--y"},
            description = "координата по Y-ку для определения точки начала обрезания изображения"
    )
    protected int y = 0;

    @Override
    public void run() {
        try {
            BufferedImage originalImage = ImageIO.read(new File(inImage));
            BufferedImage outputImage = cropImage (originalImage, x, y, targetWidth, targetHeight);
            inImage = inImage.substring(0, inImage.length() - 4);
            ImageIO.write(outputImage, "jpg", new File(inImage + "_copy.jpg"));

            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    static BufferedImage cropImage (BufferedImage originalImage, int x, int y, int targetWidth, int targetHeight) throws IOException {
        // 1. Добавляем изображение
        MarvinImage imageIn = new MarvinImage(originalImage);
        MarvinImage imageOut = new MarvinImage();

        // 2. Задаем координаты области обрезания
        MarvinPluginCollection.crop(imageIn, imageOut, x, y, targetWidth, targetHeight);

        // 3. Сохраняем финальное изображение
//        MarvinImageIO.saveImage(imageOut, "src/test/resources/J_R_R_Tolkien_The_Hobbit_1937_copy.jpg");

        return imageOut.getBufferedImageNoAlpha();
    }

}
