package nslj.src.display;

import java.io.*;

public class NslDotNslsFilter implements FilenameFilter {
    String filter;
    public  NslDotNslsFilter (String filter) {
	this.filter=filter;
	System.out.println("Creating filter "+filter);
    }
    public String getFilter() {	
	return filter;
    }
    public boolean accept(File dir, String name) {
	System.out.println("Testing "+name);
        return name.endsWith(filter);
	//return 1;
    }

}


