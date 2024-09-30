import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageFilterApplication {
    private BufferedImage originalImage;
    private BufferedImage filteredImage;
    private JFrame frame;
    private JPanel panel;
    private JButton grayscaleButton;
    private JButton sepiaButton;
    private JButton resetButton;
    private JLabel imageLabel;

    public ImageFilterApplication() {
        frame = new JFrame("Image Filter Application");
        panel = new JPanel();
        grayscaleButton = new JButton("Grayscale");
        sepiaButton = new JButton("Sepia");
        resetButton = new JButton("Reset");
        imageLabel = new JLabel();

        frame.setLayout(new BorderLayout());
        panel.add(grayscaleButton);
        panel.add(sepiaButton);
        panel.add(resetButton);
        frame.add(panel, BorderLayout.NORTH);
        frame.add(imageLabel, BorderLayout.CENTER);

        grayscaleButton.addActionListener(e -> applyGrayscaleFilter());
        sepiaButton.addActionListener(e -> applySepiaFilter());
        resetButton.addActionListener(e -> resetImage());

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose an image");
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            loadImage(selectedFile.getPath());
        }

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void loadImage(String imagePath) {
        try {
            originalImage = ImageIO.read(new File(imagePath));
            filteredImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            filteredImage.getGraphics().drawImage(originalImage, 0, 0, null);
            updateImageLabel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateImageLabel() {
        if (filteredImage != null) {
            ImageIcon imageIcon = new ImageIcon(filteredImage);
            imageLabel.setIcon(imageIcon);
            frame.revalidate();
        }
    }

    private void applyGrayscaleFilter() {
        if (originalImage != null) {
            for (int y = 0; y < originalImage.getHeight(); y++) {
                for (int x = 0; x < originalImage.getWidth(); x++) {
                    Color pixelColor = new Color(originalImage.getRGB(x, y));
                    int avg = (pixelColor.getRed() + pixelColor.getGreen() + pixelColor.getBlue()) / 3;
                    Color grayscaleColor = new Color(avg, avg, avg);
                    filteredImage.setRGB(x, y, grayscaleColor.getRGB());
                }
            }
            updateImageLabel();
        }
    }

    private void applySepiaFilter() {
        if (originalImage != null) {
            for (int y = 0; y < originalImage.getHeight(); y++) {
                for (int x = 0; x < originalImage.getWidth(); x++) {
                    Color pixelColor = new Color(originalImage.getRGB(x, y));
                    int r = pixelColor.getRed();
                    int g = pixelColor.getGreen();
                    int b = pixelColor.getBlue();
                    int tr = (int) (0.393 * r + 0.769 * g + 0.189 * b);
                    int tg = (int) (0.349 * r + 0.686 * g + 0.168 * b);
                    int tb = (int) (0.272 * r + 0.534 * g + 0.131 * b);
                    tr = Math.min(255, tr);
                    tg = Math.min(255, tg);
                    tb = Math.min(255, tb);
                    Color sepiaColor = new Color(tr, tg, tb);
                    filteredImage.setRGB(x, y, sepiaColor.getRGB());
                }
            }
            updateImageLabel();
        }
    }

    private void resetImage() {
        if (originalImage != null) {
            filteredImage.getGraphics().drawImage(originalImage, 0, 0, null);
            updateImageLabel();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ImageFilterApplication::new);
    }
}
