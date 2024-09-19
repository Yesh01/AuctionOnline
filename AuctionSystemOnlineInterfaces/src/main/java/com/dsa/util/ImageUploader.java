package com.dsa.util;

import java.io.File;

import javax.swing.JFileChooser;

public class ImageUploader {

    // Method to upload an image and return the file path
    public String uploadImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose an image");
        
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();  // Returns the path of the selected image
        }
        return null;
    }
}
