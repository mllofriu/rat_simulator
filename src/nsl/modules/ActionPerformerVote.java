package nsl.modules;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import nslj.src.lang.NslDinInt0;
import nslj.src.lang.NslModule;
import robot.IRobot;
import support.Utiles;

public class ActionPerformerVote extends NslModule {

	public NslDinInt0[] votes;
	
	private IRobot robot;

	private Random r;

	public ActionPerformerVote(String nslName, NslModule nslParent, int numLayers, IRobot robot){
		super(nslName, nslParent);
		
		this.robot = robot;
		
		votes = new NslDinInt0[numLayers];
		for(int i =0 ; i < numLayers; i++)
			votes[i] = new NslDinInt0(this, "votes" + i);
			
		r = new Random();
	}
	
	public void simRun(){
		boolean[] aff;
		
		// Count the votes
		AbstractMap<Integer,Votes> voteBox = new HashMap<Integer,Votes>();
		for (int i = 0; i < votes.length; i++){
			int vote = votes[i].get();
			// -1 represents no vote
			if (vote != -1){
				if(voteBox.containsKey(vote))
					voteBox.get(vote).incrementVotes();
				else
					voteBox.put(vote, new Votes(vote, 1));
			}
		}
		
		List<Votes> voteList = new LinkedList<Votes>(voteBox.values());
		// If there are no votes, directly to explorer algorithm
		if (!voteList.isEmpty()){
			// Sort them according to number of votes
			Collections.sort(voteList);
			
			do {
				int action = voteList.get(voteList.size()-1).getAction();
				robot.rotate(Utiles.actions[action]);
				aff = robot.affordances();
				// If cannot go forward that direction
				if (!aff[Utiles.discretizeAction(0)]){
					// Undo rotation
					robot.rotate(-Utiles.actions[action]);
					// Take out this element and keep trying
					voteList.remove(voteList.size() - 1);
				}
			} while (!aff[Utiles.discretizeAction(0)] && !voteList.isEmpty());
		}
		// If there were no votes, or none is executable
		if (voteList.isEmpty()){
			// If there is no actual actiona that can be performed, execute explorer algorithm
			do {
				int action = (int) Math.round(r.nextGaussian() * .5 + Utiles.discretizeAction(0));
				// Trim
				action = action < 0 ? 0 : action;
				action = action >= Utiles.actions.length ? Utiles.actions.length - 1 : action;
				// Rotate the robot to the desired action
				robot.rotate(Utiles.actions[action]);
				// Re-calculate affordances
				aff = robot.affordances();
			} while (!aff[Utiles.discretizeAction(0)]);
		}
		
		// Now it is safe to forward
		robot.forward();
	}
}

class Votes implements Comparable<Votes> {
	private int action;
	private int votes;
	
	public Votes(int action, int votes){
		this.action = action;
		this.votes = votes;
	}

	public int getAction() {
		return action;
	}

	public int getVotes() {
		return votes;
	}
	
	public void incrementVotes(){
		votes++;
	}

	@Override
	public int compareTo(Votes o) {
		if (votes < o.votes)
			return -1;
		else if (votes == o.votes)
			return 0;
		else 
			return 1;
	}
	
}
