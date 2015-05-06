package edu.usf.ratsim.nsl.modules;

import java.util.Random;

import nslj.src.lang.NslDinInt0;
import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;
import edu.usf.experiment.subject.Subject;
import edu.usf.experiment.subject.affordance.Affordance;
import edu.usf.experiment.subject.affordance.EatAffordance;
import edu.usf.experiment.subject.affordance.ForwardAffordance;

/**
 * Module to generate random actions when the agent hasnt moved (just rotated)
 * for a while
 * 
 * @author biorob
 * 
 */
public class StillExplorer extends NslModule {

	private static final int TIME_EXPLORING = 5;
	private NslDinInt0 takenAction;
	private int maxActionsSinceForward;
	private Subject sub;
	private int actionsSinceForward;
	private Random r;
	private NslDoutFloat1 votes;
	private float stillExploringVal;
	private int timeToExplore;

	public StillExplorer(String nslName, NslModule nslParent,
			int maxActionsSinceForward, Subject sub, float stillExploringVal) {
		super(nslName, nslParent);

		takenAction = new NslDinInt0(this, "takenAction");

		votes = new NslDoutFloat1(this, "votes", sub.getPossibleAffordances()
				.size() + 1);

		this.maxActionsSinceForward = maxActionsSinceForward;
		this.sub = sub;
		this.stillExploringVal = stillExploringVal;

		actionsSinceForward = 0;
		r = new Random();
		timeToExplore = 0;
	}

	public void simRun() {
		votes.set(0);

		if (takenAction.get() != -1){
			Affordance taken = sub.getPossibleAffordances().get(takenAction.get());
	
			if (taken instanceof ForwardAffordance)
				actionsSinceForward = 0;
			else
				actionsSinceForward++;
		} else 
			actionsSinceForward++;

		// When the agent hasnt moved for a while, add exploring value to random
		// action
		if (timeToExplore > 0 || actionsSinceForward > maxActionsSinceForward) {
			if (timeToExplore == 0)
				timeToExplore = TIME_EXPLORING;
			else
				timeToExplore--;
			int selectedAction;
			
			do {
				selectedAction = r.nextInt(sub.getPossibleAffordances().size());
			} while ((sub.getPossibleAffordances().get(selectedAction) instanceof EatAffordance));
			votes.set(selectedAction, stillExploringVal);
		}
	}
}
