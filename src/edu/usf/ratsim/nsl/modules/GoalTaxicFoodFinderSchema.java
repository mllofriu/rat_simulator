package edu.usf.ratsim.nsl.modules;

import java.util.List;
import java.util.Random;

import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;

import nslj.src.lang.NslDinInt0;
import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.robot.Landmark;
import edu.usf.ratsim.support.Configuration;
import edu.usf.ratsim.support.Debug;
import edu.usf.ratsim.support.Utiles;

public class GoalTaxicFoodFinderSchema extends NslModule {

	private static final float CLOSE_TO_FOOD_THRS = Configuration
			.getFloat("VirtualUniverse.closeToFood");
	
	private IRobot robot;
	private ExperimentUniverse univ;
	public NslDinInt0 goalFeeder;
	public NslDoutFloat1 votes;
	private float maxReward;
	private Random r;

	private int repCount;

	private double alpha;

	public GoalTaxicFoodFinderSchema(String nslName, NslModule nslParent,
			IRobot robot, ExperimentUniverse univ, int numActions,
			float maxReward, float explorationHalfLifeVal) {
		super(nslName, nslParent);
		this.robot = robot;
		this.univ = univ;
		this.maxReward = maxReward;
		this.alpha = - Math.log(.5) / explorationHalfLifeVal;
		
		goalFeeder = new NslDinInt0(this, "goalFeeder");
		votes = new NslDoutFloat1(this, "votes", numActions);
		
		r = new Random();
		
		repCount = 0;
	}

	public void simRun() {
		// If the current goal is flashing override other modules actions
		// (this module should come after others)
		votes.set(0);
		
		Landmark lm = getLM(goalFeeder.get(), robot.getLandmarks());
		if (lm != null) {
			if (Debug.printTaxicBh)
				System.out.println("Found Food - executing taxic");
			if (lm.location.distance(new Point3f(0,0,0))<CLOSE_TO_FOOD_THRS){
				votes.set(Utiles.eatAction, maxReward);
//				System.out.println("Setting votes to eat");
			} else {
				// Build quat4d for angle to food
				// Use (1,0,0) to get absolute orientation
				Quat4f rotToFood = Utiles.angleToPoint(lm.location);

				// Get affordances
				// boolean[] affordances;

				// Get best action to food
				int action = Utiles.bestActionToRot(rotToFood);
				
//				System.out.println(action);

				votes.set(action, maxReward);
			}
		} else {
			// Give a forward impulse
			double explorationValue =  maxReward * Math.exp(-repCount * alpha);
//			System.out.println(explorationValue);
			if (r.nextFloat() > .8)
				votes.set(Utiles.discretizeAction(0),explorationValue);
			else 
				if (r.nextFloat() > .5)
					votes.set(Utiles.discretizeAction(90), explorationValue);
				else
					votes.set(Utiles.discretizeAction(-90), explorationValue);
		}
	}

	private Landmark getLM(int id, List<Landmark> lms) {
		for (Landmark lm : lms)
			if (lm.id == id)
				return lm;
		
		return null;
	}

	public void newRep() {
		repCount++;
//		System.out.println("Increasing rep" + repCount);
	}

	public void newTrial() {
		repCount = 0;
	}


}
