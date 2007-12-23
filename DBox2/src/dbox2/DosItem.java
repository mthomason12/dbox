/*
 * DosItem.java
 *
 * Created on 7. juni 2007, 15:47
 *
 */

package dbox2;

import java.io.Serializable;

/**
 *
 * @author Truben
 */
public class DosItem implements Serializable {
    
    private String name="";
    private String path="";
    private String gamefile="";
    private String installer="";
    private String icon="";
    private int cycles=6000;
    private int frameskip=0;
    private String cdrom="";
    private String floppy="";
    private String extra="";

    public int getFrameskip() {
        return frameskip;
    }

    public void setFrameskip(int frameskip) {
        this.frameskip = frameskip;
    }
    

    public String getFloppy() {
        return floppy;
    }

    public void setFloppy(String floppy) {
        this.floppy = floppy;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
    
    /** Creates a new instance of DosItem */
    public DosItem() {
        
    }

    public String getCdrom() {
        return cdrom;
    }

    public void setCdrom(String cdrom) {
        this.cdrom = cdrom;
    }
    
    public DosItem(String nname, String npath, String ngame, String ninstaller, int ncycles, String ncdrom) {
        path = npath;
        cdrom = ncdrom;
        gamefile = ngame;
        installer = ninstaller;
        cycles = ncycles;
        name = nname;
    }

	public int getCycles() {
		return cycles;
	}

	public void setCycles(int cycles) {
		this.cycles = cycles;
	}
        
        public String getName() {
		return name;
	}

	public void setName(String nname) {
		name = nname;
	}
        
        public String getIcon() {
		return icon;
	}

	public void setIcon(String name) {
		this.icon = name;
	}
        
        public String toString() {
            return getName();
        }
        
	public String getGame() {
		return gamefile;
	}

	public void setGame(String game) {
		this.gamefile = game;
	}

	public String getInstaller() {
		return installer;
	}

	public void setInstaller(String installer) {
		this.installer = installer;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
        
        public boolean equals(DosItem d) {
            if(d.getCycles() != cycles)
                return false;
            if(!d.getGame().equals(gamefile))
                return false;
            if(!d.getIcon().equals(icon))
                return false;
            if(!d.getPath().equals(path))
                return false;
            if(!d.getCdrom().equals(cdrom))
                return false;
            if(!d.getFloppy().equals(floppy))
                return false;
            if(!d.getExtra().equals(extra))
                return false;
            if(d.getFrameskip() != frameskip)
                return false;
            return true;
        }
    
    
}
