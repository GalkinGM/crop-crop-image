package image.resizer.Commands;

import marvin.image.MarvinImage;
import marvin.image.MarvinImageMask;
import org.marvinproject.image.blur.gaussianBlur.GaussianBlur;
import picocli.CommandLine;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;


//TODO Размываем изображения (blur radius)


@CommandLine.Command(name = "blur", mixinStandardHelpOptions = true, description = "Размываем изображения.")

public class PortionBlurImage implements Runnable {
    @CommandLine.Option(
            names = {"--image"},
            description = "Укажите путь к изображению"
    )
    protected String inImage;

    @CommandLine.Option(
            names = {"--x"},
            description = "координата по X-су для определения точки начала размывания изображения (не обязательно указывать)"
    )
    protected int x = 0;

    @CommandLine.Option(
            names = {"--y"},
            description = "координата по Y-ку для определения точки начала размывания изображения (не обязательно указывать)"
    )
    protected int y = 0;

    @CommandLine.Option(
            names = {"--tW"},
            description = "Укажите требуемую ширину (не обязательно указывать)"
    )
    protected int targetWidth = 0;

    @CommandLine.Option(
            names = {"--tH"},
            description = "Укажите требуемую высоту (не обязательно указывать)"
    )
    protected int targetHeight = 0;

    @CommandLine.Option(
            names = {"--bl"},
            description = "размытие изображения (укажите цифру от 1 до 50)"
    )
    protected int blurring;


    @Override
    public void run() {
        try {
            BufferedImage originalImage = ImageIO.read(new File(inImage));
            BufferedImage outputImage = portionBlurImage (originalImage, x, y, targetWidth, targetHeight, blurring);
            inImage = inImage.substring(0, inImage.length() - 4);
            ImageIO.write(outputImage, "jpg", new File(inImage + "_copy.jpg"));
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    static BufferedImage portionBlurImage (BufferedImage originalImage, int x, int y, int targetWidth, int targetHeight, int blurring){
        if (targetWidth == 0){ targetWidth = originalImage.getWidth();}
        if (targetHeight == 0){ targetHeight = originalImage.getHeight();}

        // 1. Добавляем изображение
        MarvinImage image = new MarvinImage(originalImage);

        // 2. Создаем маски для каждой области которые хотим размыть
        MarvinImageMask mask1 = new MarvinImageMask(image.getWidth(), image.getHeight(), x, y, targetWidth, targetHeight);

        // 3.  Задаем размытие изображение маски
        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur.load();
        gaussianBlur.setAttributes("radius", blurring);
        gaussianBlur.process(image.clone(), image, mask1);

        return image.getBufferedImageNoAlpha();
    }



}
