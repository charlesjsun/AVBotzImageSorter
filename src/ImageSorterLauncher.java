
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class ImageSorterLauncher {

    public static final String INPUT_FOLDER = "images";
    public static final String OUTPUT_FOLDER = "output";
    public static final String IMAGE_TYPES_FILE = "image_types.txt";

    public static void main(String[] args) {

        // Import the types of images
        String[] types = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader("image_types.txt"));
            types = br.lines().toArray(size -> new String[size]);
        } catch (FileNotFoundException e) {
            System.err.println("*Face palm* I'm not mad, just diappointed. You did not create image_types.txt");
            e.printStackTrace();
        }

        // Import or create the input folder
        File input = new File(INPUT_FOLDER + "/");
        if (!input.exists()) {
            input.mkdir();
        }
        DirectoryStream<Path> inputDirectory = null;
        try {
            inputDirectory = Files.newDirectoryStream(Paths.get(input.toURI()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Import or create the output folder
        File output = new File(OUTPUT_FOLDER + "/");
        if (!output.exists()) {
            output.mkdir();
        }

        // Fill the output folder with type folders
        File[] typeFolders = new File[types.length];
        for (int i = 0; i < types.length; ++i) {
            types[i] = types[i].toLowerCase();
            typeFolders[i] = new File(OUTPUT_FOLDER + "/" + types[i] + "/");
            if (!typeFolders[i].exists()) {
                typeFolders[i].mkdir();
            }
        }

        new ImageSorterUI(inputDirectory, types).setVisible(true);
        // Make the actual gui
        //new ImageSorterFrame(input, typeFolders);

    }

    public static void displayImage(File file) {

        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        JFrame editorFrame = new JFrame("Image " + file.getName());
        editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ImageIcon imageIcon = new ImageIcon(image);
        JLabel jLabel = new JLabel();
        jLabel.setIcon(imageIcon);
        editorFrame.getContentPane().add(jLabel, BorderLayout.CENTER);

        editorFrame.pack();
        editorFrame.setLocationRelativeTo(null);
        editorFrame.setVisible(true);

    }

}
