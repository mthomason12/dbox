# Introduction #

D-Box 2.2 supports "theming". Theming is a method to alter the look of the application. Themes in D-Box consists of two parts: A theme file that defines the theme, and images that the theme uses.


# Theme properties #

The theme file contains a set of properties. All properties is defined in this manner:

```
property := value
```

For example:

```
name := Truben.no
```

The available properties are:

**name**

Defines the name of the theme

Example:
```
name := A cool theme
```
**author**

Credits the author of the theme.

Example:
```
author := Peder
```
**url**

The url to the theme's homepage. note that you can't use //

Example:
```
url := truben.no
```
**header-background-image**

Sets the background image for the toolbar.

Example:
```
header-background-image := img/background.png
```
**header-background-repeat**

Defines how many times the background image should be repeated. If you don't want a background image, set this property to 0.

Example:
```
header-background-repeat := 100
```
**window-background-color**

Defines the background color of the window. The color is defined using the same notation as you would use in HTML and CSS. [More info](http://en.wikipedia.org/wiki/Hex_triplet#Hex_triplet)

Example:
```
window-background-color := #FF0000
```
This would produce a red window.

**gamelist-background-color**

Defines the background color of the gamelist. The color is defined using the same notation as you would use in HTML and CSS. [More info](http://en.wikipedia.org/wiki/Hex_triplet#Hex_triplet)

Example:
```
gamelist-background-color := #FF0000
```
This would produce a red gamelist.

**gamelist-foreground-color**

Defines the text color in the gamelist. The color is defined using the same notation as you would use in HTML and CSS. [More info](http://en.wikipedia.org/wiki/Hex_triplet#Hex_triplet)

Example:
```
gamelist-foreground-color := #FF0000
```
This would produce a red window.

**gamelist-selected-background-color**

Defines the background color of a selected item in the gamelist. The color is defined using the same notation as you would use in HTML and CSS. [More info](http://en.wikipedia.org/wiki/Hex_triplet#Hex_triplet)

Example:
```
gamelist-selected-background-color := #FF0000
```
This would make the backgroundcolor to the selected item red.

**gamelist-selected-foreground-color**

Defines the text color of a selected item in the gamelist. The color is defined using the same notation as you would use in HTML and CSS. [More info](http://en.wikipedia.org/wiki/Hex_triplet#Hex_triplet)

Example:
```
gamelist-selected-foreground-color := #FF0000
```
This would make the text color to the selected item red.

**gamelist-dafaultgame-image**

The relative path to an image that represent a game if no other image is added. Supported formats are GIF, PNG and JPG.

Example:
```
gamelist-dafaultgame-image := img/gameicon.png
```

In this example the image is in the "img" folder.

**gamelist-favorite-image**

The relative path to an image that is present if the game is marked as a favorite. Supported formats are GIF, PNG and JPG. The default image in D-Box is a star.

Example:
```
gamelist-favorite-image := favorite.png
```

In this example the image is in the same folder as the definition file.

**gamelist-notfavorite-image**

The relative path to an image that is present if the game is NOT marked as a favorite. Supported formats are GIF, PNG and JPG. The default image in D-Box is a a transparante image (nothing).

Example:
```
gamelist-notfavorite-image := img/notfavorite.png
```

In this example the image is in the "img" folder.

**search-background-color**

**search-foreground-color**

**search-inactive-color**

**header-play-active-image**

**header-play-inactive-image**

**header-edit-active-image**

**header-edit-inactive-image**

**header-tools-active-image**

**header-tools-inactive-image**

**header-search-active-image**

**header-search-inactive-image**

**show-borders**

Show borders around the search-box or not. Default is true.

Example:

```
show-borders := false
```

**show-window-border**

Defines if D-Box should draw the standard border around the main window. This setting is experimental only, and should not be used. The default is true.

Example:

```
show-window-border := false
```

**show-unified-toolbar**

Defines if the theme should use the unified toolbar on MacOS X. Possible values are _true_ and _false_. Default is _true_.

Example:

```
show-unified-toolbar := false
```