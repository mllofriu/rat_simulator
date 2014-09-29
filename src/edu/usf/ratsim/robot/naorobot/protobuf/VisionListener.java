package edu.usf.ratsim.robot.naorobot.protobuf;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import javax.vecmath.Point3f;

import edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionFrame;
import edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslDetection.SSL_DetectionRobot;
import edu.usf.ratsim.robot.naorobot.protobuf.MessagesRobocupSslWrapper.SSL_WrapperPacket;
import edu.usf.ratsim.support.Position;

//North east
//(222.5344, 0.0, 1373.7507)
//
//South east
//(2542.5972, 0.0, 1360.0709)	
//
//South west
//(2603.2822, 0.0, -1166.3612)
//
//North west
//(201.37334, 0.0, -1063.1487)

public class VisionListener {

	private InetAddress group;
	private MulticastSocket s;
	private Position lastPosition;
	private long lastPosTime;

	public VisionListener() {
		try {
			group = InetAddress.getByName("224.5.23.2");
			s = new MulticastSocket(10002);
			s.joinGroup(group);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		lastPosTime = 0;
	}

	private Position getRobotPosition() {
		if (Math.abs(System.currentTimeMillis() - lastPosTime) < 500)
			return lastPosition;
		
		boolean finish = false;
		SSL_WrapperPacket f = null;
		while (!finish) {
			try {
				byte[] buf = new byte[4096];
				DatagramPacket recv = new DatagramPacket(buf, buf.length);
				s.receive(recv);
				byte[] data = new byte[recv.getLength()];
				System.arraycopy(buf, 0, data, 0, recv.getLength());
				f = SSL_WrapperPacket.parseFrom(data);
				finish = f.getDetection().getRobotsBlueList().size() > 0;
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		SSL_DetectionRobot r = f.getDetection().getRobotsBlue(0);
		lastPosition = new Position(r.getX(), r.getY(), r.getOrientation());
		lastPosTime = System.currentTimeMillis();
		return lastPosition;
	}
	
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		s.leaveGroup(group);
		s.close();
	}

	public Point3f getRobotPoint(){
		Position p = getRobotPosition();
		return scale(new Point3f(p.getX(), 0, p.getY()));
	}
	
	private Point3f scale(Point3f p) {
		float x = p.x / 2400 - 1/12;
		float y = p.z * 6f / (13f*1200) + 6f/13;
		x = Math.min (1, x); x = Math.max(0, x);
		y = Math.min(1, y); y = Math.max(0, y);
		return new Point3f(x, 0, y);
	}

	public float getRobotOrientation(){
		Position p = getRobotPosition();
		return -p.getOrient();
	}
	
	public static void main(String[] args){
		System.out.println(new VisionListener().getRobotPoint());
		System.out.println(new VisionListener().getRobotOrientation());
	}
}
