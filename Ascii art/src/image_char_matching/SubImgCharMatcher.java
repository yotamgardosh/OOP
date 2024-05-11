package image_char_matching;
import java.util.HashMap;
import java.util.Map;

/**
 * Matches characters to sub-images based on their brightness levels.
 * This class maintains a mapping from characters to their perceived brightness
 * and provides functionality to find the closest character match for a given brightness level.
 */
public class SubImgCharMatcher {

    private HashMap<Character, Integer> charBrightnessMap = new HashMap<>();
    private char maxBrightnessChar;
    private char minBrightnessChar;
    private int maxBrightness = Integer.MIN_VALUE;
    private int minBrightness = Integer.MAX_VALUE;

    /**
     * Constructs a SubImgCharMatcher with a predefined set of characters.
     * It calculates and stores the brightness for each character in the set.
     *
     * @param charset Array of characters to be used for matching.
     */
    public SubImgCharMatcher(char[] charset) {
        for (char c : charset) {
            addChar(c);
        }
    }

    /**
     * Finds the character that best matches the specified target brightness.
     *
     * @param targetBrightness The target brightness to match against.
     * @return The character that closest matches the target brightness.
     */
    public char getCharByImageBrightness(double targetBrightness) {
        char closestChar = 0;
        double minDiff = Double.MAX_VALUE;
        for (Map.Entry<Character, Integer> entry : charBrightnessMap.entrySet()) {
            double normalizedBrightness = normalizeBrightness(entry.getValue());
            double diff = Math.abs(normalizedBrightness - targetBrightness);
            if (diff < minDiff || (diff == minDiff && entry.getKey() < closestChar)) {
                minDiff = diff;
                closestChar = entry.getKey();
            }
        }
        return closestChar;
    }

    /**
     * Adds a character to the brightness map after calculating its brightness.
     *
     * @param c The character to add.
     */
    public void addChar(char c) {
        int brightness = calculateCharBrightness(c);
        charBrightnessMap.put(c, brightness);
        updateMaxMinChars(c, brightness);
    }

    /**
     * Removes a character from the brightness map.
     *
     * @param c The character to remove.
     */
    public void removeChar(char c) {
        if (charBrightnessMap.keySet().isEmpty() || !charBrightnessMap.containsKey(c)) return;
        charBrightnessMap.remove(c);
        if (c == maxBrightnessChar || c == minBrightnessChar) {
            updateMaxMinBrightnessAfterRemoval();
        }
    }

    // Calculates the brightness of a character based on its representation.
    private int calculateCharBrightness(char c) {
        int brightness = 0;
        boolean[][] charArray = CharConverter.convertToBoolArray(c);
        for (int y = 0; y < CharConverter.DEFAULT_PIXEL_RESOLUTION; y++) {
            for (int x = 0; x < CharConverter.DEFAULT_PIXEL_RESOLUTION; x++) {
                if (charArray[y][x]) {
                    brightness++;
                }
            }
        }
        return brightness;
    }

    // Normalizes a brightness value to a 0-1 scale.
    private double normalizeBrightness(int brightness) {
        if (maxBrightness == minBrightness) return 1.0; //in case only one char is left after removal
        return (double) (brightness - minBrightness) / (maxBrightness - minBrightness);
    }

    // Updates the maximum and minimum brightness characters.
    private void updateMaxMinChars(char c, int brightness) {
        if (brightness > maxBrightness) {
            maxBrightness = brightness;
            maxBrightnessChar = c;
        }
        if (brightness < minBrightness) {
            minBrightness = brightness;
            minBrightnessChar = c;
        }
    }

    // Recalculates the maximum and minimum brightness values after a character is removed.
    private void updateMaxMinBrightnessAfterRemoval() {
        maxBrightness = Integer.MIN_VALUE;
        minBrightness = Integer.MAX_VALUE;
        for (Map.Entry<Character, Integer> entry : charBrightnessMap.entrySet()) {
            int currentBrightness = entry.getValue();
            if (currentBrightness > maxBrightness) {
                maxBrightness = currentBrightness;
                maxBrightnessChar = entry.getKey();
            }
            if (currentBrightness < minBrightness) {
                minBrightness = currentBrightness;
                minBrightnessChar = entry.getKey();
            }
        }
    }
}
