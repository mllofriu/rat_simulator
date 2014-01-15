package experiment;

public class TimeStop implements StopCondition {

	private int time;

	public TimeStop(int time){
		this.time = time;
	}
	
	@Override
	public boolean experimentFinished() {
		if (time-- <= 0)
			System.out.println("Finished by time");
		return time-- <= 0;
	}

}
