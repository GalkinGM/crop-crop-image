package image.resizer;

import image.resizer.Commands.CropImage;
import image.resizer.Commands.PortionBlurImage;
import image.resizer.Commands.QualityValueImage;
import image.resizer.Commands.ResizeImage;
import image.resizer.imageprocessor.BadAttributesException;
import picocli.CommandLine;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.Callable;

//@CommandLine.Command(name = "resizer",
//        mixinStandardHelpOptions = true,
//        version = "resizer 0.0.1",
//        description = "Information about repository")

@CommandLine.Command(
        name = "tester",
        subcommands = {
                ResizeImage.class,
                QualityValueImage.class,
                PortionBlurImage.class,
                CropImage.class

        }
)


public class ResizerApp  implements Callable<Integer> {

    @CommandLine.Parameters(index = "0",
            description = "the name to welcome.")
    private String names ;

    public static void main(String [] args) {
        int exitCode = runConsole(args);
        System.exit(exitCode);
    }

    protected static int runConsole(String[] args) {
        return new CommandLine(new ResizerApp()).execute(args);
    }

//    @CommandLine.Option(
//            names = {"--imageIn"},
////            required = true,
//            description = "укажите путь к изображению которое хотите преобразовать"
//    )
    protected String imageIn = "src/test/resources/testImage.jpg";
    File inputImage = new File(imageIn);

//    @CommandLine.Option(names = {"-s", "--saveImage"},
//            description = "укажите имя под которым нужно сохранить изображение после преобразования (изображения сохраняются в папку src/test/resources)")
    protected String name = "copy";
    File outPutFile = new File("src/test/resources/" + name + ".jpg");

//    @CommandLine.Option(
//            names = {"--tW"},
//            description = "укажите ширину для изменение размера изображения"
//    )
    protected int targetWidth = 0;

//    @CommandLine.Option(
//            names = {"--tH"},
//            description = "укажите высоту для изменение размера изображения"
//    )
    protected int targetHeight = 0;

//    @CommandLine.Option(
//            names = {"--qu"},
//            description = "это поле заполняется если вы хотите изменить качество изображения (укажите цифру от 1 до 100)"
//    )
    protected int quality = 100;

//    @CommandLine.Option(
//            names = {"--bl"},
//            description = "это поле заполняется если вы хотите размыть изображения (укажите цифру от 1 до 50)"
//    )
    protected int blurring = 0;

//    @CommandLine.Option(
//            names = {"--crop"},
//            description = "это поле заполняется если вы хотите обрезать изображение"
//    )
    protected String crop ="";

//    @CommandLine.Option(
//            names = {"--x"},
//            description = "координата по X-су для определения точки начала обрезания изображения"
//    )
    protected int x = 0;

//    @CommandLine.Option(
//            names = {"--y"},
//            description = "координата по Y-ку для определения точки начала обрезания изображения"
//    )
    protected int y = 0;

//    @CommandLine.Option(
//            names = {"-cH", "--cropHeight"},
//            description = "высота обрезания изображения"
//    )
    protected int cropHeight = 300;

//    @CommandLine.Option(
//            names = {"-cW", "--cropWidth"},
//            description = "ширина обрезания изображения"
//    )
    protected int cropWidth = 300;

    public void setInputFile(File inputImage) {
        this.inputImage = inputImage;
    }

    public void setOutputFile(File outPutFile) {
        this.outPutFile = outPutFile;
    }

    public void setResizeWidth(Integer reducedPreviewWidth) {
        this.targetWidth = reducedPreviewWidth;
    }

    public void setResizeHeight(Integer reducedPreviewHeight) {
        this.targetHeight = reducedPreviewHeight;
    }

    public void setQuality(int i) {
        this.quality = i;

    }

    public void setBlurRadius(Integer i) {
        this.blurring = i;
    }

    @Override
    public Integer call() throws Exception {
        if (quality < 0) {
                throw new BadAttributesException("Please check params!");
            }

        ImageProcessor imageProcessor = new ImageProcessor();
        if (!imageIn.equals("src/test/resources/testImage.jpg")){inputImage = new File(imageIn);}
        BufferedImage outputImage = imageProcessor.qualityValueImage(ImageIO.read(inputImage), targetWidth, targetHeight, quality, blurring, crop, x, y, cropWidth, cropHeight);
        if (!name.equals("copy")){outPutFile = new File("src/test/resources/" + name + ".jpg" );}
        ImageIO.write(outputImage,"jpg", new FileImageOutputStream(outPutFile));

        return 0;
    }



}