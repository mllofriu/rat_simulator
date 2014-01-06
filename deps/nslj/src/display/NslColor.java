/* SCCS  @(#)NslColor.java	1.7 --- 09/01/99 -- 00:15:42 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

package nslj.src.display;

import java.awt.*;
import java.lang.*;

public class NslColor extends Color {

    static final private String colorNames[] = {
	"BLUE",
	"RED",
	"GRAY",
	"DARKGREY",
	"YELLOW",
	"GREEN",
	"BLACK",
	"CYAN",
	"MAGENTA",
	"ORANGE",
	"PINK",
	"WHITE",
    };

    static final private Color colorTypes[] = {
    	blue, 
    	red,
    	gray,
    	darkGray,
    	yellow,
    	green,
    	black,
    	cyan,
    	magenta,
    	orange,
    	pink,
    	white,
    };

    public NslColor(int r, int g, int b) {
    	super(r, g, b);
    }
    public NslColor(int rgb) {
    	super(rgb);
    }
    
    public NslColor(String name) {
    	super(getColor(name).getRGB());
    }

    public static Color getColor (String name) {
	
	Color result;
    
    	name = name.toUpperCase();
    
        for (int i=0; i<colorNames.length; i++) {
	    if (name.equals(colorNames[i])) {
	    	return colorTypes[i];
	    }
	}

	return null;  

    }
    
    public static boolean isValidColor(String name) {
    
        name = name.toUpperCase();
        
        for (int i=0; i<colorNames.length; i++) {
	    if (name.equals(colorNames[i])) {
	    	return true;
	    }
	}
    	
    	return false;
    }

    public static Color getColorByIndex(int index) {
        return colorTypes[index % colorTypes.length];
    }
}
