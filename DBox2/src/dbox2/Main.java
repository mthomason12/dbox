

package dbox2;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Truben
 */
public class Main {

    public static final int MAJORVERSION = 1;
    public static final int MINORVERSION = 7;
    public static String configFile = "dbox.config";
    public static String gameFile = "gamelist.dat";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // Read command line config
            for(int i = 0; i < args.length; i++) {
                if(args[i].toLowerCase().equals("-config")) {
                    configFile = args[++i];
                    System.out.println("Use config file " + configFile);
                }
            }

            System.setProperty("apple.laf.useScreenMenuBar","true");
            java.lang.System.setProperty("com.apple.mrj.application.apple.menu.about.name", "D-Box");
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
            MainWindow n = new MainWindow();
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
