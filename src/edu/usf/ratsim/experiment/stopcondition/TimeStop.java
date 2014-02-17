package edu.usf.ratsim.experiment.stopcondition;

import org.w3c.dom.Element;

public class TimeStop implements StopCondition {

	private static final String STR_STOP_TIME = "time";
	private int time;

	public TimeStop(int time) {
		this.time = time;
	}

	public TimeStop(Element condParams) {
		this.time = Integer.parseInt(condParams
				.getElementsByTagName(STR_STOP_TIME).item(0).getTextContent());
	}

	public boolean experimentFinished() {
		time--;
		if (time <= 0)
			System.out.println("Finished by time");

		return time <= 0;
	}

}
