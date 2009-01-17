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

    public static String currentDir = "";


    public static boolean isMac() {
        if("Mac OS X".equals(System.getProperty("os.name")))
            return true;
        return false;
    }

    public static String getFileAWT(java.awt.Component c) {
        if(isMac()) {
            FileDialog fd = new FileDialog((Frame)c.getParent(), "Select File", FileDialog.LOAD);
            if(!currentDir.equals(""))
                fd.setDirectory(currentDir); // back to where we were
            fd.pack();
            fd.setVisible(true);

            //lastdir = fd.getDirectory();
            if (fd.getFile() != null) {
                currentDir = fd.getDirectory();
                return fd.getDirectory() + fd.getFile();
                
                }
            else
                return null;
        }
        else {
            final JFileChooser fc = new JFileChooser();
            if(!currentDir.equals(""))
                fc.setCurrentDirectory(new File(currentDir)); // back to where we were
            fc.addChoosableFileFilter(new Filteret());
            int returnVal = fc.showOpenDialog(c);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                currentDir = file.getAbsolutePath();
                return file.getAbsolutePath();
            }
            else
                return null;
        }
    }
    public static String getFileAWTDosBox(java.awt.Component c) {
        if(isMac()) {
            FileDialog fd = new FileDialog((Frame)c.getParent(), "Select File", FileDialog.LOAD);
            if(!currentDir.equals(""))
                fd.setDirectory(currentDir); // back to where we were
            fd.pack();
            fd.setVisible(true);

            //lastdir = fd.getDirectory();
            if (fd.getFile() != null){
                currentDir = fd.getDirectory();
                return fd.getDirectory() + fd.getFile();}
            else
                return null;
        }
        else {
            final JFileChooser fc = new JFileChooser();
            if(!currentDir.equals(""))
                fc.setCurrentDirectory(new File(currentDir)); // back to where we were
            fc.addChoosableFileFilter(new DosBoxFilteret());
            int returnVal = fc.showOpenDialog(c);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                currentDir = file.getAbsolutePath();
                return file.getAbsolutePath();
            }
            else
                return null;
        }
    }

}

class Filteret extends javax.swing.filechooser.FileFilter {
        public boolean accept(File file) {
            String filename = file.getName().toLowerCase();
            if(file.isDirectory())
                return true;
            if(filename.endsWith(".exe"))
                return true;
            if(filename.endsWith(".com"))
                return true;
            if(filename.endsWith(".bat"))
                return true;

            return false;
        }
        public String getDescription() {
            return "Executable DOS files (*.exe, *.com, *.bat)";
        }
    }
class DosBoxFilteret extends javax.swing.filechooser.FileFilter {
        public boolean accept(File file) {
            String filename = file.getName().toLowerCase();
            if(file.isDirectory())
                return true;
            if(filename.startsWith("dosbox"))
                return true;
            return false;
        }
        public String getDescription() {
            return "DosBox Executable";
        }
    }
