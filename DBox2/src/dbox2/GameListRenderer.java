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
             String ikon = "";
             if(MainWindow.bl.getGame(s) != null)
                ikon = MainWindow.bl.getGame(s).getIcon();
             if(ikon.equals(""))
                 setIcon(defaultIcon);
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
                        }
                    }
                    else
                        ii = new ImageIcon(ikon);

                    BufferedImage bi = new BufferedImage(22, 22,
                    BufferedImage.TYPE_INT_ARGB);
                    bi.getGraphics().drawImage(ii.getImage(), 0, 0, 22, 22, null);
                    ii = new ImageIcon(bi);

                    setIcon(ii);
                    //System.out.println(ikon);
                 //}
                 //catch(Exception e) {
                     //System.out.println("Icon for " + s + " not found! -> " + ikon);
                     //setIcon(defaultIcon);
                 //}
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
           setFont(list.getFont());
         setOpaque(true);
         return this;
     }
 }
