package ascii_art;

import image.Image;
import image.ImageUtils; // Make sure this import matches your actual package structure
import image_char_matching.SubImgCharMatcher; // Adjust this import based on your package structure

import java.util.List;

/**
 * This class represents the core algorithm for generating ASCII art from an image.
 * It utilizes the Image, ImageUtils, and SubImgCharMatcher classes to convert an image into ASCII art by
 * adjusting the image resolution, padding the image, dividing it into sub-images, and matching each sub-image
 * with an appropriate character based on its brightness.
 */
public class AsciiArtAlgorithm {

    private Image image;
    private char[] charset;
    private int resolution;

    private String output;

    /**
     * Constructs an AsciiArtAlgorithm with specified image, character set, resolution, and output type.
     *
     * @param image     The source image to convert into ASCII art.
     * @param charset   The set of characters to use for creating ASCII art.
     * @param resolution The resolution of the ASCII art in terms of character density.
     * @param output    The desired output format (e.g., console, file).
     */
    public AsciiArtAlgorithm(Image image, char[] charset, int resolution, String output){
        this.image = image;
        this.charset = charset;
        this.resolution = resolution;
        this.output = output;
    }

    /**
     * Executes the ASCII art generation algorithm.
     *
     * @return A 2D character array representing the ASCII art.
     */
    public char[][] run() {
        ImageUtils utils = new ImageUtils();
        SubImgCharMatcher matcher = new SubImgCharMatcher(charset);

        // pad the image
        Image paddedImage = utils.padImage(image);

        // divide the padded image into sub-images based on the specified resolution
        List<Image> subImages = utils.divideToSubImages(resolution, paddedImage);
        int subImagesPerRow = paddedImage.getWidth() / (paddedImage.getWidth() / resolution);

        // Initialize the ASCII art matrix
        char[][] asciiArt = new char[subImages.size() / subImagesPerRow][subImagesPerRow];

        // Iterate over each sub-image to calculate its brightness and match it to a character
        for (int i = 0; i < subImages.size(); i++) {
            double brightness = utils.calculateBrightness(subImages.get(i));
            char matchedChar = matcher.getCharByImageBrightness(brightness);

            // Determine the position of the sub-image in the grid
            int row = i / subImagesPerRow;
            int col = i % subImagesPerRow;
            asciiArt[row][col] = matchedChar;
        }

        return asciiArt;
    }
}

