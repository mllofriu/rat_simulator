package experiment;

public class TimeStop implements StopCondition {

	private int time;

	public TimeStop(int time){
		this.time = time;
	}
	
	@Override
	public boolean experimentFinished() {
		return time-- <= 0;
	}

}
