
package dbox2;

import dbox2.util.ImageHandlerer;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.*;

/**
 *
 * @author Truben
 */
class GameListRenderer extends JLabel implements ListCellRenderer {

    ImageIcon favorite = ImageHandlerer.resizeIcon(new ImageIcon(Main.theme.getGameFavoriteImage()));
    ImageIcon notfavorite = ImageHandlerer.resizeIcon(new ImageIcon(Main.theme.getGameNotFavoriteImage()));
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
        this.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 0));
        this.setLayout(new BorderLayout());

        if (Main.pref.isShowIcons()) {
            setSize(this.getWidth()/2, Main.pref.getIconHeight()+4);
            try {
                setIcon(MainWindow.bl.getGame(s).getImageIcon());
            }
            catch(NullPointerException e) {
                // Do nothing
            }
        } else
            setIcon(null);
        

        if (isSelected) {
            setBackground(Main.theme.getGameSelectedBackgroundColor());
            setForeground(Main.theme.getGameSelectedForegroundColor());
            setOpaque(true);
        } else {
            setForeground(Main.theme.getGameForegroundColor());
            setOpaque(false);
        }

        // Star this game

        if (MainWindow.bl.getGame(value.toString()) != null && MainWindow.bl.getGame(s).isStar())
            l.setIcon(favorite);
        else if (MainWindow.bl.getGame(value.toString()) != null && !MainWindow.bl.getGame(s).isStar())
            l.setIcon(notfavorite);
        
        this.add(l, BorderLayout.EAST);

        Dimension d = new Dimension(list.getWidth()/Main.pref.getNumerOfColumnsInGameList(), this.getHeight());
        setPreferredSize(d);
        setSize(d);


        return this;
    }

}
