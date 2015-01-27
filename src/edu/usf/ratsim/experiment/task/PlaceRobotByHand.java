package edu.usf.ratsim.experiment.task;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.Map;

import javax.vecmath.Point4f;

import edu.usf.ratsim.experiment.ExperimentTask;
import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.experiment.subject.ExpSubject;
import edu.usf.ratsim.support.ElementWrapper;
import edu.usf.ratsim.support.Utiles;

public class PlaceRobotByHand implements ExperimentTask {

	private static final String STR_INIT_POS = "initialPosition";
	private Point4f initPos;

	public PlaceRobotByHand(Point4f initPos) {
		this.initPos = initPos;
	}

	public PlaceRobotByHand(ElementWrapper taskParams,
			Map<String, Point4f> points) {
		String initPosName = taskParams.getChildText(STR_INIT_POS);
		initPos = points.get(initPosName);
	}

	public void perform(ExperimentUniverse univ, ExpSubject subject) {
		Utiles.alarm();
		System.out.println("Place robot");
		boolean ready = false;
		while (!ready){
			try {
				System.out.println("Is the robot in place? [y/n]");
				ready = System.in.read() == 'y';
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Flush input just in case
		try {
			while(System.in.available() > 0)
				System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		univ.setRobotPosition(new Point2D.Float(initPos.x, initPos.y), initPos.w);
	}

}
