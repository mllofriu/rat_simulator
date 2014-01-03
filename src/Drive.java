/* Mdulo NSL que representa la necesidad de comer de la rata.
   Alejandra Barrera
   Versin: 1
   Fecha: 11 de febrero de 2005
 */

import nslj.src.system.*;
import nslj.src.cmd.*;
import nslj.src.lang.*;
import nslj.src.math.*;
import nslj.src.display.*;
import java.lang.*;

import support.Configuration;

public class Drive extends NslModule {
	public NslDinDouble0 distFood = new NslDinDouble0("distFood", this);
	public NslDoutDouble0 driveValue = new NslDoutDouble0("driveValue", this);
	public NslDoutDouble0 reward = new NslDoutDouble0("reward", this);
	private static final double MAX = Configuration.getDouble("Drive.Dmax");
	private final double OKEEFE_RESOURCE_REDUCTION = Configuration.getDouble("Drive.OKEEFE_RESOURCE_REDUCTION");
	private final double OKEEFE_RESOURCE_INCENTIVE = Configuration.getDouble("Drive.OKEEFE_RESOURCE_INCENTIVE");
	// valor usado para iniciar el hambre al comienzo de cada etapa de testing
	private static final double HANGRY_VALUE = Configuration.getDouble("Drive.HANGRY_VALUE");

	private static double value = Configuration.getDouble("Drive.INITIAL_HUNGRY")*MAX;

	private static double oldValue = value;
	private double ALPHA = 0.003;
	private static double rewardValue = 0;
	public static boolean ateFood;

	public Drive(String nslName, NslModule nslParent) {
		super(nslName, nslParent);
	}


	public static double getReward() {
		return rewardValue / MAX;
	}

	public void computeDrive() {
		double reduction = 0;
		double incentive = 0;
		double dFood = distFood.get();
		ateFood = false;
		oldValue = value;
		if (dFood >= 0 && dFood < 1) {
			reduction = OKEEFE_RESOURCE_REDUCTION;
			ateFood = true;
		}
		if (dFood != -1)
			incentive = OKEEFE_RESOURCE_INCENTIVE;
		if (reduction > 0)
			incentive = 0;
		double d1 = MAX - value;

		value = value + ALPHA * d1 - reduction * value + incentive * d1;
		//TODO: By gonzalo normalizo a 1. rewardValue = (oldValue / MAX) * OKEEFE_RESOURCE_REDUCTION;
		rewardValue = oldValue / MAX;
	}

	public void simRun() {
			computeDrive();

		driveValue.set(value);
		reward.set(rewardValue);
	} // simRun

	public static void setReallyHangry() {
		setValue(HANGRY_VALUE);
	}
	
	public static void setValue(double motivation) {
		value = motivation * MAX;
		oldValue = value;
	}
}
