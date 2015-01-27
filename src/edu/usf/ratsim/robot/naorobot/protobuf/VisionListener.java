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
//(414, 1270)
//
//South east
//(2740, 1309)	
//
//South west
//(2719, -1014)
//
//North west
//(397, -1020)

public class VisionListener {

	private final float nex = 414;
	private final float ney = 1270;
	private final float sex = 2740;
	private final float sey = 1390;
	private final float nwx = 396;
	private final float nwy = -1020;
	private final float swx = 2719;
	private final float swy = -1020;
	private final float fl = 2;
	
	private InetAddress group;
	private MulticastSocket s;
	private Position lastPosition;
	private long lastPosTime;
	

	public VisionListener() {
		try {
			group = InetAddress.getByName("224.5.23.2");
			s = new MulticastSocket(10002);
			s.joinGroup(group);
			s.setReceiveBufferSize(4096);
			System.out.println(s.getReceiveBufferSize());
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
		if (Math.abs(System.currentTimeMillis() - lastPosTime) < 200)
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
//		System.out.println(r.getX());
//		System.out.println(r.getY());
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
		return scale(new Point3f(p.getX(), p.getY(), 0));
	}
	
	private Point3f scale(Point3f p) {
		float x = (-fl/(nwx - swx)) * p.x + (1 + (fl/(nwx - swx)) * swx);
		float y = (fl/(ney - nwy)) * p.y + (-1 + (-fl/(ney - nwy)) * nwy);
		x = Math.min (fl/2, x); x = Math.max(-fl/2, x);
		y = Math.min(fl/2, y); y = Math.max(-fl/2, y);
		return new Point3f(x, y, 0);
	}

	public float getRobotOrientation(){
		Position p = getRobotPosition();
		return -p.getOrient();
	}
	
	public static void main(String[] args){
		VisionListener v = new VisionListener();
		while (true){
			System.out.println(v.getRobotPoint());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		System.out.println(new VisionListener().getRobotOrientation());
	}
}
