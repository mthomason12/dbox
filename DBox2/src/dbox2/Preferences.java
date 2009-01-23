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

import java.io.Serializable;

public class Preferences implements Serializable {
    
    private String DosBoxPath;
    private boolean KeepOpen = false;
    private boolean FullScreen = false;

    public boolean isFullScreen() {
        return FullScreen;
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
    public Preferences() {
        DosBoxPath = "";
        
    }

    public String getDosBoxPath() {
        return DosBoxPath;
    }

    public void setDosBoxPath(String DosBoxPath) {
        this.DosBoxPath = DosBoxPath;
    }
    
    
    

    
}
