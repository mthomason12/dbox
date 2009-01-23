/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dbox2;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.*;

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
         String ikon = MainWindow.bl.getGame(s).getIcon();
         if(ikon.equals(""))
             setIcon(defaultIcon); // NOI18N
         else {
             //try {
                ImageIcon ii = new ImageIcon(ikon);

                BufferedImage bi = new BufferedImage(22, 22,
                BufferedImage.TYPE_INT_ARGB);
                bi.getGraphics().drawImage(ii.getImage(), 0, 0, 22, 22, null);
                ii = new ImageIcon(bi);

                setIcon(ii);
                System.out.println(ikon);
             //}
             //catch(Exception e) {
                 //System.out.println("Icon for " + s + " not found! -> " + ikon);
                 //setIcon(defaultIcon);
             //}
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
