package mts.teta.resizer;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import marvin.image.MarvinImageMask;
import marvinplugins.MarvinPluginCollection;
import net.coobird.thumbnailator.Thumbnails;
import org.marvinproject.image.blur.gaussianBlur.GaussianBlur;
import marvin.image.MarvinImage;

public class ImageProcessor {

    //TODO Задаем уровень сжатия изображения, уменьшаем/увеличиваем картинку JPEG/PNG (quality value)
    static BufferedImage qualityValueImage (BufferedImage originalImage, int targetWidth, int targetHeight, int quality, int blurring, String commandCrop, int x, int y, int cropHeight, int cropWidth) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if(targetWidth ==0){targetWidth = originalImage.getWidth();}
        if(targetHeight ==0){targetHeight = originalImage.getHeight();}
        Thumbnails.of(originalImage)
                .forceSize(targetWidth, targetHeight)
                .outputFormat("JPEG")
                .outputQuality((double) quality/100)

                .toOutputStream(outputStream);
        byte[] data = outputStream.toByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        BufferedImage outputImages = ImageIO.read(inputStream);
        if (blurring > 0){
             outputImages = PortionBlurImage(outputImages, 0, 0, targetWidth, targetHeight, blurring);
        }

        if (commandCrop.equals("crop")){
            outputImages = cropImage(outputImages , x, y, cropWidth, cropHeight);
        }

        return outputImages;
//        return ImageIO.read(inputStream);
    }

    //TODO Вырезаем прямоугольную область изображения (crop width height x y)
    static BufferedImage cropImage (BufferedImage originalImage, int x, int y, int targetWidth, int targetHeight) throws IOException {
        // 1. Добавляем изображение
        MarvinImage imageIn = new MarvinImage(originalImage);
//        MarvinImage imageIn = MarvinImageIO.loadImage("src/test/resources/J_R_R_Tolkien_The_Hobbit_1937.jpg");
        MarvinImage imageOut = new MarvinImage();

        // 2. Задаем координаты области обрезания
        MarvinPluginCollection.crop(imageIn, imageOut, x, y, targetWidth, targetHeight);

        // 3. Сохраняем финальное изображение
//        MarvinImageIO.saveImage(imageOut, "src/test/resources/J_R_R_Tolkien_The_Hobbit_1937_copy.jpg");

        return imageOut.getBufferedImageNoAlpha();
    }

    //TODO Размываем изображения (blur radius)
    static BufferedImage PortionBlurImage (BufferedImage originalImage, int x, int y, int targetWidth, int targetHeight, int blurring){
        // 1. Добавляем изображение
        MarvinImage image = new MarvinImage(originalImage);

        // 2. Создаем маски для каждой области которые хотим размыть
        MarvinImageMask mask1 = new MarvinImageMask(image.getWidth(), image.getHeight(), 0, 0, image.getWidth(),image.getHeight());

        // 3.  Задаем размытие изображение маски
        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur.load();
        gaussianBlur.setAttributes("radius", blurring);
        gaussianBlur.process(image.clone(), image, mask1);

        return image.getBufferedImageNoAlpha();
    }

    //TODO Задаем необходимый размер  для изображения (resize width height)
//    static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
//        MarvinImage imageIn = new MarvinImage(originalImage);
//        Scale scale = new Scale();
//        scale.load();
//        scale.setAttribute("newWidth", targetWidth);
//        scale.setAttribute("newHeight", targetHeight);
//        scale.process(imageIn.clone(), imageIn, null, null, false);
//        return imageIn.getBufferedImageNoAlpha();
//    }

}
