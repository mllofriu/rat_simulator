import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;

import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.vecmath.*;
import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.swing.*;

public class WorldFrame extends java.awt.Frame {
	private final String DEFAULT_MAZE_DIR=Configuration.getString("WorldFrame.MAZE_DIRECTORY");
	private final String DEFAULT_MAZE_FILE=Configuration.getString("WorldFrame.MAZE_FILE");
	private final String CURRENT_MAZE_DIR= System.getProperty("user.dir")+File.separatorChar+DEFAULT_MAZE_DIR+File.separatorChar;

	robotWorldCanvas world1;
    TopWorldCanvas world2;
    //ThirdViewWorldCanvas world3;
    smallRobotWorldCanvas world3;
    WorldConstructor constr;
    
    public WorldFrame() {
        initComponents();
        world3 = new smallRobotWorldCanvas();
        world3.setSize(80*5,80);
        panel4.add(world3);
        world2 = new TopWorldCanvas(SimpleUniverse.getPreferredConfiguration());
        world2.setSize(240,240);
        panel3.add(world2);
        world1 = new robotWorldCanvas(SimpleUniverse.getPreferredConfiguration());
        world1.setSize(240,240);
        panel2.add(world1);
//        world3 = new ThirdViewWorldCanvas(SimpleUniverse.getPreferredConfiguration());
    	openFile(false);
//        startRobot(0, 0, 0.5);
    }
        
	public Point2d getGlobalCoodinate() {
		return world2.getGlobalPosition();
	}
	
	public Point2d getFood() {
		return world2.getFood();
	}
	
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panel1 = new java.awt.Panel();
        button1 = new java.awt.Button();
        button3 = new java.awt.Button();
        button2 = new java.awt.Button();
        button4 = new java.awt.Button();
        button5 = new java.awt.Button();
        button6 = new java.awt.Button();
        button9 = new java.awt.Button();
        buttonCW =new java.awt.Button();
        
        posRat = new java.awt.Label();
        
        buttonACW =new java.awt.Button();
        panel3 = new java.awt.Panel();
        panel2 = new java.awt.Panel();
        panel4 = new java.awt.Panel();
        

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

        button2.setLabel("<");
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        panel1.add(button2, gridBagConstraints);

        button4.setLabel(">");
        button4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button4ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        panel1.add(button4, gridBagConstraints);

        button5.setLabel("/\\");
            button5.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    button5ActionPerformed(evt);
                }
            });

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 3;
            gridBagConstraints.gridy = 1;
            panel1.add(button5, gridBagConstraints);

            button6.setLabel("\\/");
            button6.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    button6ActionPerformed(evt);
                }
            });

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 3;
            gridBagConstraints.gridy = 2;
            panel1.add(button6, gridBagConstraints);

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
            buttonACW.setLabel("<(");
            buttonACW.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    buttonACWActionPerformed(evt);
                }
            });

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 2;
            gridBagConstraints.gridy = 3;
            panel1.add(buttonACW, gridBagConstraints);

            // boton rotar anti-horario
            buttonCW.setLabel(")>");
            buttonCW.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    buttonCWActionPerformed(evt);
                }
            });

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 4;
            gridBagConstraints.gridy = 3;
            panel1.add(buttonCW, gridBagConstraints);
            
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
            panel3.setBackground(new java.awt.Color(255, 204, 0));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridwidth = 1;
            add(panel3, gridBagConstraints);

            panel2.setBackground(new java.awt.Color(153, 244, 51));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridwidth = 1;
            add(panel2, gridBagConstraints);

            panel4.setBackground(new java.awt.Color(204, 153, 0));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.gridwidth = 3;
            add(panel4, gridBagConstraints);

            java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            setBounds((screenSize.width-510)/2, (screenSize.height-510)/2, 510, 510);
        }
    
    // accion asociada al boton de girar horario
    private void buttonCWActionPerformed(java.awt.event.ActionEvent evt) {
        rotateRobot(45);
        mostrarColores();
    }

    // accion asociada al boton de girar anti-horario
    private void buttonACWActionPerformed(java.awt.event.ActionEvent evt) {
        rotateRobot(-45);
        mostrarColores();
    }

    // accion asociada al boton de mover izquierda
    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {
        world1.moveCamera(new Vector3f(-0.1f,0f,0f));
        world1.moveRobot(new Vector3f(-0.1f,0f,0f));
        world2.moveRobot(new Vector3f(-0.1f,0f,0f));
        mostrarColores();
    }

    // accion asociada al boton de mover derecha
    private void button4ActionPerformed(java.awt.event.ActionEvent evt) {
        world1.moveCamera(new Vector3f(0.1f,0f,0f));
        world1.moveRobot(new Vector3f(0.1f,0f,0f));
        world2.moveRobot(new Vector3f(0.1f,0f,0f));
        mostrarColores();
    }

    // accion asociada al boton de retroceder
    private void button6ActionPerformed(java.awt.event.ActionEvent evt) {
        world1.moveCamera(new Vector3f(0f,0f,0.1f));
        world1.moveRobot(new Vector3f(0f,0f,0.1f));
        world2.moveRobot(new Vector3f(0f,0f,0.1f));
        mostrarColores();
    }

    private void button9ActionPerformed(java.awt.event.ActionEvent evt) {
        world2.moveCamera(new Vector3f(0f, -1f, 0f));
    }

    // accion asociada al boton de avanzar
    private void button5ActionPerformed(java.awt.event.ActionEvent evt) {
        world1.moveCamera(new Vector3f(0f,0f,-0.1f));
        world1.moveRobot(new Vector3f(0f,0f,-0.1f));
        world2.moveRobot(new Vector3f(0f,0f,-0.1f));
        mostrarColores();
    }

    private void button3ActionPerformed(java.awt.event.ActionEvent evt) {
        world2.moveCamera(new Vector3f(0f, 1f, 0f));        
    }

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {
    	openFile(true);
    }
    
    private void openFile(boolean elegir) {
    	String fileName="";
    	
    	if (elegir) {
    		JFileChooser chooser = new JFileChooser();
    		chooser.setCurrentDirectory(new File(CURRENT_MAZE_DIR));
        
    		int returnVal = chooser.showOpenDialog(this);
    		if(returnVal == JFileChooser.APPROVE_OPTION)
    			fileName=chooser.getSelectedFile().getAbsoluteFile().toString();
    	} else 
    		fileName=CURRENT_MAZE_DIR+DEFAULT_MAZE_FILE;
    	
    	System.out.println("File: "+ fileName);
    	
    	if (fileName!="") { 
    		constr = new WorldConstructor(fileName);
            world1.setBranchGroup(constr.getFrontGroup());
            world2.setBranchGroup(constr.getTopGroup());
    	}
    }
    
    private void exitForm(java.awt.event.WindowEvent evt) {
        System.exit(0);
    }
    
    public static void main(String args[]) {
        new WorldFrame().show();
    }
    
    float angle2 = 0;
    public void rotateRobot(float angle) {
		angle2 = angle2 + angle;
    
        world2.moveRobotBody(-angle);
        world1.moveRobotBody(-angle);
        world1.moveCamera(-angle);
    }
    
    //Alejandra Barrera
    //Mover desde World slo la cmara del robot y tomar tres snapshots
    public void rotateRobotCamera(float angle)
    {
        world1.moveCamera(-angle);
        updatePosRat();
    }
    

	public void startRobot(Point4d point) {
		float x = (float) point.x;
		float y = (float) point.y;
		float z = (float) point.z;
		
		world1.startCamera(new Vector3f((float) x, (float) y, (float) z));
		world1.startRobot(new Vector3f((float) x, (float) y, (float) z));
		world2.startRobot(new Vector3f((float) x, (float) y, (float) z));
		//world3.startRobot(new Vector3f((float) x, (float) y, (float) z));
        updatePosRat();

	}   
	

	public void moveRobot(Point3d point) {
		float x = (float) point.x;
		float y = (float) point.y;
		float z = (float) point.z;
		world1.moveCamera(new Vector3f((float) x, (float) y, (float) z));
		world1.moveRobot(new Vector3f((float) x, (float) y, (float) z));
		world2.moveRobot(new Vector3f((float) x, (float) y, (float) z));
		//world3.moveRobot(new Vector3f((float) x, (float) y, (float) z));
		// rotateRobot(point.w);
        updatePosRat();
	}
	
////By Gonzalo
//	public boolean isCollided() {
//        return world1.isCollided();
//    }
    
    public int[][] getColorMatrix() {
    	world3.repaint(); 
    	//while(world3.isRendererRunning());
        return  world1.getColorMatrix();
    }
    
    private void updatePosRat() {
    	posRat.setText("Posicin de la rata (x,y,r): " + world2.getX()+ ", "+ world2.getX());
    }
    
    private java.awt.Button button1;
    private java.awt.Button button2;
    private java.awt.Button button3;
    private java.awt.Button button4;
    private java.awt.Button button5;
    private java.awt.Button button6;
    private java.awt.Button button9;
    private java.awt.Button buttonCW;
    private java.awt.Button buttonACW;
    
    
    private java.awt.Panel panel1;
    private java.awt.Panel panel2;
    private java.awt.Panel panel3;
    private java.awt.Panel panel4;
    
    private java.awt.Label posRat;
    
    private void mostrarColores() {
        Integer contador;
//        try {
//			Thread.sleep(1500);
//		} catch (Exception e) {
//			System.out.println(e);
//		}
        Hashtable <Color, Integer> contadores = Utiles.contadores(world1.getColorMatrix());
        contador = contadores.get(Color.RED); if (contador!=null) System.out.println("#Red: " + contador);
        contador = contadores.get(Color.CYAN);if (contador!=null) System.out.println("#Cyan: " + contador);
        contador = contadores.get(Color.MAGENTA);if (contador!=null) System.out.println("#Magenta: " + contador);
        contador = contadores.get(Color.WHITE);if (contador!=null) System.out.println("#White: " + contador);
        contador = contadores.get(Color.YELLOW);if (contador!=null) System.out.println("#Yellow: " + contador);
        contador = contadores.get(Color.BLUE);if (contador!=null) System.out.println("#Blue: " + contador);        
    }

	public int getGlobalAngle() {
		// TODO Auto-generated method stub
		return (int)angle2;
	}
}
