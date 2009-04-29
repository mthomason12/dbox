/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbox2;

import dbox2.util.ImageHandlerer;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

    ImageIcon favorite = ImageHandlerer.resizeIcon(new ImageIcon(getClass().getResource("/dbox2/img/emblem-favorite.png")));
    ImageIcon notfavorite = ImageHandlerer.resizeIcon(new ImageIcon(getClass().getResource("/dbox2/img/emblem-notfavorite.png")));
    JLabel l = new JLabel();

    public Component getListCellRendererComponent(
            JList list,
            Object value, // value to display
            int index, // cell index
            boolean isSelected, // is the cell selected
            boolean cellHasFocus) // the list and the cell have the focus
    {
        String s = value.toString();
        setText(" " + s);
        this.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        this.setLayout(new BorderLayout());

        if (MainWindow.pref.isShowIcons()) {
            setSize(this.getWidth(), MainWindow.pref.getIconHeight());
            

            setIcon(MainWindow.bl.getGame(s).getImageIcon());
            /*
            String ikon = "";

            if (MainWindow.bl.getGame(s) != null) {
                ikon = MainWindow.bl.getGame(s).getIcon();
            }
            if (ikon.equals("")) {
                setIcon(defaultIcon);
            } else {
                //try {
                ImageIcon ii = defaultIcon;
                if (ikon.toLowerCase().endsWith("ico")) { // If the file is a ICO file
                    try {
                        ImageInputStream in = ImageIO.createImageInputStream(new FileInputStream(new File(ikon)));
                        ICOFile f;
                        f = new ICOFile(in);
                        IconEntry ie = f.getEntry(0);
                        ii = new ImageIcon(ie.getBitmap().getImage());
                    } catch (IOException ex) {
                        System.out.println("Error reading icon " + ikon);
                    }
                } else {
                    ii = new ImageIcon(ikon);
                }

                setIcon(resizeIcon(ii));
                
            }
             * */
        } else {
            setIcon(null);
        }

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
            setOpaque(true);
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            setOpaque(false);
        }

        // Star this game

        if (MainWindow.bl.getGame(value.toString()) != null && MainWindow.bl.getGame(s).isStar()) {
            l.setIcon(favorite);
            l.setToolTipText("Remove from favorites");
        } else if (MainWindow.bl.getGame(value.toString()) != null && !MainWindow.bl.getGame(s).isStar()) {
            l.setIcon(notfavorite);
            l.setToolTipText("Add to favorites");
        }
        this.add(l, BorderLayout.EAST);

        return this;
    }


}
