package edu.usf.ratsim.nsl.modules.qlearning;

import nslj.src.lang.NslDoutFloat0;
import nslj.src.lang.NslModule;
import edu.usf.ratsim.experiment.ExperimentUniverse;

public class Reward extends NslModule {

	private NslDoutFloat0 reward;
	private ExperimentUniverse universe;
	private float nonFoodReward;
	private float foodReward;
	
	public Reward(String nslName, NslModule nslParent, ExperimentUniverse universe, float foodReward, float nonFoodReward){
		super(nslName, nslParent);
		
		reward = new NslDoutFloat0(this, "reward");
		
		this.universe = universe;
		this.foodReward = foodReward;
		this.nonFoodReward = nonFoodReward;
	}
	
	public void simRun(){
		if(universe.hasRobotAte()){
//			System.out.println("Ate Food");
			reward.set(foodReward);
		} else 
			reward.set(nonFoodReward);
	}
}
