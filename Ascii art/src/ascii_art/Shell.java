package ascii_art;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Objects;

import image.Image;
import ascii_output.*;


/**
 * The Shell class acts as the main interface for the ASCII art application, handling user interactions
 * and commands to manipulate ASCII art generation settings and processes. It supports operations such
 * as adding/removing characters, changing resolution, selecting images, and choosing output methods.
 */
public class Shell {

    private final int defaultResolution = 128;
    private final char[] defaultCharSet = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private final String defaultImagePath = "cat.jpeg";
    private final String defaultOutput = "console";
    private int userResolution;
    private LinkedHashSet<Character> userCharSet;
    private String userImagePath;
    private String userOutput;
    private Image UserImage;

    /**
     * Initializes the Shell with default settings for ASCII art generation.
     * @throws IOException If an I/O error occurs reading from the stream
     */
    public Shell() throws IOException {
        // Initialize user settings with default values
        this.userResolution = defaultResolution;
        this.userImagePath = defaultImagePath;
        this.userOutput = defaultOutput;
        this.userCharSet = new LinkedHashSet<>();
        for (char c : this.defaultCharSet) {
            this.userCharSet.add(c);
        }
    }

    /**
     * Main loop handling user input and triggering ASCII art generation or settings adjustments.
     */
    public void run() {

        try {
            this.UserImage = new Image(userImagePath); //Load the default image here to catch exceptions early
        } catch (IOException e) {
            System.out.println("Initial image load failed: " + e.getMessage());
            return;
        }

        while (true) {
            System.out.print(">>> ");
            String userInput = KeyboardInput.readLine(); // Read user input

            try {
                if ("exit".equals(userInput)) {
                    break;
                } else if ("chars".equals(userInput)) {
                    printCharset();
                } else if (userInput.startsWith("add ")) {
                    addUserCharacters(userInput.substring(4));
                } else if (userInput.startsWith("remove ")) {
                    removeUserCharacters(userInput.substring(7));
                } else if (userInput.startsWith("res")) {
                    changeResolution(userInput.length() > 3 ? userInput.substring(4) : "");
                } else if (userInput.startsWith("image ")) {
                    changeImage(userInput.substring(6).trim());
                } else if (userInput.startsWith("output ")) {
                    changeOutputMethod(userInput.substring(7).trim());
                } else if ("asciiArt".equals(userInput)) {
                    runAsciiArt();
                } else {
                    System.out.println("Did not execute due to incorrect command.");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void runAsciiArt() {
        if (userCharSet.isEmpty()) {
            System.out.println("Did not execute. Charset is empty.");
            return;
        }
        char[] reformattedCharSet = reformatCharSet(userCharSet);
        AsciiArtAlgorithm runAsciiArt = new AsciiArtAlgorithm(UserImage, reformattedCharSet,
                userResolution, userOutput);
        char[][] asciifiedImage = runAsciiArt.run();
        if (Objects.equals(userOutput, "console")) {
            ConsoleAsciiOutput consoleAsciiOutput = new ConsoleAsciiOutput();
            consoleAsciiOutput.out(asciifiedImage);
        } else {
            HtmlAsciiOutput htmlAsciiOutput = new HtmlAsciiOutput("out.html", "Courier New");
            htmlAsciiOutput.out(asciifiedImage);
        }
    }


    private char[] reformatCharSet(LinkedHashSet<Character> userCharSet) {
        char[] reformatCharSet = new char[userCharSet.size()];
        int i = 0;
        for (Character c : userCharSet) {
            reformatCharSet[i++] = c;
        }
        return reformatCharSet;

    }

    private void changeOutputMethod(String outputType) throws IOException {
        if ("html".equals(outputType) || "console".equals(outputType)) {
            userOutput = outputType;
            System.out.println("Output method set to " + outputType + ".");
        } else {
            throw new IOException("Did not change output method due to incorrect format.");
        }
    }


    private void changeResolution(String command) throws IOException {
        int imgWidth = this.UserImage.getWidth();
        int imgHeight = this.UserImage.getHeight();
        int minCharsInRow = Math.max(1, imgWidth / imgHeight);
        int maxCharsInRow = imgWidth;

        if ("up".equals(command)) {
            if (userResolution * 2 <= maxCharsInRow) {
                userResolution *= 2;
            } else {
                throw new IOException("Did not change resolution due to exceeding boundaries.");
            }
        } else if ("down".equals(command)) {
            if (userResolution / 2 >= minCharsInRow) {
                userResolution /= 2;
            } else {
                throw new IOException("Did not change resolution due to exceeding boundaries.");
            }
        } else {
            throw new IOException("Did not change resolution due to incorrect format.");
        }

        System.out.println("Resolution set to " + userResolution + ".");
    }


    private void changeImage(String imagePath) throws IOException {
        this.UserImage = new Image(imagePath); // Assuming Image constructor can throw IOException
        this.userImagePath = imagePath; // Update the path to the new image
        System.out.println("Image changed successfully to " + imagePath);

    }

    private void printCharset() {
        for (Character c : userCharSet) {
            System.out.print(c + " ");
        }
        System.out.println(); // Print a newline after listing all characters
    }

    private void addUserCharacters(String input) throws IOException{
        if (input.length() == 1) {
            userCharSet.add(input.charAt(0));
        } else if ("all".equals(input)) {
            for (char c = 32; c <= 126; c++) {
                userCharSet.add(c);
            }
            userCharSet.add(' ');

        } else if (input.matches("^[a-zA-Z0-9]-[a-zA-Z0-9]$")) {
            char start = input.charAt(0);
            char end = input.charAt(2);
            if (start > end) {
                char temp = start;
                start = end;
                end = temp;
            }
            for (char c = start; c <= end; c++) {
                userCharSet.add(c);
            }
        } else if (input.equals("space")){
            userCharSet.add(' ');
        } else {
            throw new IOException("Did not add due to incorrect format.");
        }
    }

    private void removeUserCharacters(String input) throws IOException{
        if (input.length() == 1) {
            userCharSet.remove(input.charAt(0));
        } else if ("all".equals(input)) {
            userCharSet.clear();
        } else if (input.matches("^[a-zA-Z0-9]-[a-zA-Z0-9]$")) {
            char start = input.charAt(0);
            char end = input.charAt(2);
            if (start > end) {
                char temp = start;
                start = end;
                end = temp;
            }
            for (char c = start; c <= end; c++) {
                userCharSet.remove(c);
            }
        } else if (input.equals("space")) {
            userCharSet.remove(' ');
        } else {
            throw new IOException("Did not remove due to incorrect format.");

        }
    }

    /**
     * The main entry point of the ASCII art application. This method initializes the Shell,
     * setting up the environment for user interaction. It handles any initial setup errors
     * and starts processing user commands for generating ASCII art.
     *
     * @param args Command-line arguments, not used in this application.
     */
    public static void main(String[] args) {
        try {
            Shell shell = new Shell();
            shell.run();
        }
        catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
