package dbox2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author truben
 */
public class Updater {

    private static String currentURL = "http://dbox.truben.no/current.php";
    private static String dboxURL = "http://dbox.truben.no";

    public static void CheckForUpdate(boolean notify) {
        URL updateURL;
        {
            BufferedReader in = null;
            try {

                updateURL = new URL(currentURL);
                in = new BufferedReader(new InputStreamReader(updateURL.openStream()));
                int[] version = parseVersion(in.readLine());
                boolean isNewerAvailable = isNewestVersion(version);
                if (isNewerAvailable) {
                    int answer = JOptionPane.showConfirmDialog(null, "There is a newer version of D-Box available!\nThe newest version is " +
                            version[0] + "." + version[1] + " and you have version " + Main.MAJORVERSION + "." + Main.MINORVERSION +
                            ".\nGo to D-Box' homepage and download?", "Newer version available", JOptionPane.YES_NO_OPTION);
                    if (answer == JOptionPane.YES_OPTION) {
                        BrowserControl.openUrl(dboxURL);
                    }
                } else if (notify) {
                    JOptionPane.showMessageDialog(null, "You have the newest version (" + Main.MAJORVERSION + "." + Main.MINORVERSION +
                            ")! Congratulations!", "No need to update", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Unable to connect to... \n" + currentURL);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Unable to check for new version beacuse of server-side problems.");
            } finally {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private static int[] parseVersion(String version) {
        String[] numbers = version.split("\\.");
        if (numbers.length != 2) {
            throw new NumberFormatException("Wrong number of dots");
        }
        return new int[]{Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1])};
    }

    private static boolean isNewestVersion(int[] version) {
        if (version[0] > Main.MAJORVERSION) {
            return true;
        }
        return version[1] > Main.MINORVERSION;
    }
}