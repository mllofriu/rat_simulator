package nsl.modules;

import nslj.src.lang.NslDoutInt0;
import nslj.src.lang.NslModule;
import support.Utiles;
import experiment.ExperimentUniverse;

public class HeadingAngle extends NslModule{
	
	public NslDoutInt0 headingAngle;
	
	private ExperimentUniverse universe;

	public HeadingAngle(String nslName, NslModule nslParent, ExperimentUniverse universe){
		super(nslName, nslParent);
		
		this.universe = universe;
		
		this.headingAngle = new NslDoutInt0(this, "headingAngle");
	}
	
	public void simRun(){
		headingAngle.set(Utiles.discretizeAngle(universe.getRobotOrientationAngle()));
	}

}