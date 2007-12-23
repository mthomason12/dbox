

package dbox2;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Truben
 */
public class Main {

    /** Creates a new instance of Main */
    public Main() {
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            System.setProperty("apple.laf.useScreenMenuBar","true");
            java.lang.System.setProperty("com.apple.mrj.application.apple.menu.about.name", "D-Box");
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
            dbox2.MainWindow n = new dbox2.MainWindow();
            n.setVisible(true);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger("global").log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger("global").log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger("global").log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger("global").log(Level.SEVERE, null, ex);
        }
    }

}
