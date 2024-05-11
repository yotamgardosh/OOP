package image;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Provides utility functions for manipulating images, including calculating brightness,
 * dividing images into sub-images, and padding images to fit certain dimensions.
 */
public class ImageUtils {

    /**
     * Calculates the normalized brightness of an image.
     *
     * @param image The image to calculate brightness for.
     * @return The average brightness of the image, normalized to a 0-1 scale.
     */
    public double calculateBrightness(Image image) {
        int width = image.getWidth();
        int height = image.getHeight();
        double totalGrayScale = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color = image.getPixel(i, j);
                double grayPixel = color.getRed() * 0.2126 + color.getGreen() * 0.7152
                        + color.getBlue() * 0.0722;
                totalGrayScale += grayPixel;
            }
        }
        double averageGrayScale = totalGrayScale / (width * height);
        return averageGrayScale / 255; // normalize to 0-1
    }

    /**
     * Divides an image into smaller sub-images based on a specified resolution.
     *
     * @param resolution The number of divisions along each axis.
     * @param image The image to be divided.
     * @return A list of sub-images created from the original image.
     */
    public List<Image> divideToSubImages(int resolution, Image image) {
        int width = image.getWidth();
        int height = image.getHeight();

        // Calculate the size of each sub-image
        int subImageWidth = width / resolution;
        int subImageHeight = height / resolution;

        List<Image> subImages = new ArrayList<>();

        // Iterate over the image to create sub-images
        for (int row = 0; row < resolution; row++) {
            for (int col = 0; col < resolution; col++) {
                // Calculate the starting point for each sub-image
                int startX = col * subImageWidth;
                int startY = row * subImageHeight;

                // Create a new pixel array for the sub-image
                Color[][] subImagePixels = new Color[subImageHeight][subImageWidth];

                for (int i = 0; i < subImageHeight; i++) {
                    for (int j = 0; j < subImageWidth; j++) {
                        // Copy pixels from the original image to the sub-image
                        subImagePixels[i][j] = image.getPixel(startY + i, startX + j);
                    }
                }
                // Create a new Image object for the sub-image and add it to the list
                subImages.add(new Image(subImagePixels, subImageWidth, subImageHeight));
            }
        }

        return subImages;
    }

    /**
     * Pads an image to ensure its width and height are powers of two, which is often
     * required for certain processing techniques.
     *
     * @param image The image to be padded.
     * @return A new image that has been padded to meet the dimension requirements.
     */
    public Image padImage(Image image){
        int width = image.getWidth();
        int height = image.getHeight();
        int newWidth = calculateDimension(width);
        int newHeight = calculateDimension(height);
        Color[][] newPixelArray = new Color[newHeight][newWidth];
        int xPadding = (newWidth - width) / 2;
        int yPadding = (newHeight - height) / 2;

        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newWidth; j++) {
                if (i < yPadding || i >= yPadding + height || j < xPadding || j >= xPadding + width) {
                    newPixelArray[i][j] = new Color(255, 255, 255);
                } else {
                    newPixelArray[i][j] = image.getPixel(i - yPadding, j - xPadding);
                }
            }
        }
        return new Image(newPixelArray, newWidth, newHeight);
    }

    /**
     * Calculates the smallest power of two that is greater than or equal to a given dimension.
     * This is used to determine the new dimensions for padding images.
     *
     * @param oldDimension The original dimension (width or height) of the image.
     * @return The calculated dimension as a power of two.
     */
    private int calculateDimension(int oldDimension) {
        int powerOfTwo = 1;
        while (powerOfTwo < oldDimension) {
            powerOfTwo *= 2;
        }
        return powerOfTwo;
    }







}
