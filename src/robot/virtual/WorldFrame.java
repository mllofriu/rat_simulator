package robot.virtual;
import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.io.File;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.Locale;
import javax.media.j3d.VirtualUniverse;
import javax.swing.JFileChooser;
import javax.vecmath.Vector3f;

import support.Configuration;

import com.sun.j3d.utils.universe.SimpleUniverse;

public class WorldFrame extends java.awt.Frame {

	private static final long serialVersionUID = -698020368303861261L;
	private final String DEFAULT_MAZE_DIR=Configuration.getString("WorldFrame.MAZE_DIRECTORY");
	private final String CURRENT_MAZE_DIR= System.getProperty("user.dir")+File.separatorChar+
			DEFAULT_MAZE_DIR+File.separatorChar;

	Canvas3D topViewCanvas, robotViewCanvas;
	Canvas3D[] robotViewsCanvas;

	private VirtualExpUniverse expUniv;

	public WorldFrame(VirtualExpUniverse world) {
		this.expUniv = world;

		initComponents();

		// Create the canvases
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

		// Wide view canvases
		robotViewsCanvas = new Canvas3D[RobotNode.NUM_ROBOT_VIEWS];
		for (int i=0; i<RobotNode.NUM_ROBOT_VIEWS; i++){
			robotViewsCanvas[i] = new Canvas3D(config);
			robotViewsCanvas[i].setSize(80,80);
			world.getRobotViews()[i].addCanvas3D(robotViewsCanvas[i]);
			wideViewPanel.add(robotViewsCanvas[i]);
		}
		
		// Main robot view canvas
		robotViewCanvas = new Canvas3D(config);
		robotViewCanvas.setSize(240,240);
		world.getRobotViews()[RobotNode.NUM_ROBOT_VIEWS / 2].addCanvas3D(robotViewCanvas);
		robotViewPanel.add(robotViewCanvas);
		// Top view canvas
		topViewCanvas = new Canvas3D(config);
		world.getTopView().addCanvas3D(topViewCanvas);
		topViewCanvas.setSize(240,240);
		topViewPanel.add(topViewCanvas);
		
	}


	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		panel1 = new java.awt.Panel();
		button1 = new java.awt.Button();
		button3 = new java.awt.Button();
		leftBtn = new java.awt.Button();
		rightBtn = new java.awt.Button();
		forwardBtn = new java.awt.Button();
		backBtn = new java.awt.Button();
		button9 = new java.awt.Button();
		turnRightBtn =new java.awt.Button();

		posRat = new java.awt.Label();

		turnLeftBtn =new java.awt.Button();
		robotViewPanel = new java.awt.Panel();
		topViewPanel = new java.awt.Panel();
		wideViewPanel = new java.awt.Panel();


		setLayout(new java.awt.GridBagLayout());

		setResizable(false);
		setTitle("NSLWorld");
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				exitForm(evt);
			}
		});


		panel1.setLayout(new java.awt.GridBagLayout());

		button1.setLabel("Abrir");
		button1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				button1ActionPerformed(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 3;
		panel1.add(button1, gridBagConstraints);

		button3.setLabel("-");
		button3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				button3ActionPerformed(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 1;
		panel1.add(button3, gridBagConstraints);

		leftBtn.setLabel("<");
		leftBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				button2ActionPerformed(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 2;
		panel1.add(leftBtn, gridBagConstraints);

		rightBtn.setLabel(">");
		rightBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				button4ActionPerformed(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 2;
		panel1.add(rightBtn, gridBagConstraints);

		forwardBtn.setLabel("/\\");
		forwardBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				forwardBtnAction(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 1;
		panel1.add(forwardBtn, gridBagConstraints);

		backBtn.setLabel("\\/");
		backBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				backBtnAction(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 2;
		panel1.add(backBtn, gridBagConstraints);

		button9.setLabel("+");
		button9.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				button9ActionPerformed(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		panel1.add(button9, gridBagConstraints);

		// boton rotar horario
		turnLeftBtn.setLabel("<(");
		turnLeftBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				turnLeftBtnAction(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 3;
		panel1.add(turnLeftBtn, gridBagConstraints);

		// boton rotar anti-horario
		turnRightBtn.setLabel(")>");
		turnRightBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				turnRightBtnAction(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 3;
		panel1.add(turnRightBtn, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 4;
		panel1.add(posRat, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		add(panel1, gridBagConstraints);

		// panel1 es el de los botones
		// panel2 -> world1, panel3 -> world2, panel4 -> world3
		robotViewPanel.setBackground(Color.blue);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 1;
		add(robotViewPanel, gridBagConstraints);

		topViewPanel.setBackground(new java.awt.Color(153, 244, 51));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 1;
		add(topViewPanel, gridBagConstraints);

		wideViewPanel.setBackground(new java.awt.Color(204, 153, 0));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 3;
		add(wideViewPanel, gridBagConstraints);

		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((screenSize.width-510)/2, (screenSize.height-510)/2, 510, 510);
	}

	// accion asociada al boton de girar horario
	private void turnRightBtnAction(java.awt.event.ActionEvent evt) {
		rotateRobot(45);
		mostrarColores();
	}

	// accion asociada al boton de girar anti-horario
	private void turnLeftBtnAction(java.awt.event.ActionEvent evt) {
		rotateRobot(-45);
		mostrarColores();
	}

	// accion asociada al boton de mover izquierda
	private void button2ActionPerformed(java.awt.event.ActionEvent evt) {
		expUniv.moveRobot(new Vector3f(-0.1f,0f,0f));
		mostrarColores();
	}

	// accion asociada al boton de mover derecha
	private void button4ActionPerformed(java.awt.event.ActionEvent evt) {
		expUniv.moveRobot(new Vector3f(0.1f,0f,0f));
		mostrarColores();
	}

	// accion asociada al boton de retroceder
	private void backBtnAction(java.awt.event.ActionEvent evt) {
		expUniv.moveRobot(new Vector3f(0f,0f,.1f));
		mostrarColores();
	}

	private void button9ActionPerformed(java.awt.event.ActionEvent evt) {
	}

	// accion asociada al boton de avanzar
	private void forwardBtnAction(java.awt.event.ActionEvent evt) {
		expUniv.moveRobot(new Vector3f(0f,0f,-.1f));
		mostrarColores();
	}

	private void button3ActionPerformed(java.awt.event.ActionEvent evt) {
		//        w2Canvas.moveCamera(new Vector3f(0f, 1f, 0f));        
	}

	private void button1ActionPerformed(java.awt.event.ActionEvent evt) {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(CURRENT_MAZE_DIR));

		int returnVal = chooser.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			String fileName=chooser.getSelectedFile().getAbsoluteFile().toString();
			//TODO: createworld with filename
		}
	}

	private void exitForm(java.awt.event.WindowEvent evt) {
		System.exit(0);
	}

	//    public static void main(String args[]) {
	//        new WorldFrame().show();
	//    }

	public void rotateRobot(float angle) {
		expUniv.rotateRobot(Math.toRadians(-angle));
		System.out.println("Robot rotate");
	}

	//Alejandra Barrera
	//Mover desde World slo la cmara del robot y tomar tres snapshots
	//    public void rotateRobotCamera(float angle)
	//    {
	//        w1Canvas.moveCamera(-angle);
	//        updatePosRat();
	//    }


	//	public void startRobot(Point4d point) {
	//		float x = (float) point.x;
	//		float y = (float) point.y;
	//		float z = (float) point.z;
	//		
	//		w1Canvas.startCamera(new Vector3f((float) x, (float) y, (float) z));
	//		w1Canvas.startRobot(new Vector3f((float) x, (float) y, (float) z));
	//		w2Canvas.startRobot(new Vector3f((float) x, (float) y, (float) z));
	//		//world3.startRobot(new Vector3f((float) x, (float) y, (float) z));
	//        updatePosRat();
	//
	//	}   


	//	public void moveRobot(Point3d point) {
	//		float x = (float) point.x;
	//		float y = (float) point.y;
	//		float z = (float) point.z;
	//		w1Canvas.moveCamera(new Vector3f((float) x, (float) y, (float) z));
	//		w1Canvas.moveRobot(new Vector3f((float) x, (float) y, (float) z));
	//		w2Canvas.moveRobot(new Vector3f((float) x, (float) y, (float) z));
	//		//world3.moveRobot(new Vector3f((float) x, (float) y, (float) z));
	//		// rotateRobot(point.w);
	//        updatePosRat();
	//	}

	//	public void moveRobotForward() {
	//		// Move the robot one step along the current direction
	//		double x = STEP * Math.sin(getGlobalAngle() * Math.PI / 180);
	//		double z = -STEP * Math.cos(getGlobalAngle() * Math.PI / 180);
	//		moveRobot(new Point3d(x,0,z));
	//	}

	////By Gonzalo
	//	public boolean isCollided() {
	//        return world1.isCollided();
	//    }

	//    public BufferedImage getColorMatrix() {
	//    	w3Canvas.repaint(); 
	//    	//while(world3.isRendererRunning());
	//        return  w1Canvas.getColorMatrix();
	//    }

	private void updatePosRat() {
		posRat.setText("Posicin de la rata (x,y,r): " + robotViewCanvas.getX()+ ", "+ robotViewCanvas.getX());
	}

	private java.awt.Button button1;
	private java.awt.Button leftBtn;
	private java.awt.Button button3;
	private java.awt.Button rightBtn;
	private java.awt.Button forwardBtn;
	private java.awt.Button backBtn;
	private java.awt.Button button9;
	private java.awt.Button turnRightBtn;
	private java.awt.Button turnLeftBtn;


	private java.awt.Panel panel1;
	private java.awt.Panel topViewPanel;
	private java.awt.Panel robotViewPanel;
	private java.awt.Panel wideViewPanel;

	private java.awt.Label posRat;

	private void mostrarColores() {
		//        Integer contador;
		////        try {
		////			Thread.sleep(1500);
		////		} catch (Exception e) {
		////			System.out.println(e);
		////		}
		//        Hashtable <Color, Integer> contadores = Utiles.contadores(w1Canvas.getColorMatrix());
		//        contador = contadores.get(Color.RED); if (contador!=null) System.out.println("#Red: " + contador);
		//        contador = contadores.get(Color.CYAN);if (contador!=null) System.out.println("#Cyan: " + contador);
		//        contador = contadores.get(Color.MAGENTA);if (contador!=null) System.out.println("#Magenta: " + contador);
		//        contador = contadores.get(Color.WHITE);if (contador!=null) System.out.println("#White: " + contador);
		//        contador = contadores.get(Color.YELLOW);if (contador!=null) System.out.println("#Yellow: " + contador);
		//        contador = contadores.get(Color.ORANGE);if (contador!=null) System.out.println("#Orange: " + contador);
		//        contador = contadores.get(Color.BLUE);if (contador!=null) System.out.println("#Blue: " + contador);
		//        System.out.println("RobotFrame::coordenadas: "+ RobotFactory.getRobot().getGlobalCoodinate().x +", " +RobotFactory.getRobot().getGlobalCoodinate().y);
	}

	public static void main(String args[]){
		VirtualExpUniverse expUniv = new VirtualExpUniverse();
		WorldFrame worldFrame = new WorldFrame(expUniv);

		worldFrame.setVisible(true);
	}

}
