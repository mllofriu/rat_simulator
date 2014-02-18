package edu.usf.ratsim.nsl.modules.qlearning;

import edu.usf.ratsim.support.Utiles;

public final class StateAction {
	private int state;
	private int action;

	public int getState() {
		return state;
	}

	public int getAction() {
		return action;
	}

	public StateAction(int state, int action) {
		this.state = state;
		this.action = action;
	}

	public boolean equals(Object o) {
		if (o == null || !(o instanceof StateAction))
			return false;

		StateAction stateAction = (StateAction) o;

		return stateAction.state == state && stateAction.action == action;
	}

	public int hashCode() {
		return state * Utiles.discreteAngles.length + action;
	}

}