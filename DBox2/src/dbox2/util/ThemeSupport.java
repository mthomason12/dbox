package dbox2.util;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 *
 * @author truben
 */
public class ThemeSupport {

    private String name   = "Default theme";
    private String author = "Peder Skeidsvoll";
    private String url    = "http://www.truben.no";

    private URL playInactiveImage = getClass().getResource("/dbox2/img/media-playback-start-disabled.png");
    private URL playActiveImage = getClass().getResource("/dbox2/img/media-playback-start.png");

    private URL editInactiveImage = getClass().getResource("/dbox2/img/utilities-terminal-disabled.png");
    private URL editActiveImage = getClass().getResource("/dbox2/img/utilities-terminal.png");

    private URL toolsInactiveImage = getClass().getResource("/dbox2/img/emblem-system-disabled.png");
    private URL toolsActiveImage = getClass().getResource("/dbox2/img/emblem-system.png");

    private URL searchInactiveImage = getClass().getResource("/dbox2/img/down-arrow-disabled.png");
    private URL searchActiveImage = getClass().getResource("/dbox2/img/down-arrow.png");

    private boolean showWindowDecoration = true;
    private boolean showBorders = true;


    private URL backgroundImage = getClass().getResource("/dbox2/img/bg.jpg");
    private int backgroundRepeat = 100;
    private Color backgroundColor = java.awt.SystemColor.window;

    private Color gameBackgroundColor = java.awt.SystemColor.text;
    private Color gameForegroundColor = java.awt.SystemColor.textText;
    private Color gameSelectedBackgroundColor = java.awt.SystemColor.textHighlight; // TODO: Get the real color
    private Color gameSelectedForegroundColor = java.awt.SystemColor.textHighlightText;

    private URL defaultGame = getClass().getResource("/dbox2/img/down-arrow.png");

    private Color searchBackgroundColor = java.awt.SystemColor.text;
    private Color searchForegroundColor = java.awt.SystemColor.textText;
    private Color searchInactiveColor = java.awt.SystemColor.textInactiveText;





    private String filePath;
    


    public ThemeSupport(File f) {
        String lines ="";
        try {
            Scanner s = new Scanner(f);
            filePath = f.getAbsolutePath().substring(0, f.getAbsolutePath().lastIndexOf(File.separator)+1);
            while(s.hasNextLine()) {
                lines = s.nextLine();
                String[] line = parseLine(lines);
                if(line == null)
                    return; // if there are errors in the file, just quit
                else if(line.length == 0)
                    continue; // empty line
                else {
                    setSetting(line);
                }

            }
        } catch (FileNotFoundException ex) {
            Messages.showErrorMessage("Not able to read file.\n" + f.getAbsolutePath());
        }
        catch (MalformedURLException ex) {
            Messages.showErrorMessage("Bad filepath:\n" + lines);
        }
        catch (NumberFormatException ex) {
            Messages.showErrorMessage("Problem interpreting number in line:\n" + lines);
        }
        catch (Exception ex) {
            Messages.showErrorMessage("Bad line:\n" + lines);
        }


    }

    private String[] parseLine(String line) {
        // Remove comments
        if(line.indexOf("//") != -1) {
            line = line.substring(0,line.indexOf("//"));
        }

        line = line.trim();
        
        // Check if the line is empty
        if(line.equals(""))
            return new String[0];

        // Parse
        String[] parts = line.split(":=");

        // Check if we have the correct number of substrings
        if(parts.length != 2) {
            Messages.showErrorMessage("Error parsing theme. Check this line:\n\n" + line);
            return null;
        }

        // remove spaces
        parts[0] = parts[0].trim().toLowerCase();
        parts[1] = parts[1].trim();

        return parts;

    }

    private void setSetting(String[] s) throws MalformedURLException,NumberFormatException {
        if      (s[0].equals("name"))
            name = s[1];
        else if (s[0].equals("author"))
            author = s[1];
        else if (s[0].equals("url"))
            url = s[1];
        else if (s[0].equals("header-background-image"))
            backgroundImage = new File(filePath + s[1]).toURI().toURL();
        else if (s[0].equals("header-background-repeat"))
            backgroundRepeat = Integer.parseInt(s[1]);
        else if (s[0].equals("window-background-color"))
            backgroundColor = decodeColor(s[1]);
        else if (s[0].equals("gamelist-background-color"))
            gameBackgroundColor = decodeColor(s[1]);
        else if (s[0].equals("gamelist-foreground-color"))
            gameForegroundColor = decodeColor(s[1]);
        else if (s[0].equals("gamelist-selected-background-color"))
            gameSelectedBackgroundColor = decodeColor(s[1]);
        else if (s[0].equals("gamelist-selected-foreground-color"))
            gameSelectedForegroundColor = decodeColor(s[1]);
        else if (s[0].equals("gamelist-dafaultgame-image"))
            defaultGame = new File(filePath + s[1]).toURI().toURL();
        else if (s[0].equals("search-background-color"))
            searchBackgroundColor = decodeColor(s[1]);
        else if (s[0].equals("search-foreground-color"))
            searchForegroundColor = decodeColor(s[1]);
        else if (s[0].equals("search-inactive-color"))
            searchInactiveColor = decodeColor(s[1]);
        else if (s[0].equals("header-play-active-image"))
            playActiveImage = new File(filePath + s[1]).toURI().toURL();
        else if (s[0].equals("header-play-inactive-image"))
            playInactiveImage = new File(filePath + s[1]).toURI().toURL();
        else if (s[0].equals("header-edit-active-image"))
            editActiveImage = new File(filePath + s[1]).toURI().toURL();
        else if (s[0].equals("header-edit-inactive-image"))
            editInactiveImage = new File(filePath + s[1]).toURI().toURL();
        else if (s[0].equals("header-tools-active-image"))
            toolsActiveImage = new File(filePath + s[1]).toURI().toURL();
        else if (s[0].equals("header-tools-inactive-image"))
            toolsInactiveImage = new File(filePath + s[1]).toURI().toURL();
        else if (s[0].equals("header-search-active-image"))
            searchActiveImage = new File(filePath + s[1]).toURI().toURL();
        else if (s[0].equals("header-search-inactive-image"))
            searchInactiveImage = new File(filePath + s[1]).toURI().toURL();
        else if (s[0].equals("show-borders"))
            showBorders = Boolean.parseBoolean(s[1]);
        else if (s[0].equals("show-window-border"))
            showWindowDecoration = Boolean.parseBoolean(s[1]);

    }

    private Color decodeColor(String s) {
        if(s.charAt(0) != '#')
            return Color.decode("#" + s);
        return Color.decode(s);
    }

    public String getAuthor() {
        return author;
    }

    public URL getBackgroundImage() {
        return backgroundImage;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getBackgroundRepeat() {
        return backgroundRepeat;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getGameBackgroundColor() {
        return gameBackgroundColor;
    }

    public Color getSearchBackgroundColor() {
        return searchBackgroundColor;
    }

    public Color getGameForegroundColor() {
        return gameForegroundColor;
    }

    public Color getSearchForegroundColor() {
        return searchForegroundColor;
    }

    public Color getSearchInactiveColor() {
        return searchInactiveColor;
    }

    public Color getGameSelectedBackgroundColor() {
        return gameSelectedBackgroundColor;
    }

    public Color getGameSelectedForegroundColor() {
        return gameSelectedForegroundColor;
    }

    public URL getPlayActiveImage() {
        return playActiveImage;
    }

    public URL getPlayInactiveImage() {
        return playInactiveImage;
    }

    public URL getEditActiveImage() {
        return editActiveImage;
    }

    public URL getEditInactiveImage() {
        return editInactiveImage;
    }

    public URL getSearchActiveImage() {
        return searchActiveImage;
    }

    public URL getSearchInactiveImage() {
        return searchInactiveImage;
    }

    public URL getToolsActiveImage() {
        return toolsActiveImage;
    }

    public URL getToolsInactiveImage() {
        return toolsInactiveImage;
    }

    public boolean isShowBorders() {
        return showBorders;
    }

    public boolean isShowWindowDecoration() {
        return showWindowDecoration;
    }

    public URL getDefaultGame() {
        return defaultGame;
    }

}