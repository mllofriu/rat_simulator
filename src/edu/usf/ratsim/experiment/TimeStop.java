package edu.usf.ratsim.experiment;

public class TimeStop implements StopCondition {

	private int time;

	public TimeStop(int time) {
		this.time = time;
	}

	
	public boolean experimentFinished() {
		time--;
		if (time <= 0)
			System.out.println("Finished by time");
		
		return time <= 0;
	}

}
