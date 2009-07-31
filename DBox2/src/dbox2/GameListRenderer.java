
package dbox2;

import dbox2.util.ImageHandlerer;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.*;

/**
 *
 * @author Truben
 */
class GameListRenderer extends JLabel implements ListCellRenderer {

    ImageIcon favorite = ImageHandlerer.resizeIcon(new ImageIcon(getClass().getResource("/dbox2/img/emblem-favorite.png")));
    ImageIcon notfavorite = ImageHandlerer.resizeIcon(new ImageIcon(getClass().getResource("/dbox2/img/emblem-notfavorite.png")));
    JLabel l = new JLabel();

    public Component getListCellRendererComponent(
            JList list,
            Object value,           // value to display
            int index,              // cell index
            boolean isSelected,     // is the cell selected
            boolean cellHasFocus)   // the list and the cell have the focus
    {
        String s = value.toString();
        setText(" " + s);
        this.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        this.setLayout(new BorderLayout());

        if (Main.pref.isShowIcons()) {
            setSize(this.getWidth(), Main.pref.getIconHeight());
            try {
                setIcon(MainWindow.bl.getGame(s).getImageIcon());
            }
            catch(NullPointerException e) {
                // Do nothing
            }
        } else
            setIcon(null);
        

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

        if (MainWindow.bl.getGame(value.toString()) != null && MainWindow.bl.getGame(s).isStar())
            l.setIcon(favorite);
        else if (MainWindow.bl.getGame(value.toString()) != null && !MainWindow.bl.getGame(s).isStar())
            l.setIcon(notfavorite);
        
        this.add(l, BorderLayout.EAST);

        return this;
    }

}
