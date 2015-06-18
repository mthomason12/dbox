# What's new in D-Box 2.0 #

  * **Drag & drop support!** Simply drag the main file into the gamelist, and D-Box will set up the rest (i.e. find setup files and icons)
  * **New slick user interface**
  * Add games from another game list to your library. Simply drop the .dat file on the game list
  * Import/Export/Clear game list menus
  * Added a small empty border around the objects in the game list
  * Folder icon instead of "Browse..." text in dialogs
  * .app for MacOS X
  * Windows and Mac builds includes DOSBox 0.72
  * Better size on Preferences dialog
  * Possible to mark a game as a favorite
  * New human readable gamelist file format. Note: D-Box 1.7 automatically exports to the new 2.0 file format, but uses the old one, it is therefore almost trivial to upgrade from 1.7 to 2.0 (CPU Cycles and frameskip settings will be discarded). If you upgrade from an older version (1.6, 1.5, 1.0), D-Box 2.0 won't recognize your old database. I therefore recommend every user of D-Box older than 1.7 to use 1.7 before they upgrade to 2.0
  * Filter games by genre (you can edit available genres in the config file) or show only favorite games
  * New meta information tab in "edit game" dialog: Add searchable keywords and genres
  * New switches:
    * -config configuration file - _uses the configuration file instead of the standard "dbox.config"_
    * -gamefile game-list file - _uses the game-list file instead of the standard "gamelist.dat"_
    * -version - _prints the current D-Box version_