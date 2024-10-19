import javax.swing.JFileChooser;
import java.io.File;

public class ImageChooser {

    public static File chooseImageFile() {
        // Create a File object representing the folder we want to choose from
        File folder = new File("path/to/image/folder");

        // Create a JFileChooser object
        JFileChooser fileChooser = new JFileChooser(folder);

        // Set the file filter to only show image files
        String[] extensions = {"jpg", "jpeg", "png", "gif"};
        String[] descriptions = {"JPEG files", "PNG files", "GIF files"};

        fileChooser.setFileFilter(new CustomFileFilter(extensions, descriptions));

        // Show the file chooser dialog to the user
        int result = fileChooser.showOpenDialog(null);

        // Get the selected file from the file chooser
        File selectedFile = null;
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
        }

        // Validate that a file was actually selected
        if (selectedFile != null && selectedFile.exists()) {
            return selectedFile;
        } else {
            System.out.println("No image file selected.");
            return null;
        }
    }

    private static class CustomFileFilter extends javax.swing.filechooser.FileFilter {
        String[] extensions;
        String[] descriptions;

        public CustomFileFilter(String[] extensions, String[] descriptions) {
            this.extensions = extensions;
            this.descriptions = descriptions;
        }

        @Override
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            String extension = getFileExtension(f);
            if (extension != null) {
                for (int i = 0; i < extensions.length; i++) {
                    if (extensions[i].equalsIgnoreCase(extension)) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public String getDescription() {
            return String.join(", ", descriptions);
        }

        private String getFileExtension(File f) {
            String s = f.getName();
            int i = s.lastIndexOf('.');
            if (i > 0 && i < s.length() - 1) {
                return s.substring(i + 1).toLowerCase();
            }
            return "";
        }
    }
}
