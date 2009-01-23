package dbox2;
/*
 * Preferences.java
 *
 * Created on 8. juni 2007, 16:39
 */

/**
 *
 * @author Truben
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewPreferences implements Serializable {
    
    private String DosBoxPath;
    private boolean KeepOpen = false;
    private boolean FullScreen = false;
    private boolean ShowIcons = true;
    private int IconWidth = 22;
    private int IconHeight = 22;
    private boolean IconResize = true;

    public void writeConfig(String filename) throws IOException {
        FileWriter fstream = new FileWriter(filename);
        BufferedWriter out = new BufferedWriter(fstream);
        out.write(this.toString());
        //Close the output stream
        out.close();
    }

    public void readConfig(String filename) {
        Scanner s = new Scanner("");
        try {
            s = new Scanner(new File(filename));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NewPreferences.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(s.hasNextLine()) {
            String io = s.nextLine();
            if(io.trim().startsWith("#"))
                continue; // Comment

            Scanner line = new Scanner(io.trim().toLowerCase());
            line.useDelimiter(":");
            String keyword = line.next();
            System.out.println("Keyword: " + keyword);

            if(keyword.equals("dosboxpath"))
                DosBoxPath = line.next().trim();
            else if(keyword.equals("iconwidth"))
                IconWidth = Integer.parseInt(line.next().trim());
            else if(keyword.equals("iconheight"))
                IconHeight = Integer.parseInt(line.next().trim());
            else if(keyword.equals("iconresize"))
                IconResize = Boolean.parseBoolean(line.next().trim());
            else if(keyword.equals("showicons"))
                ShowIcons = Boolean.parseBoolean(line.next().trim());
            else if(keyword.equals("fullscreen"))
                FullScreen = Boolean.parseBoolean(line.next().trim());
            else if(keyword.equals("keepopen"))
                KeepOpen = Boolean.parseBoolean(line.next().trim());

        }
    }

    public String toString() {
        return       "# D-Box' config file. Please handle with care!\n" +
                     "DosBoxPath: " + DosBoxPath + "\n" +
                     "KeepOpen: " + KeepOpen + "\n" +
                     "FullScreen: " + ShowIcons + "\n" +
                     "IconWidth: " + IconWidth + "\n" +
                     "IconHeight: " + IconHeight + "\n" +
                     "IconResize: " + IconResize;
    }

    public boolean isFullScreen() {
        return FullScreen;
    }

    public void readOldPref(Preferences p) {
        this.FullScreen = p.isFullScreen();
        this.KeepOpen = p.isKeepOpen();
        this.DosBoxPath = p.getDosBoxPath();
    }

    public void setFullScreen(boolean FullScreen) {
        this.FullScreen = FullScreen;
    }

    public boolean isKeepOpen() {
        return KeepOpen;
    }

    public void setKeepOpen(boolean KeepOpen) {
        this.KeepOpen = KeepOpen;
    }
    
    /** Creates a new instance of Preferences */
    public NewPreferences() {
        DosBoxPath = "";
        
    }

    public String getDosBoxPath() {
        return DosBoxPath;
    }

    public void setDosBoxPath(String DosBoxPath) {
        this.DosBoxPath = DosBoxPath;
    }

    /**
     * @return the ShowIcons
     */
    public boolean isShowIcons() {
        return ShowIcons;
    }

    /**
     * @param ShowIcons the ShowIcons to set
     */
    public void setShowIcons(boolean ShowIcons) {
        this.ShowIcons = ShowIcons;
    }

    /**
     * @return the IconWidth
     */
    public int getIconWidth() {
        return IconWidth;
    }

    /**
     * @param IconWidth the IconWidth to set
     */
    public void setIconWidth(int IconWidth) {
        this.IconWidth = IconWidth;
    }

    /**
     * @return the IconHeight
     */
    public int getIconHeight() {
        return IconHeight;
    }

    /**
     * @param IconHeight the IconHeight to set
     */
    public void setIconHeight(int IconHeight) {
        this.IconHeight = IconHeight;
    }

    /**
     * @return the IconResize
     */
    public boolean isIconResize() {
        return IconResize;
    }

    /**
     * @param IconResize the IconResize to set
     */
    public void setIconResize(boolean IconResize) {
        this.IconResize = IconResize;
    }
    
}
