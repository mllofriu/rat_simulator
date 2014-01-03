package support;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Random;

import java.awt.geom.Point2D.Double;

import robot.RobotFactory;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class Utiles {

	public static int NO_HUE = -1;
	private static final int ANGLE_HEAD_TURN = Configuration.getInt("Robot.ANGLE_HEAD_TURN");
	
	// retorna una direccion absoluta 
	public static int relativa2absolute(int currentAbs, int rel) {
		int k = currentAbs - rel;
		int j = (k - 4) % 8;
		if (j < 0)
			j += 8;
		return j;
	}

	// a partir de una orientacion absoluta actual y una orientacion absoluta deseada
	// retorna una direccion relativa a tomar para alcanzarla
	public static int absolute2relative(int currentAbs, int abs) {
//		int result;
//		if (Math.abs(currentAbs-abs)<=4) 
//			result=currentAbs - abs; 
//		else 
//			result=currentAbs - abs + 4 - 8;
//		return result + 4; // 4 es la posicion del cero en direcciones relativas
				int k = currentAbs - abs;
		int j = (k + 4) % 8;
		if (j < 0)
			j += 8;
		return j;
	}

	// Convertir la accion a grados
	public static int acccion2GradosRelative(int accion) {
		int actionDegrees;

		switch (accion) {
		case 0:
			actionDegrees = -180;
			break;
		case 1:
			actionDegrees = -135;
			break;
		case 2:
			actionDegrees = -90;
			break;
		case 3:
			actionDegrees = -45;
			break;
		case 4:
			actionDegrees = 0;
			break;
		case 5:
			actionDegrees = 45;
			break;
		case 6:
			actionDegrees = 90;
			break;
		case 7:
			actionDegrees = 135;
			break;
		default:
			actionDegrees = 0;
			break;
		}
		return actionDegrees;
	}
	
	// Convertir la accion a grados
	public static int gradosRelative2Acccion(int grados) {
		int actionDegrees;

		switch (grados) {
		case -180:
			actionDegrees = 0;
			break;
		case -135:
			actionDegrees = 1;
			break;
		case -90:
			actionDegrees = 2;
			break;
		case -45:
			actionDegrees = 3;
			break;
		case 0:
			actionDegrees = 4;
			break;
		case 45:
			actionDegrees = 5;
			break;
		case 90:
			actionDegrees = 6;
			break;
		case 135:
			actionDegrees = 7;
			break;
		case 180:
			actionDegrees = 0;
			break;
	default:
			actionDegrees = 0;
			break;
		}
		return actionDegrees;
	}
	
	// Convertir laos grados absolutos a accion
	public static int gradosAbsolute2Acccion(int grados) {
		int actionDegrees;

		switch (grados) {
		case 0:
			actionDegrees = 0;
			break;
		case 45:
			actionDegrees = 1;
			break;
		case 90:
			actionDegrees = 2;
			break;
		case 135:
			actionDegrees = 3;
			break;
		case 180:
			actionDegrees = 4;
			break;
		case 225:
			actionDegrees = 5;
			break;
		case 270:
			actionDegrees = 6;
			break;
		case 315:
			actionDegrees = 7;
			break;
		case 360:
			actionDegrees = 0;
			break;
	default:
			actionDegrees = -1;
			break;
		}
		return actionDegrees;
	}

	// Convertir una accion a grados absolutos
	public static int acccion2GradosAbsolute(int i) {
		switch (i) {
		case 0:
			return 360;
		case 1:
			return 45;
		case 2:
			return 90;
		case 3:
			return 135;
		case 4:
			return 180;
		case 5:
			return 225;
		case 6:
			return 270;
		case 7:
			return 315;
		}
		return -1;
	}
	
	public static Hashtable heights(BufferedImage imagen) {
		int iterH, iterW;
		Integer height, contador;
		Color color;
		Hashtable<Color, Integer> contadores, contadoresMax = new Hashtable<Color, Integer>();

		for (iterW=0; iterW<imagen.getWidth();iterW++) {
			contadores = new Hashtable<Color, Integer>();
			for (iterH=0; iterH<imagen.getHeight();iterH++) {
				color=rgb2Color(imagen.getRGB(iterW,iterH));
				
				contador = ((Integer)contadores.get(color));
				if (contador==null) { 
					contador=0;
				} else
					contador++;
				contadores.put(color, contador);
			}
			
			merge2max(contadoresMax, contadores);
		}
			
		return contadoresMax;
	}
	
	/**
	 * @param contadoresMax
	 * @param contadores
	 */
	private static void merge2max(Hashtable<Color, Integer> a,Hashtable<Color, Integer> b) {
		Color[] colors = RobotFactory.getRobot().getColorsLandmarks();
		Integer aH, bH;
		
        for (int iterLand=0; iterLand<colors.length;iterLand++){
        	aH = a.get(colors[iterLand]);
        	bH = b.get(colors[iterLand]);
        	if ((aH!=null)&&(bH!=null)) {
        		if (bH>aH) a.put(colors[iterLand], bH);
        	} else if (bH!=null)
        		a.put(colors[iterLand], bH);
        }
	}

	public static Hashtable contadores(BufferedImage imagen) {
		int iterH, iterW;
		Integer contador;
		Color color;
		Hashtable<Color, Integer> contadores = new Hashtable<Color, Integer>();

		for (iterH=0; iterH<imagen.getHeight();iterH++)
			for (iterW=0; iterW<imagen.getWidth();iterW++) {
				color=rgb2Color(imagen.getRGB(iterW,iterH));
				
				contador = ((Integer)contadores.get(color));
				if (contador==null) { 
					contador=0;
				} else
					contador++;
				contadores.put(color, contador);
			}
		return contadores;
	}
	
	public static int contador(BufferedImage imagen, Color color) {
		int iterH, iterW;
		int contador=0;

		for (iterH=0; iterH<imagen.getHeight();iterH++)
			for (iterW=0; iterW<imagen.getWidth();iterW++) {
				if (rgb2Color(imagen.getRGB(iterH,iterW)).equals(color))
					contador++;
			}
		return contador;
	}

	/* para una imágen panoramica de 80*3X80 devuelve un numero [0..79]
	 * de la mayor aparición de pixeles en el histograma del color
	 */
	public static double anguloColor(BufferedImage imagen, Color color) {
		final int THRES = 10;
		int iterH, iterW, h, pos, val, count, distance, colorHUE;
		pos=-1; val=0;
				
		for (iterW=0; iterW<imagen.getWidth();iterW++) { // columnas
			count = 0;
			for (iterH=0; iterH<imagen.getHeight();iterH++) { // filas
				h=imagen.getRGB(iterW,iterH);
				distance = distancia(rgb2Color(h),color);
				if (distance<THRES) {
					count++;
					//imagen[iterH][iterW]=color2RGB(color);
				}
			}
			if (count>val) {
				val=count;
				pos=iterW;
			}
			//System.out.print("("+iterW+")"+count+".");
		}
		
		// devuelve angulos return (int)(3.0*(double)ANGLE_HEAD_TURN * (double)pos/(double)imagen.length - 3.0*(double)ANGLE_HEAD_TURN/2.0);
		return (double)pos/(double)imagen.getWidth();
	}
	
	public static int anguloColorHUE(int [][]imagen, Color color) {
		final int THRES = 10;
		int iterH, iterW, h, pos, val, count, distance, colorHUE;
		pos=-1; val=0;
		colorHUE=RGB2HUE(color2RGB(color));
		
		for (iterW=0; iterW<imagen[0].length;iterW++) { // columnas
			count = 0;
			for (iterH=0; iterH<imagen.length;iterH++) { // filas
				h=RGB2HUE(imagen[iterH][iterW]);
				if (h==NO_HUE) {
					distance = Math.abs(h-colorHUE);
					if (distance>126) distance =253-distance;
					if (distance<THRES) count++;
				}
			}
			if (count>val) {
				val=count;
				pos=iterW;
			}
		}
		return (int)(3.0*(double)ANGLE_HEAD_TURN * (double)pos/(double)imagen[0].length - 3.0*(double)ANGLE_HEAD_TURN/2.0);
	}

	static Color rgb2Color(int rgb) {
		int red = (rgb & 0x00ff0000) >> 16;
		int green = (rgb & 0x0000ff00) >> 8;
		int blue = rgb & 0x000000ff;
		return new Color(red, green, blue);	
	}

	public static int color2RGB(Color color) {
		return (color.getRed() << 16)+(color.getGreen() << 8)+color.getBlue();
	}
	
	static String toString(Color color) {
		String result;
		if (color.equals(Color.BLACK)) result="BLACK";
		else if (color.equals(Color.WHITE)) result="WHITE";
		else if (color.equals(Color.RED)) result="RED";
		else if (color.equals(Color.BLUE)) result="BLUE";
		else if (color.equals(Color.GREEN)) result="GREEN";
		else if (color.equals(Color.CYAN)) result="CYAN";
		else if (color.equals(Color.MAGENTA)) result="MAGENTA";
		else if (color.equals(Color.YELLOW)) result="YELLOW";
		else result=color.toString();
		
		return result;
	}

	static int RGB2HUE(int rgb) {
		int hue, delta, max, min;
		int r = (rgb & 0x00ff0000) >> 16;
		int g = (rgb & 0x0000ff00) >> 8;
		int b = rgb & 0x000000ff;

		max=Math.max(r, Math.max(g,b));
		min=Math.min(r, Math.min(g,b));
		delta=max-min;
		hue =0;
		
		if (2*delta<=max) hue=NO_HUE;
		else {
			if (r==max) hue =42+42*(g-b)/delta;
			else if (g==max) hue = 126 + 42 * (b-r)/delta;
			else if (b==max) hue = 210 + 42 * (r-g)/delta;
		}
		return hue;
	}

	static int distancia(Color a, Color b)
	{
	  long rmean = (a.getRed() + b.getRed()) / 2;
	  long red = a.getRed() - b.getRed();
	  long green = a.getGreen() - b.getGreen();
	  long blue = a.getBlue()-b.getBlue();
	  return (int)Math.sqrt((((512+rmean)*red*red)>>8) + 4*green*green + (((767-rmean)*blue*blue)>>8));
	}
	
	private static final double DELTA_STEP = Configuration.getDouble("Simulation.DeltaStep")/100;///Math.sqrt(2);
	public static final double SPEED_ERROR = Configuration.getDouble("Robot.SPEED_ERROR"); 
	public static final double ANGLE_ERROR = Configuration.getDouble("Robot.ANGLE_ERROR"); 

	public static double speed(Point2D.Double start, Point2D.Double end) {
		double deltaX = end.x-start.x;
		double deltaY = end.y-start.y;
		double result = Math.sqrt(Math.pow(deltaX/DELTA_STEP,2.0)+Math.pow(deltaY/DELTA_STEP,2.0));
//		double error = (2*SPEED_ERROR*Math.random()-SPEED_ERROR)/100;
//		if (error!=0)
//			System.err.println("UTILES::speed error: "+ error);
//		return result*(1+error);
		return result;
	}
	
	public static double headDirection(Point2D.Double start, Point2D.Double end) {
		double deltaX = end.x-start.x;
		double deltaY = end.y-start.y;
		double result = Math.atan2(deltaY/DELTA_STEP, deltaX/DELTA_STEP);
//		double error = (2*ANGLE_ERROR*Math.random()-ANGLE_ERROR)/100;
//		if (error!=0)
//			System.err.println("UTILES::speed error: "+ error);

//		return result*(1+error);
		return result;
	}

	public static String getCurrentDirectoryAbsolute() {
		return System.getProperty("user.dir");
	}

	public static void speak(String text) {
		Runtime runtime = Runtime.getRuntime();
//    	if (System.getProperty("os.name").equals("Linux"))
//			try {
//				Process process = runtime.exec("espeak \'" + text + "\'");
//				BufferedReader bufferedReader = new BufferedReader(
//						new InputStreamReader(process.getInputStream()));
//				String line;
//				while ((line = bufferedReader.readLine()) != null) {
//					System.out.println(line);
//				}
//				process.waitFor();
//
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
	}
	
	static private int enOrigen=0; // cantidad de veces que paso por el origen
	static private int entreIyII=0; // cantidad de veces que paso entre el cuadrante I y II
	static private int entreIIyIII=0; // cantidad de veces que paso entre el ...
	static private int entreIIIyIV=0; // cantidad de veces que paso entre el ...
	static private int entreIVyI=0; // cantidad de veces que paso entre el ...
	
	// dado un punto devuelve el cuadrante al que pertenece entre 0 y 3
	// detalle de implementación: ocurre muchas veces que se cumple el == a 0 por lo que balaceo entre los cuadrantes correspondientes
	static DecimalFormat df = new DecimalFormat("#.####");
	public static int getCuadrante(Double coord) {
		int result;
		// redondeo a cuatro dígitos decimales
		coord.x = java.lang.Double.parseDouble(df.format(coord.x));
		coord.y = java.lang.Double.parseDouble(df.format(coord.y));
		if ((coord.x>0)&&(coord.y>0))
			result = 0;
		else if ((coord.x<0)&&(coord.y>0))
			result = 1;
		else if ((coord.x<0)&&(coord.y<0))
			result = 2;
		else if ((coord.x>0)&&(coord.y<0))
			result = 3;
		else if ((coord.x==0)&&(coord.y==0)) { // paso por el centro de coordenadas
			enOrigen=(enOrigen+1)%4;
			result = enOrigen;
		} else if ((coord.x==0)&&(coord.y>0)) {
			entreIyII=(entreIyII+1)%2;
			result = entreIyII;
		} else if ((coord.x==0)&&(coord.y<0)) {
			entreIIIyIV=(entreIIIyIV+1)%2;
			result = entreIIIyIV+2;
		} else if ((coord.x>0)&&(coord.y==0)) {
			entreIVyI=(entreIVyI+1)%2;
			result = (entreIVyI+3)%4; // devuelve alternadamente los cuadrantes I y IV
		} else if ((coord.x<0)&&(coord.y==0)) {
			entreIIyIII=(entreIIyIII+1)%2;
			result = entreIIyIII+1; // devuelve alternadamente los cuadrantes II y III
		} else
			result = -1;
		//System.err.println("Utiles::cuadrante: "+result);
		return result;
	}
	
	public static void shuffleList(Object[] array) {
		Collections.shuffle(Arrays.asList(array));
	}
	
	public static void shuffleListAMano(Object[] array) {
		int n = array.length;
		Random random = new Random();
		random.nextInt();
		for (int i = 0; i < n; i++) {
			int change = i + random.nextInt(n - i);
		    swap(array, i, change);
		}
	 }
		
	 private static void swap(Object[] a, int i, int change) {
		 Object helper = a[i];
		 a[i] = a[change];
		 a[change] = a[i];
	 }
	  
}
