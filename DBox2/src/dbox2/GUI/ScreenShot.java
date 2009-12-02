package dbox2.GUI;

import dbox2.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;

/**
 *
 * @author truben
 */
public class ScreenShot extends JDialog implements MouseListener, KeyListener, FocusListener, MouseWheelListener {

    PanelImage panelImage;
    File[] files;
    int pictureNumber = 0;


    public ScreenShot(URL url, File[] files) {
        this.files = files;
        panelImage = new PanelImage(url);
        setUp();
        
    }

    public void mouseClicked(MouseEvent e) {
        dispose();
    }

    public void mousePressed(MouseEvent e) {
        dispose();
    }

    public void mouseReleased(MouseEvent e) {
        dispose();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() == ' ' || e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_ENTER)
            dispose();
        else if(e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
            deleteImage();
        else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_UP)
            prevImage();
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_DOWN)
            nextImage();

    }

    private void nextImage() {
        pictureNumber++;
        pictureNumber = pictureNumber % files.length;
        showImage();
    }

    private void prevImage() {
        pictureNumber--;
        if(pictureNumber < 0)
            pictureNumber = files.length - 1;
        else
            pictureNumber = pictureNumber % files.length;
        showImage();
    }

    private void showImage() {
        if(files != null) {
            try {
                this.remove(panelImage);
                panelImage = null;
                panelImage = new PanelImage(files[pictureNumber].toURI().toURL());
                panelImage.setBackground(Color.BLUE);
                Image img = panelImage.getBackgroundImage();
                this.setSize(new Dimension(img.getWidth(this),img.getHeight(this)));
                panelImage.setSize(img.getWidth(this), img.getHeight(this));
                panelImage.setVisible(true);
                this.add(panelImage,BorderLayout.CENTER);
                Dimension d = Main.n.getSize();
                Point p = Main.n.getLocation();
                p.setLocation(p.getX()+(d.getWidth()/2) - (this.getWidth()/2), p.getY()+(d.getHeight()/2) - (this.getHeight()/2));
                this.setLocation(p);
                this.repaint();
            } catch (MalformedURLException ex) {
                Logger.getLogger(ScreenShot.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void dispose() {
        this.setVisible(false);
        Main.n.requestFocus();
        Main.n.setFocusable(true);
        Main.n.applicationList.setFocusable(true);
        Main.n.applicationList.requestFocus();
    }

    private void setUp() {
        this.setLayout(new BorderLayout());
        this.add(panelImage, BorderLayout.CENTER);
        Image img = panelImage.getBackgroundImage();
        this.setSize(new Dimension(img.getWidth(this),img.getHeight(this)));
        this.setBackground(Color.BLACK);
        this.setUndecorated(true);
        this.addMouseListener(this);
        panelImage.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,Color.WHITE,Color.WHITE));
        this.addKeyListener(this);
        this.addMouseWheelListener(this);
        this.addFocusListener(this);
        Point p = Main.n.getLocation();
        Dimension d = Main.n.getSize();
        p.setLocation(p.getX()+(d.getWidth()/2) - (this.getWidth()/2), p.getY()+(d.getHeight()/2) - (this.getHeight()/2));
        this.setLocation(p);

        this.setVisible(true);
    }

    public void focusGained(FocusEvent e) {

    }

    public void focusLost(FocusEvent e) {
        dispose();
    }

    private void deleteImage() {
        this.removeFocusListener(this);
        if(JOptionPane.showConfirmDialog(this, "Do you want to delete this screenshot?") == JOptionPane.YES_OPTION) {
            files[pictureNumber].delete();
            dispose();
        }
        this.addFocusListener(this);
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        if(e.getWheelRotation() > 0)
            nextImage();
        else if(e.getWheelRotation() < 0)
            prevImage();
    }

    
}
