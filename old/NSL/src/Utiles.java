import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

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

	// retorna una direccion relativa 
	public static int absolute2relative(int currentAbs, int abs) {
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
	
	public static Hashtable contadores(int [][]imagen) {
		int iterH, iterW;
		Integer contador;
		Color color;
		Hashtable<Color, Integer> contadores = new Hashtable<Color, Integer>();

		for (iterH=0; iterH<imagen.length;iterH++)
			for (iterW=0; iterW<imagen[iterH].length;iterW++) {
				color=rgb2Color(imagen[iterH][iterW]);
				
				contador = ((Integer)contadores.get(color));
				if (contador==null) { 
					contador=0;
				} else
					contador++;
				contadores.put(color, contador);
			}
		return contadores;
	}
	
	public static int contador(int [][]imagen, Color color) {
		int iterH, iterW;
		int contador=0;

		for (iterH=0; iterH<imagen.length;iterH++)
			for (iterW=0; iterW<imagen[iterH].length;iterW++) {
				if (rgb2Color(imagen[iterH][iterW]).equals(color))
					contador++;
			}
		return contador;
	}

	/* para una imágen panoramica de 80*3X80 devuelve un numero [0..79]
	 * de la mayor aparición de pixeles en el histograma del color
	 */
	public static int anguloColor(int [][]imagen, Color color) {
		final int THRES = 10;
		int iterH, iterW, h, pos, val, count, distance, colorHUE;
		pos=-1; val=0;
				
		for (iterW=0; iterW<imagen.length;iterW++) { // columnas
			count = 0;
			for (iterH=0; iterH<imagen[0].length;iterH++) { // filas
				h=imagen[iterW][iterH];
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
		return (int)Math.round(80.0*(double)pos/(double)imagen.length);
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

	static int color2RGB(Color color) {
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

	public static void speak(String text) {
		Runtime runtime = Runtime.getRuntime();
    	if (System.getProperty("os.name").equals("Linux"))
			try {
				Process process = runtime.exec("espeak \'" + text + "\'");
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(process.getInputStream()));
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					System.out.println(line);
				}
				process.waitFor();

			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
