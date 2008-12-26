/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dbox2;

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
        if("Mac OS X".equals(System.getProperty("os.name")))
            return true;
        return false;
    }

    public static String getFileAWT(java.awt.Component c) {
        if(isMac()) {
            FileDialog fd = new FileDialog((Frame)c.getParent(), "Select File", FileDialog.LOAD);
            //fd.setDirectory(lastdir); // back to where we were
            fd.pack();
            fd.setVisible(true);

            //lastdir = fd.getDirectory();
            if (fd.getFile() != null)
                return fd.getDirectory() + fd.getFile();
            else
                return null;
        }
        else {
            final JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(c);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                return file.getAbsolutePath();
            }
            else
                return null;
        }
    }

}
