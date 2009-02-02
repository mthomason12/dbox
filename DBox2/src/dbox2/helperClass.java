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
        if(MainWindow.pref.getTypeOfFileDialog() == 1)
            return false;
        if(MainWindow.pref.getTypeOfFileDialog() == 2)
            return true;
        if("Mac OS X".equals(System.getProperty("os.name")))
            return true;
        return false;
    }

    public static String getDirAWT(java.awt.Component c) {
        if(isMac()) {
            FileDialog fd = new FileDialog((Frame)c.getParent(), "Select Directory", FileDialog.LOAD);

            if(!MainWindow.pref.getLastUsedPath().equals(""))
                fd.setDirectory(MainWindow.pref.getLastUsedPath()); // back to where we were
            fd.pack();
            fd.setVisible(true);

            //lastdir = fd.getDirectory();
            if (fd.getFile() != null) {
                MainWindow.pref.setLastUsedPath(fd.getDirectory());
                return fd.getDirectory();

                }
            else
                return null;
        }
        else {
            final JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(fc.DIRECTORIES_ONLY);
            if(!MainWindow.pref.getLastUsedPath().equals(""))
                fc.setCurrentDirectory(new File(MainWindow.pref.getLastUsedPath())); // back to where we were
            fc.addChoosableFileFilter(new DirFilteret());
            int returnVal = fc.showOpenDialog(c);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                MainWindow.pref.setLastUsedPath(file.getAbsolutePath());
                return file.getAbsolutePath();
            }
            else
                return null;
        }
    }

    public static String getFileAWT(java.awt.Component c) {
        if(isMac()) {
            FileDialog fd = new FileDialog((Frame)c.getParent(), "Select File", FileDialog.LOAD);
            if(!MainWindow.pref.getLastUsedPath().equals(""))
                fd.setDirectory(MainWindow.pref.getLastUsedPath()); // back to where we were
            fd.pack();
            fd.setVisible(true);

            //lastdir = fd.getDirectory();
            if (fd.getFile() != null) {
                MainWindow.pref.setLastUsedPath(fd.getDirectory());
                return fd.getDirectory() + fd.getFile();
                
                }
            else
                return null;
        }
        else {
            final JFileChooser fc = new JFileChooser();
            if(!MainWindow.pref.getLastUsedPath().equals(""))
                fc.setCurrentDirectory(new File(MainWindow.pref.getLastUsedPath())); // back to where we were
            fc.addChoosableFileFilter(new Filteret());
            int returnVal = fc.showOpenDialog(c);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                MainWindow.pref.setLastUsedPath(file.getAbsolutePath());
                return file.getAbsolutePath();
            }
            else
                return null;
        }
    }
    public static String getFileAWTDosBox(java.awt.Component c) {
        if(isMac()) {
            FileDialog fd = new FileDialog((Frame)c.getParent(), "Select File", FileDialog.LOAD);
            if(!MainWindow.pref.getLastUsedPath().equals(""))
                fd.setDirectory(MainWindow.pref.getLastUsedPath()); // back to where we were
            fd.pack();
            fd.setVisible(true);

            //lastdir = fd.getDirectory();
            if (fd.getFile() != null){
                MainWindow.pref.setLastUsedPath(fd.getDirectory());
                return fd.getDirectory() + fd.getFile();}
            else
                return null;
        }
        else {
            final JFileChooser fc = new JFileChooser();
            if(!MainWindow.pref.getLastUsedPath().equals(""))
                fc.setCurrentDirectory(new File(MainWindow.pref.getLastUsedPath())); // back to where we were
            fc.addChoosableFileFilter(new DosBoxFilteret());
            int returnVal = fc.showOpenDialog(c);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                MainWindow.pref.setLastUsedPath(file.getAbsolutePath());
                return file.getAbsolutePath();
            }
            else
                return null;
        }
    }

        public static String getFileAWTIcon(java.awt.Component c) {
        if(isMac()) {
            FileDialog fd = new FileDialog((Frame)c.getParent(), "Select File", FileDialog.LOAD);
            if(!MainWindow.pref.getLastUsedPath().equals(""))
                fd.setDirectory(MainWindow.pref.getLastUsedPath()); // back to where we were
            fd.pack();
            fd.setVisible(true);

            //lastdir = fd.getDirectory();
            if (fd.getFile() != null){
                MainWindow.pref.setLastUsedPath(fd.getDirectory());
                return fd.getDirectory() + fd.getFile();}
            else
                return null;
        }
        else {
            final JFileChooser fc = new JFileChooser();
            if(!MainWindow.pref.getLastUsedPath().equals(""))
                fc.setCurrentDirectory(new File(MainWindow.pref.getLastUsedPath())); // back to where we were
            fc.addChoosableFileFilter(new IconFilteret());
            int returnVal = fc.showOpenDialog(c);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                MainWindow.pref.setLastUsedPath(file.getAbsolutePath());
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

class IconFilteret extends javax.swing.filechooser.FileFilter {
        public boolean accept(File file) {
            String filename = file.getName().toLowerCase();
            if(file.isDirectory())
                return true;
            if(filename.endsWith(".ico"))
                return true;
            if(filename.endsWith(".gif"))
                return true;
            if(filename.endsWith(".png"))
                return true;
            if(filename.endsWith(".jpg"))
                return true;

            return false;
        }
        public String getDescription() {
            return "Icon files (*.ico, *.gif, *.png, *.jpg)";
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

class DirFilteret extends javax.swing.filechooser.FileFilter {
        public boolean accept(File file) {
            if(file.isDirectory())
                return true;
            else
                return false;
        }
        public String getDescription() {
            return "Directories";
        }
    }