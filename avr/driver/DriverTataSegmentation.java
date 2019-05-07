package com.avanseus.avr.driver;

import com.avanseus.avr.detection.Localization;
import com.avanseus.avr.detection.LogoDetection;
import com.avanseus.descriptorVectors.GetUniformSkeletonImage;
import com.avanseus.imageUtils.*;
import com.avanseus.segmentation.Segmentation;
import com.avanseus.avr.detection.FineTuneClustering;
import com.avanseus.avr.fileOperations.FileUtils;
import com.avanseus.avr.model.CombinedImage;
import com.avanseus.avr.model.WindowData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * Created by yash on 8/7/16.
 */
public class DriverTataSegmentation {
    private Properties properties = new Properties();
    private String classPath = null;
    public static String srcImage = null;
    public static String destDir = null;

    public DriverTataSegmentation(boolean user, String imagePath,String destinationDir) {
        try {
            if(user) {
                classPath = "/home/hemanth/analytics/analytics/AVR/AVR-core/src/main/resources/";
            } else {
                classPath = "D:/GitRepo/workspace/AVR/AVR-core/src/main/resources/";
            }
            InputStream input = new FileInputStream(classPath+"avr.properties");
            properties.load(input);
            srcImage = properties.getProperty("SRC_IMAGE");
            destDir = properties.getProperty("DEST_DIR");
            //srcImage = imagePath;
            //destDir = destinationDir;
            FileUtils.createDirectory(destDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(BufferedImage bufferedImage) {
        ImageUtils imageUtils = new ImageUtils();
        try {
            BufferedImage bitmapImage = imageUtils.getBufferedImageFrom2D(imageUtils.getLogical2DImageDouble(bufferedImage));
            HashMap<Integer, int[][]> charLabels = new HashMap<>();
            Segmentation segmentation = new Segmentation();
            charLabels = segmentation.SegmentUsingConnectedComponentAnalysis(bitmapImage);
            for(int indx=1;indx<=charLabels.size();indx++) {
                String name = "SegmentedChars_" +"_"+ indx;
                imageUtils.getVisibleImageFromBufferedImageOfLogicalOnes(
                        imageUtils.getBufferedImageFrom2D(charLabels.get(indx-1)), name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        try {
            /*File folder = new File("D:\\TataPowerSED\\MatlabCode\\ANPRDATA\\renumberplatelocalization\\threshold_img");
            File[] listOfFiles = folder.listFiles();
            for (File file : listOfFiles) {
                if ( file.isFile() ) {
                    String src = file.getAbsolutePath();
                    String dest = src.substring(0, src.lastIndexOf("."));
                    DriverTataSegmentation DriverTataSegmentation = new DriverTataSegmentation(false, src, dest);
                    File inputImageFile = new File(srcImage);
                    BufferedImage inputImage = ImageIO.read(inputImageFile);
                    DriverTataSegmentation.run(inputImage);
                }
            }*/
            DriverTataSegmentation driver = new DriverTataSegmentation(false,null,null);
            File inputImageFile = new File(srcImage);
            BufferedImage inputImage = ImageIO.read(inputImageFile);
            driver.run(inputImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
