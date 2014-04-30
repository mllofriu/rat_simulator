package edu.usf.ratsim.proofofconcepts;

import nslj.src.lang.NslDoutInt0;
import nslj.src.lang.NslInt0;
import nslj.src.lang.NslModule;

public class SharedNumberModule  extends NslModule {

	private NslDoutInt0 num0;
	
	public SharedNumberModule(String nslName, NslModule nslParent){
		super(nslName, nslParent);
		
		num0 = new NslDoutInt0("num0", this);
		num0.set(0);
	}
	
	public void simRun(){
		num0.set(num0.get() + 1);
		System.out.println(num0.get());
	}
}
