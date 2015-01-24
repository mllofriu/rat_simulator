package edu.usf.ratsim.robot.romina;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import javax.vecmath.Point3f;

import edu.usf.ratsim.experiment.ExperimentUniverse;
import edu.usf.ratsim.robot.IRobot;
import edu.usf.ratsim.robot.Landmark;
import edu.usf.ratsim.robot.romina.protobuf.Connector.Command;
import edu.usf.ratsim.robot.romina.protobuf.Connector.Command.Builder;
import edu.usf.ratsim.robot.romina.protobuf.Connector.Command.CommandType;
import edu.usf.ratsim.robot.romina.protobuf.Connector.Response;
import edu.usf.ratsim.robot.virtual.UniverseFrame;
import edu.usf.ratsim.robot.virtual.VirtualExpUniverse;
import edu.usf.ratsim.support.Configuration;
import edu.usf.ratsim.support.Debug;

public class Romina implements IRobot {
	
	private static final float CLOSE_TO_FOOD_THRS = Configuration
			.getFloat("VirtualUniverse.closeToFood");

	private Socket protoSocket;
	private boolean validResponse;
	private Response r;
	private ExperimentUniverse world;

	public Romina(String host, int port, ExperimentUniverse world) {
		if (Configuration.getBoolean("UniverseFrame.display")) {
			UniverseFrame worldFrame = new UniverseFrame(
					(VirtualExpUniverse) world);
			worldFrame.setVisible(true);
		}

		((SLAMUniverse) world).setRominaRobot(this);
		this.world = world;
		
		try {
			protoSocket = new Socket(host, port);
			System.out.println("Connection stablished");

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Builder b = Command.newBuilder();
			b.setType(CommandType.startRobot);
			Command c = b.build();
			c.writeTo(protoSocket.getOutputStream());

			Response.parseDelimitedFrom(protoSocket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		validResponse = false;
	}

	@Override
	public void rotate(float degrees) {
		try {
			Builder b = Command.newBuilder();
			b.setType(CommandType.doAction);
			b.setAngle(degrees);
			Command c = b.build();
			c.writeTo(protoSocket.getOutputStream());

			Response.parseDelimitedFrom(protoSocket.getInputStream());

			validResponse = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void eat() {
		world.robotEat();
		if (Debug.printTryingToEat)
			System.out.println("Romina ate");
	}

	@Override
	public boolean[] getAffordances() {
		try {
			if (!validResponse) {
				Builder b = Command.newBuilder();
				b.setType(CommandType.getInfo);
				Command c = b.build();
				c.writeTo(protoSocket.getOutputStream());

				r = Response.parseDelimitedFrom(protoSocket.getInputStream());
				validResponse = true;
			}

			boolean res[] = new boolean[r.getAffs().getAffCount()];
			for (int i = 0; i < r.getAffs().getAffCount(); i++)
				res[i] = r.getAffs().getAff(i);

			return res;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean hasFoundFood() {
		Point3f robot = getRobotPoint();
		for (Landmark lm : getLandmarks()) {
			if (world.isFeederActive(lm.id)
					&& lm.location.distance(new Point3f()) < CLOSE_TO_FOOD_THRS)
				return true;
		}

		return false;
	}

	@Override
	public void startRobot() {
		try {
			Builder b = Command.newBuilder();
			b.setType(CommandType.startRobot);
			Command c = b.build();
			c.writeTo(protoSocket.getOutputStream());

			Response.parseDelimitedFrom(protoSocket.getInputStream());

			validResponse = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public BufferedImage[] getPanoramica() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void forward() {
		rotate(0);
		validResponse = false;
	}

	@Override
	public boolean[] getAffordances(int wallLookahead) {
		return getAffordances();
	}

	@Override
	public List<Landmark> getLandmarks() {
		try {
			if (!validResponse) {
				Builder b = Command.newBuilder();
				b.setType(CommandType.getInfo);
				Command c = b.build();
				c.writeTo(protoSocket.getOutputStream());

				r = Response.parseDelimitedFrom(protoSocket.getInputStream());
				validResponse = true;
			}

			List<Landmark> lms = new LinkedList<Landmark>();
			for (edu.usf.ratsim.robot.romina.protobuf.Connector.Landmark lm : r.getLandmarksList())
				lms.add(new Landmark(lm));

			return lms;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean hasTriedToEat() {
		return false;
	}

	public Point3f getRobotPoint() {
		try {
			if (!validResponse){
				Builder b = Command.newBuilder();
				b.setType(CommandType.getInfo);
				Command c = b.build();
				c.writeTo(protoSocket.getOutputStream());
				
	
				r = Response.parseDelimitedFrom(protoSocket
						.getInputStream());
				validResponse = true;
			}
			
			return new Point3f(r.getRobotPos().getX(), r.getRobotPos().getY(), 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public float getRobotOrientation() {
		try {
			if (!validResponse){
				Builder b = Command.newBuilder();
				b.setType(CommandType.getInfo);
				Command c = b.build();
				c.writeTo(protoSocket.getOutputStream());
				
	
				r = Response.parseDelimitedFrom(protoSocket
						.getInputStream());
				validResponse = true;
			}
			
			return r.getRobotPos().getTheta();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}

	public boolean isCloseToAFeeder() {
		Point3f robot = getRobotPoint();
		for (Landmark lm : getLandmarks()) {
			if (lm.location.distance(new Point3f()) < CLOSE_TO_FOOD_THRS)
				return true;
		}

		return false;
	}

	public void invalidateResponse() {
		validResponse = false;
	}

}
