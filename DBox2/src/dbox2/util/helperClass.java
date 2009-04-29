/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dbox2.util;

import dbox2.*;
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author pederskeidsvoll
 */
public class helperClass {


    public static boolean isMac() {
        if(MainWindow.pref.getTypeOfFileDialog() == 1)
            return false;
        if(MainWindow.pref.getTypeOfFileDialog() == 2)
            return true;
        if("Mac OS X".equals(System.getProperty("os.name")))
            return true;
        return false;
    }

    public static String showFileChooser(java.awt.Component c, String header, FileChooserFilter filter, boolean directories) {
        if(isMac()) { // AWT
            FileDialog fd = new FileDialog((Frame)c.getParent(), header, FileDialog.LOAD);

            if(!MainWindow.pref.getLastUsedPath().equals(""))
                fd.setDirectory(MainWindow.pref.getLastUsedPath()); // back to where we were

            fd.pack();
            fd.setVisible(true);

            if (fd.getFile() != null) {
                MainWindow.pref.setLastUsedPath(fd.getDirectory());
                if(directories)
                    return fd.getDirectory();
                else
                    return fd.getDirectory() + fd.getFile();
            }
            else
                return null;
        }
        else {  // SWING

            final JFileChooser fc = new JFileChooser();
            if(directories) fc.setFileSelectionMode(fc.DIRECTORIES_ONLY);

            if(!MainWindow.pref.getLastUsedPath().equals(""))
                fc.setCurrentDirectory(new File(MainWindow.pref.getLastUsedPath())); // back to where we were

            fc.addChoosableFileFilter(filter);
            int returnVal = fc.showOpenDialog(c);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                MainWindow.pref.setLastUsedPath(file.getAbsolutePath());
                if(directories)
                    return file.getAbsolutePath();
                else
                    return file.getAbsolutePath();
            }
            else
                return null;
        }
    }
}
