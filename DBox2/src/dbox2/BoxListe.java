/*
 * BoxListe.java
 *
 * Created on 8. juni 2007, 01:18
 */

package dbox2;

import dbox2.DosItem;
import java.util.*;
import java.io.*;

/**
 * @author Truben
 */
public class BoxListe implements Serializable {
    
    private List<DosItem> gamelist;
    
    /** Creates a new instance of BoxListe */
    public BoxListe() {
        gamelist = new ArrayList<DosItem>();
    }
    
    /**
     * Searches through the list of games, and tries to find the game.
     * The name is case insensitive.
     *
     * @param name The name of the game
     * @return The name of the game, null if no game is found
     **/
    public DosItem getGame(String name) {
        name = name.toLowerCase();
        for(DosItem item : gamelist) {
            if(item.getName().toLowerCase().equals(name)) {
                return item;
            }
        }
        return null;
    }
    
    /**
     * Removes the game specified by the name
     * @param name The name of the game we want to remove
     * @return The game that we removed
     **/
    public DosItem removeGame(String name) {
        name = name.toLowerCase();
        for(DosItem item : gamelist) {
            if(item.getName().toLowerCase().equals(name)) {
                gamelist.remove(item);
                return item;
            }
        }
        return null;
    }
    
    /**
     * Adds the game to the list
     * @param di The game we want to add
     **/
    public void addGame(DosItem di) {
        gamelist.add(di);
    }
    
    /**
     * Get a list of game titles.
     * @return List of game titles in the db
     **/
    public String[] getGameList() {
        String[] temp = new String[gamelist.size()];
        int count=0;
        for(DosItem item : gamelist) {
            temp[count++] = item.getName();
        }
        Arrays.sort(temp);
        return temp;
    }
    
    /**
     * Get a list of game icons.
     * @return List of game titles in the db
     **/
    public String[] getIconList() {
        String[] temp = new String[gamelist.size()];
        int count=0;
        for(DosItem item : gamelist) {
            temp[count++] = item.getIcon();
        }
        return temp;
    }
    
    
    /**
     * Get a list of game titles. Separated by commas.
     * @param searchstr game name must include this
     * @return List of game titles in the db
     **/
    public String[] getGameList(String searchstr) {
        int count=0;
        int counter=0;
        searchstr=searchstr.toLowerCase();
        
        for(DosItem item : gamelist)
            if(item.getName().toLowerCase().indexOf(searchstr) != -1)
                count++;
        
        String[] temp = new String[count];
            
        for(DosItem item : gamelist) {
            if(item.getName().toLowerCase().indexOf(searchstr) != -1) {
                temp[counter++] = item.getName();
            }
        }
        Arrays.sort(temp);
        return temp;
    }
    
    /**
     * Find out how many games we have in our db
     * 
     * @return Number of games
     **/
    public int getNrGames() {
        return gamelist.size();
    }
    
    
    
}
