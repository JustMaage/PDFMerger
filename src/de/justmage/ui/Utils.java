package de.justmage.ui;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;

public class Utils {

    public static String chooseFile(final File currFolder, final Component parent) {
        final JFileChooser chooser = new JFileChooser(currFolder);
        if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION)
            return chooser.getSelectedFile().getAbsolutePath();
        return null;
    }

    public static String chooseFile(File currFolder, final Component parent, FileFilter filter) {
        return chooseFile(currFolder, parent, filter, false);
    }

    public static String chooseFile(File currFolder, final Component parent, FileFilter filter, boolean toSave) {
        if(currFolder == null) {
            try {
                currFolder = new File(Utils.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            } catch (Exception ignored) {
            }
        }
        final JFileChooser chooser = new JFileChooser(currFolder);
        chooser.setFileFilter(filter);
        int result;
        if(toSave)
            result = chooser.showSaveDialog(parent);
        else
            result = chooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION)
            return chooser.getSelectedFile().getAbsolutePath();
        return null;
    }

}
