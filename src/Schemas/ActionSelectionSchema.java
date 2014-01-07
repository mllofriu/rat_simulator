package Schemas;
/* M�dulo de selecci�n de acci�n.
   Alejandra Barrera
   Versi�n: 1 (Febrero, 2005)
 */

import nslj.src.system.*;
import nslj.src.cmd.*;
import nslj.src.lang.*;
import nslj.src.math.*;
import nslj.src.display.*;

import java.util.*;

import support.Configuration;

public class ActionSelectionSchema extends NslModule {
	public NslDoutInt0 actionTaken;

	public ActionSelectionSchema(String nslName, NslModule nslParent) {
		super(nslName, nslParent);
		actionTaken = new NslDoutInt0("ActionTaken", this);
	}

	public void simRun() {
		// Driver - Always go forward 
		actionTaken.set(4);
	} 
}
