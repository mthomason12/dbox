package dbox2;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 *
 * @author psk085
 */
class ImageRenderer extends DefaultListCellRenderer
{
    @Override
    public Component getListCellRendererComponent(JList list,
                                                  Object value,
                                                  int index,
                                                  boolean isSelected,
                                                  boolean cellHasFocus)
    {
        // for default cell renderer behavior
        Component c = super.getListCellRendererComponent(list, value,
                                       index, isSelected, cellHasFocus);
        // set icon for cell image
        ((JLabel)c).setIcon(new ImageIcon((BufferedImage)value));
        ((JLabel)c).setText("");
        return c;
    }
}
