/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dbox2;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import nl.ikarus.nxt.priv.imageio.icoreader.obj.*;


/**
 *
 * @author pederskeidsvoll
 */
 class GameListRenderer extends JLabel implements ListCellRenderer {
     ImageIcon defaultIcon = new ImageIcon(getClass().getResource("/dbox2/gameIcons/application-x-executable.png"));


     public Component getListCellRendererComponent(
       JList list,
       Object value,            // value to display
       int index,               // cell index
       boolean isSelected,      // is the cell selected
       boolean cellHasFocus)    // the list and the cell have the focus
     {
         String s = value.toString();
         setText(s);
         if(MainWindow.pref.isShowIcons()) {
             setSize(this.getWidth(), MainWindow.pref.getIconHeight());
             String ikon = "";
             if(MainWindow.bl.getGame(s) != null)
                ikon = MainWindow.bl.getGame(s).getIcon();
             if(ikon.equals("")) {
                 setIcon(resizeIcon(defaultIcon));
             }
             else {
                 //try {
                    ImageIcon ii = defaultIcon;
                    if(ikon.toLowerCase().endsWith("ico")) { // If the file is a ICO file
                        try {
                            ImageInputStream in = ImageIO.createImageInputStream(new FileInputStream(new File(ikon)));
                            ICOFile f;
                            f = new ICOFile(in);
                            IconEntry ie = f.getEntry(0);
                            ii = new ImageIcon(ie.getBitmap().getImage());
                        } catch (IOException ex) {
                            System.out.println("Error reading icon " + ikon);
                        }
                    }
                    else
                        ii = new ImageIcon(ikon);

                    setIcon(resizeIcon(ii));
             }
         }
         else {
             setIcon(null);
         }

         if (isSelected) {
             setBackground(list.getSelectionBackground());
               setForeground(list.getSelectionForeground());
           }
         else {
               setBackground(list.getBackground());
               setForeground(list.getForeground());
           }
           setEnabled(list.isEnabled());
           setSize(this.getWidth(), MainWindow.pref.getIconHeight());
           setFont(list.getFont());
         setOpaque(true);
         return this;
     }

     /**
      * Resizes a icon according to preferences
      * @param icon icon that should be resized
      * @return a resized icon
      */
     private ImageIcon resizeIcon(ImageIcon icon) {
         if(MainWindow.pref.isIconResize()) {
             int width = MainWindow.pref.getIconWidth();
             int height = MainWindow.pref.getIconHeight();

             BufferedImage bi = new BufferedImage(width, height,
             BufferedImage.TYPE_INT_ARGB);
             bi.getGraphics().drawImage(icon.getImage(), 0, 0, width, height, null);

             return new ImageIcon(bi);
         }
         else
             return icon;
     }
 }
