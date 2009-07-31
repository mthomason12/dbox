/*
 * DosItem.java
 *
 * Created on 7. juni 2007, 15:47
 * @author Truben
 *
 */
package dbox2;

import dbox2.util.ImageHandlerer;
import java.io.Serializable;
import javax.swing.ImageIcon;

public class DosItem implements Serializable {

    private String name = "";
    private String path = "";
    private String gamefile = "";
    private String installer = "";
    private String icon = "";
    private int cycles = 6000;
    private int frameskip = 0;
    private String cdrom = "";
    private String floppy = "";
    private String extra = "";
    private String genre = Main.pref.getGenres()[Main.pref.getGenres().length - 1];
    private String keywords = "";
    private boolean star = false;
    private ImageIcon imageIcon = ImageHandlerer.getDefaultIcon();

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public boolean isStar() {
        return star;
    }

    public void setStar(boolean star) {
        this.star = star;
    }

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
        if (!this.icon.equals(name)) {
            this.icon = name;
            this.imageIcon = ImageHandlerer.getImageIcon(name);
        }
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getKeywords() {
        return keywords;
    }

    public String toConfigString() {
        return "start game" + "\n" +
                "  name := " + getName() + "\n" +
                "  path := " + getPath() + "\n" +
                "  game := " + getGame() + "\n" +
                "  genre := " + getGenre() + "\n" +
                "  keywords := " + getKeywords() + "\n" +
                "  installer := " + getInstaller() + "\n" +
                "  floppy := " + getFloppy() + "\n" +
                "  cdrom := " + getCdrom() + "\n" +
                "  extra :=" + getExtra() + "\n" +
                "  icon := " + getIcon() + "\n" +
                "  cycles := " + getCycles() + "\n" +
                "  frameskip := " + getFrameskip() + "\n" +
                "  favorite := " + isStar() + "\n" +
                "end game\n\n";
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public boolean equals(DosItem d) {
        if (d.getCycles() != cycles) {
            return false;
        }
        if (!d.getGame().equals(gamefile)) {
            return false;
        }
        if (!d.getIcon().equals(icon)) {
            return false;
        }
        if (!d.getPath().equals(path)) {
            return false;
        }
        if (!d.getCdrom().equals(cdrom)) {
            return false;
        }
        if (!d.getFloppy().equals(floppy)) {
            return false;
        }
        if (!d.getExtra().equals(extra)) {
            return false;
        }
        if (!d.getGenre().equals(genre)) {
            return false;
        }
        if (!d.getKeywords().equals(keywords)) {
            return false;
        }
        return true;
    }
}
