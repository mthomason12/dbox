# Introduction #

D-Box is a Java program, which means it runs on many different platforms. We currently ship D-Box in Java's own JAR format for use on all platforms, and a version that works only on Windows. This document focuses on using the JAR version.

The Windows version is exactly the same as the jar version, but it is packed as a familiar setup file which creates shortcut icons in the start menu and on the desktop (optional).


# How to install #

What you need:
  * A computer (duh...)
  * Java 5.0 or better (sometimes called Java 1.5)
  * Native DosBox binaries (found here: http://i1.no/06wt)
  * Some kind of unzipper (built in in most operating systems)

What to do:
  * Download the newest D-Box
  * Unzip the archive into a nice folder (i.e. ~\dbox)
  * Double-click on dbox.jar
  * Follow the instructions on the screen


# Troubleshooting #
  * To check if you have the right java version, type "java -version" in your command line
    1. Good enough Java (1.5, 1.6, 5.0, 6.0)? Move on...
    1. Old Java (1.3,1.4)? Go to java.com and download

  * Windows tell me that dbox.jar is an archive, and want to extract it!
    1. Jar is actually a zipped file, but it is structured in a special way that makes it able to "run" like a native application. If your operating system tries to extract the file, try one of the following methods:
    1. Right click on the file and select "open as.." and then "Jar Launcher" (or something similar)
    1. Open your favorite terminal, navigate to the dbox directory and type java -jar dbox.jar