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
	public NslDinInt0 AngleToGo;
	public NslDinInt0 headAngleRat;
	public NslDoutInt0 newHeadAngleRat;
	public NslDoutInt0 ActionTaken;
	public NslDoutDouble0 currentDir;
	public NslDoutDouble0 nextDir;
	private double ad[];
	private int dirToGo;
	private int dirToGoDegrees;
	public static double x;
	public static double y;
	public static double z;
	private int j;
	private int ActionTakenAux;

	public ActionSelectionSchema(String nslName, NslModule nslParent) {
		super(nslName, nslParent);
		AngleToGo = new NslDinInt0("AngleToGo", this);
		headAngleRat = new NslDinInt0("headAngleRat", this);
		newHeadAngleRat = new NslDoutInt0("newHeadAngleRat", this);
		ActionTaken = new NslDoutInt0("ActionTaken", this);
		currentDir = new NslDoutDouble0("currentDir", this);
		nextDir = new NslDoutDouble0("nextDir", this);
		ad = new double[1];
	}

	public void computeDirectionToGo(int Angle, int j) {
		for (int i = 0; i < 1; i++)
			ad[i] = -1.0;
		if (Angle <= 15 && Angle >= 12)
			ad[0] = (j + 4) % 8;
		else if (Angle <= 11 && Angle >= 9)
			ad[0] = (j + 3) % 8;
		else if (Angle <= 8 && Angle >= 6)
			ad[0] = (j + 2) % 8;
		else if (Angle <= 5 && Angle >= 3)
			ad[0] = (j + 1) % 8;
		else if (Angle <= 2 && Angle >= -2)
			ad[0] = j;
		else if (Angle <= -3 && Angle >= -5)
			ad[0] = (j + 7) % 8;
		else if (Angle <= -6 && Angle >= -8)
			ad[0] = (j + 6) % 8;
		else if (Angle <= -9 && Angle >= -11)
			ad[0] = (j + 5) % 8;
		else if (Angle <= -12 && Angle >= -15)
			ad[0] = (j + 4) % 8;
	}

	public void computeActionTaken() {
		dirToGo = (int) ad[0];
		if (dirToGo != -1) {
			int i = j - dirToGo;
			ActionTakenAux = (i + 4) % 8;
			if (ActionTakenAux < 0)
				ActionTakenAux += 8;
		} else {
			ActionTakenAux = -1;
		}
	}
	final double STEP = 0.1;
	public static final double SPEED_ERROR = Configuration.getDouble("Robot.SPEED_ERROR"); 

	public void computeAdjustmentPosition() {
		double error = (2*SPEED_ERROR*Math.random()-SPEED_ERROR)/100;
		double step = STEP*(1+error);
		switch (dirToGo) {
		case 0:
			x = step;
			y = 0.0;
			z = 0.0;
			break;
		case 1:
			x = step;
			y = 0.0;
			z = -step;
			break;
		case 2:
			x = 0.0;
			y = 0.0;
			z = -step;
			break;
		case 3:
			x = -step;
			y = 0.0;
			z = -step;
			break;
		case 4:
			x = -step;
			y = 0.0;
			z = 0.0;
			break;
		case 5:
			x = -step;
			y = 0.0;
			z = step;
			break;
		case 6:
			x = 0.0;
			y = 0.0;
			z = step;
			break;
		case 7:
			x = step;
			y = 0.0;
			z = step;
			break;
		default:
			x = 0.0;
			y = 0.0;
			z = 0.0;
			break;
		}
	}

	public void simRun() {
		switch (headAngleRat.get()) {
		default:
			break;
		case 0:
			j = 0;
			break;
		case 45:
			j = 1;
			break;
		case 90:
			j = 2;
			break;
		case 135:
			j = 3;
			break;
		case 180:
			j = 4;
			break;
		case 225:
			j = 5;
			break;
		case 270:
			j = 6;
			break;
		case 315:
			j = 7;
			break;
		}

		if (Rat.nextHabituation) {
			Rat.newTrial = true;
			Rat.habituation = false;
			Rat.training = false;
			Rat.testing = false;
			Rat.nextHabituation = false;
			ad[0] = 2;
			computeActionTaken();
			x = 0.0;
			y = 0.0;
			z = 0.0;
		} else if (Rat.nextTraining) {
			ad[0] = 2;
				computeActionTaken();
				x = 0.0;
				y = 0.0;
				z = 0.0;
				Rat.newTrial = true;
				Rat.habituation = false;
				Rat.training = true;
				Rat.nextTraining = false;
			} else if (Rat.nextTesting) {
				World.testingTrialNumber++;
				switch (World.testingTrialNumber) {
				case 1:
				case 2:
					ad[0] = 2;
					break;
				case 3:
					ad[0] = 0;
					break;
				case 4:
					ad[0] = 6;
					break;
				}
				computeActionTaken();
				x = 0.0;
				y = 0.0;
				z = 0.0;
				Rat.newTrial = true;
				Rat.habituation = false;
				Rat.training = false;
				Rat.testing = true;
				Rat.nextTesting = false;
			} else {
				if (Rat.newTrial)
					Rat.newTrial = false;
				computeDirectionToGo(AngleToGo.get(), j);
				computeActionTaken();
				if (dirToGo == j)
					computeAdjustmentPosition();
				else {
					x = 0.0;
					y = 0.0;
					z = 0.0;
				}
			}
		
		switch (dirToGo) {
		default:
			break;
		case 0:
			dirToGoDegrees = 0;
			break;
		case 1:
			dirToGoDegrees = 45;
			break;
		case 2:
			dirToGoDegrees = 90;
			break;
		case 3:
			dirToGoDegrees = 135;
			break;
		case 4:
			dirToGoDegrees = 180;
			break;
		case 5:
			dirToGoDegrees = 225;
			break;
		case 6:
			dirToGoDegrees = 270;
			break;
		case 7:
			dirToGoDegrees = 315;
			break;
		}
		newHeadAngleRat.set(dirToGoDegrees);
		ActionTaken.set(ActionTakenAux);
		currentDir.set(j);
		nextDir.set(dirToGo);
	} // simRun
}
