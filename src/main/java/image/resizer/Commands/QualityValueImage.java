package image.resizer.Commands;


import marvin.image.MarvinImage;
import marvin.image.MarvinImageMask;
import net.coobird.thumbnailator.Thumbnails;
import org.marvinproject.image.blur.gaussianBlur.GaussianBlur;
import picocli.CommandLine;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


//TODO Задаем уровень сжатия изображения, уменьшаем/увеличиваем картинку JPEG/PNG (quality value)


@CommandLine.Command(name = "quality", mixinStandardHelpOptions = true, description = "Задаем уровень сжатия изображения, уменьшаем/увеличиваем картинку.")

public class QualityValueImage implements Runnable{

    @CommandLine.Option(
            names = {"--image"},
            description = "Укажите путь к изображению"
    )
    protected String inImage;

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
            names = {"--qu"},
            description = "это поле заполняется если вы хотите изменить качество изображения (укажите цифру от 1 до 100)"
    )
    protected int quality = 100;

    @CommandLine.Option(
            names = {"--bl"},
            description = "это поле заполняется если вы хотите размыть изображения (укажите цифру от 1 до 50)"
    )
    protected int blurring = 0;

    @Override
    public void run() {
        try {
            BufferedImage originalImage = ImageIO.read(new File(inImage));
            BufferedImage outputImage = qualityValueImage (originalImage, targetWidth, targetHeight, quality, blurring);
            inImage = inImage.substring(0, inImage.length() - 4);
            ImageIO.write(outputImage, "jpg", new File(inImage + "_copy.jpg"));

            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }


    //Уменьшает, увеличивает картинку или задает необходимый размер для изображения (resize width height)

    static BufferedImage qualityValueImage (BufferedImage originalImage, int targetWidth, int targetHeight, int quality, int blurring) throws IOException {
        if (targetWidth == 0){ targetWidth = originalImage.getWidth();}
        if (targetHeight == 0){ targetHeight = originalImage.getHeight();}
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(originalImage)
                .forceSize(targetWidth, targetHeight)
                .outputFormat("JPEG")
                .outputQuality((double) quality/100)
                .toOutputStream(outputStream);
        byte[] data = outputStream.toByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);


        if (blurring > 0){
            MarvinImage image = new MarvinImage(ImageIO.read(inputStream));

//        MarvinImage image = MarvinImageIO.loadImage("src/test/resources/8bMox.jpg");

            // 2. Создаем маски для каждой области которые хотим размыть
            MarvinImageMask mask1 = new MarvinImageMask(image.getWidth(), image.getHeight(), 0, 0, targetWidth,targetHeight);


            // 3.  Задаем размытие изображение маски
            GaussianBlur gaussianBlur = new GaussianBlur();
            gaussianBlur.load();
            gaussianBlur.setAttributes("radius", blurring);

            gaussianBlur.process(image.clone(), image, mask1);

            // 4. Сохраняем финальное изображение
            return image.getBufferedImageNoAlpha();}


        return ImageIO.read(inputStream);
    }

}


