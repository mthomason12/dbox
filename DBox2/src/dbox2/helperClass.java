/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dbox2;

/**
 *
 * @author pederskeidsvoll
 */
public class helperClass {

    public static boolean isMac() {
        if("Mac OS X".equals(System.getProperty("os.name")))
            return true;
        return false;
    }

}
