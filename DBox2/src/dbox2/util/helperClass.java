/**
 * @author Truben
 */

package dbox2.util;

import dbox2.*;
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import javax.swing.JFileChooser;


public class helperClass {

    public static boolean isMac() {
        if(Main.pref.getTypeOfFileDialog() == 1)
            return false;
        if(Main.pref.getTypeOfFileDialog() == 2)
            return true;
        if("Mac OS X".equals(System.getProperty("os.name")))
            return true;
        return false;
    }

    public static String showFileChooser(java.awt.Component c, String header, FileChooserFilter filter, boolean directories) {
        if(isMac()) { // AWT
            FileDialog fd = new FileDialog((Frame)c.getParent(), header, FileDialog.LOAD);

            if(!Main.pref.getLastUsedPath().equals(""))
                fd.setDirectory(Main.pref.getLastUsedPath()); // back to where we were

            fd.pack();
            fd.setVisible(true);

            if (fd.getFile() != null) {
                Main.pref.setLastUsedPath(fd.getDirectory());
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

            if(!Main.pref.getLastUsedPath().equals(""))
                fc.setCurrentDirectory(new File(Main.pref.getLastUsedPath())); // back to where we were

            fc.addChoosableFileFilter(filter);
            int returnVal = fc.showOpenDialog(c);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                Main.pref.setLastUsedPath(file.getAbsolutePath());
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
