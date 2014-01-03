/**
 * 
 */
package robot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import javax.vecmath.Point2d;

import support.Utiles;

import com.googlecode.javacv.cpp.opencv_core.CvRect;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

/**
 * @author gtejera
 *
 */
public class SpatialCognitionInterfaceOpenCV implements IRobot {
	private final int ACTIONS = 9;
	
	private static final int NUMBER_IMAGES_PAN = 3;

	private static final int GRADOS_HFOV = 60;
	private static final int AFF_N135 = 1;
	private static final int AFF_N90 = 2;
	private static final int AFF_N45 = 3;
	private static final int AFF_0 = 4;
	private static final int AFF_P45 = 5;
	private static final int AFF_P90 = 6;
	private static final int AFF_P135 = 7;

	private static final int ANGLE_N180 = -180;
	private static final int ANGLE_N135 = -135;
	private static final int ANGLE_N90 = -90;
	private static final int ANGLE_N45 = -45;
	private static final int ANGLE_0 = 0;
	private static final int ANGLE_P45 = 45;
	private static final int ANGLE_P90 = 90;
	private static final int ANGLE_P135 = 135;
	private static final int ANGLE_P180 = 180;

	// angulos en grados -180, 180 para cada posición que affordance se considera
	private final int ANGLE_AFF[] = {ANGLE_N180,ANGLE_N135,ANGLE_N90,ANGLE_N45,ANGLE_0,ANGLE_P45,ANGLE_P90,ANGLE_P135,ANGLE_P180};
	// affordances que son posibles extrar de la vision
	private final int VISION_AFF[] = {AFF_N90,AFF_N45,AFF_0,AFF_P45,AFF_P90};

	private static final int TH_PORCENTAGE = 10;
	private static final int TH_ANGLE = 30;

	private static final double MIN_WALL_HEIGHT = 20;

	private static final double MAX_WALL_SD = 10;

	private static final long THRESHOLD_DISTANCE = 250;
	private static final long SLEEP_VIDEO_UPDATE = 0; // espera para que se frene el robot y las imagenes no salgan corridas

	private static final double MIN_RECT_AREA = 40;
//	private static final double MAX_RECT_AREA = 11000; //
	private static final double MIN_RECT_HEIGHT = 10;
	private static final double MAX_RECT_HEIGHT = 120; //
	private static final double MAX_REL_WIDTH_HEIGHT = 1.2; //

	private static final double MM_FORWARD = 180;
	
	MostrarImagenWeb imagen = new MostrarImagenWeb();
	MostrarImagen panorama = new MostrarImagen();
	DohyoTracker dohyoTracker = new DohyoTracker();

	// imagenes que conforman a la panorámica
	BufferedImage [] images = new BufferedImage [NUMBER_IMAGES_PAN];
	// imagenes almacenadas sobre la vison actual en todas direciones para evitar procesamiento y movimientos del robot innecesarios
	BufferedImage [] images360 = new BufferedImage [360/GRADOS_HFOV];
	int cursosImagen360 = 0;
	
	GiroAvanzo robot = new GiroAvanzo();
	private final Color[] landmarkColors = {Color.GREEN, Color.YELLOW, Color.CYAN, Color.MAGENTA};
	
	public Hashtable <Color, HSVRange> mapColorHSVRange = new Hashtable<Color, HSVRange>();
    final static Range hRed = new Range(0, 18),sRed = new Range(40, 256),vRed = new Range(80, 256); final static HSVRange redHSVRange = new HSVRange(hRed, sRed, vRed);
    final static Range hYellow = new Range(20, 40),sYellow = new Range(190, 256),vYellow = new Range(200, 256); final static HSVRange yellowHSVRange = new HSVRange(hYellow, sYellow, vYellow);
    final static Range hBlue = new Range(85, 120),sBlue = new Range(100, 256),vBlue = new Range(50, 256); final static HSVRange blueHSVRange = new HSVRange(hBlue, sBlue, vBlue);
    final static Range hMagenta = new Range(140, 170),sMagenta = new Range(70, 256),vMagenta = new Range(70, 256); final static HSVRange magentaHSVRange = new HSVRange(hMagenta, sMagenta, vMagenta);

    final static Range hCyan = new Range(70, 110),sCyan = new Range(20, 256),vCyan = new Range(140, 256); final static HSVRange cyanHSVRange = new HSVRange(hCyan, sCyan, vCyan);
    final static Range hGreen = new Range(50, 70),sGreen = new Range(170, 256),vGreen = new Range(100, 256); final static HSVRange greenHSVRange = new HSVRange(hGreen, sGreen, vGreen);
    final static Range hOrange = new Range(6, 23),sOrange = new Range(170, 256),vOrange = new Range(170, 256); final static HSVRange orangeHSVRange = new HSVRange(hOrange, sOrange, vOrange);

    public final static HSVRange [] calibratedColorsHSV = {redHSVRange, orangeHSVRange, yellowHSVRange, greenHSVRange, cyanHSVRange, blueHSVRange, magentaHSVRange};
    public final static Color [] calibratedColorsRGB = {Color.red, Color.orange, Color.yellow, Color.green, Color.cyan, Color.blue, Color.magenta};

	public SpatialCognitionInterfaceOpenCV() {
		robot.connect();
        // hue, value, saturation; hue determina el color
        Range saturation = new Range(70, 230);  // valores chicos poco saturado (blancos, grises depende de el valor V)
        Range value = new Range(70, 230);		// valores chicos poca luz (negro)
        //Range hueOrange = new Range(10, 20);
        //Range hYellow = new Range(22, 35),sYellow = new Range(50, 240),vYellow = new Range(70, 230);
// Calibracion sin KTGrab
//        Range hGreen = new Range(40, 75),sGreen = new Range(60, 200),vGreen = new Range(25, 171);
//        Range hCyan = new Range(70, 100),sCyan = new Range(70, 200),vCyan = new Range(80, 230);
//        Range hBlue = new Range(100, 130),sBlue = new Range(116, 256),vBlue = new Range(120, 215);
//        Range hMagenta = new Range(170, 175),sMagenta = new Range(50, 220),vMagenta = new Range(160, 230); 
// Calibracion con KTGrab
       
        
        mapColorHSVRange.put(Color.GREEN, greenHSVRange);
        mapColorHSVRange.put(Color.YELLOW, yellowHSVRange);
        mapColorHSVRange.put(Color.ORANGE, orangeHSVRange);
        mapColorHSVRange.put(Color.MAGENTA, magentaHSVRange);
        mapColorHSVRange.put(Color.BLUE, blueHSVRange);
        mapColorHSVRange.put(Color.CYAN, cyanHSVRange);
        mapColorHSVRange.put(Color.RED, redHSVRange);
        
        for (int iterLand=0; iterLand<landmarkColors.length;iterLand++)
        	detector.addColor(landmarkColors[iterLand], mapColorHSVRange.get(landmarkColors[iterLand]));
	}
	
	//Color colorClass = new Color(220,100,123); // MAGENTA 
	//Color colorClass = new Color(230,230,230); //Color.white;
//	public static Color colorWhite = new Color(210,210,210); //new Color(200,200,170); //Color.white;
//	public static Color colorMagenta = new Color(175,75,110); //new Color(200,200,170); //Color.white;
//	public static Color colorYellow = new Color(180,160,15);//new Color(190,190,20); //new Color(200,200,170); //Color.white;
//	public static Color colorBlue = new Color(38,119,169);//new Color(50,90,140); //new Color(200,200,170); //Color.white;
//	public static Color colorGreen = new Color(60,96,53); //new Color(200,200,170); //Color.white;
//	public static Color colorCyan = new Color(70,135,130);//new Color(80,170,80); //new Color(200,200,170); //Color.white;
//	private Color [] landmarksColors = {colorMagenta,colorCyan,colorBlue,colorGreen};
	
	//public static Color colorBlack = new Color(80,80,70); //new Color(200,200,170); //Color.white;
	public Hashtable <Color, MostrarImagen> histograms = new Hashtable<Color, MostrarImagen>();
	
	boolean robotHasMoved = true;
	
	private void makePanoramica() throws Exception {
		if (robotHasMoved) {
			robot.girarAngulo(GRADOS_HFOV*(int)((NUMBER_IMAGES_PAN-1)/2));
			for (int iterPan=0; iterPan<(NUMBER_IMAGES_PAN-1);iterPan++) {
				//try {Thread.sleep(SLEEP_VIDEO_UPDATE);} catch (InterruptedException ex) {}
				images[iterPan]= imagen.getImage();
				robot.girarAngulo(-GRADOS_HFOV);
			}
			images[NUMBER_IMAGES_PAN-1]= imagen.getImage();
			robot.girarAngulo(GRADOS_HFOV*(int)((NUMBER_IMAGES_PAN-1)/2));
			
			panorama.setImagen(ImageProcessing.makePanorama(images));
			robotHasMoved = false;
		}
	}

	private void makePanoramicaMinMovs() throws Exception {
		for (int iterPan=0; iterPan<NUMBER_IMAGES_PAN;iterPan++) {
			if (images360[(cursosImagen360+iterPan)%images360.length]==null) {
				robot.girarAngulo(GRADOS_HFOV*(int)((NUMBER_IMAGES_PAN-1)/2-iterPan));
				//try {Thread.sleep(SLEEP_VIDEO_UPDATE);} catch (InterruptedException ex) {}
				images360[(cursosImagen360+iterPan)%images360.length]= imagen.getImage();
				robot.girarAngulo(-GRADOS_HFOV*(int)((NUMBER_IMAGES_PAN-1)/2-iterPan));
			}
			images[iterPan]=images360[(cursosImagen360+iterPan)%images360.length];
		}
			
		panorama.setImagen(ImageProcessing.makePanorama(images));
	}
	
    ContournsDetector detector = new ContournsDetector();
    Double [] landmarks = new Double[landmarkColors.length];
	
	public Double[] findLandmarks() {
        Hashtable <Color, CvRect> rectangles, bigRectangles;
        Hashtable <Color, Double> lands;
        Double [] landWiew = new Double[landmarkColors.length];
        
        try {
			makePanoramica();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        IplImage frame = ImageCanvas.bufferedImage2IplImage(panorama.getImage());
        rectangles = detector.findRectangles(frame);
        bigRectangles = deleteRectangles(rectangles);
        detector.drawRectangles(frame, bigRectangles);
        panorama.setImagen(frame.getBufferedImage());
        
        lands = rectanglesAsPositionSize(bigRectangles);
        for (int iterLand=0; iterLand<landmarkColors.length;iterLand++)
        	landWiew[iterLand] = lands.get(landmarkColors[iterLand]);
        mergeLandmarks(landmarks, landWiew);
        return landmarks;
	}
	
	/**
	 * @param recibe dos arrays de marcas
	 * los mergea, de la siguiente manera: se queda para cada color con la marca mas grande
	 */
	private void mergeLandmarks(Double[] a, Double[] b) {
        for (int iterLand=0; iterLand<landmarkColors.length;iterLand++)
        	if ((a[iterLand]!=null)&&(b[iterLand]!=null)) {
        		// (position, size)
// se queda con el mas grande       		if (b[iterLand].y>a[iterLand].y)
//        			a[iterLand]=b[iterLand];
        		// tomo el promedio de los dos tamaños
        		a[iterLand].y = (a[iterLand].y + b[iterLand].y)/2; 
        	} else if (b[iterLand]!=null) {
        		a[iterLand]=b[iterLand];
        	} // else dejo a como estaba
	}

	/** Devuelve los rectangulos como una tupla que almancena la posicion y el tamaño ambos normalizados 0 a 1
	 * @param bigRectangles
	 * @return
	 */
	private Hashtable<Color, Double> rectanglesAsPositionSize(Hashtable<Color, CvRect> rectangles) {
        Hashtable <Color, Double> result = new Hashtable <Color, Double>();
		Enumeration<Color> keys = rectangles.keys();
		Color color;
		CvRect rectangle;
		double size, position, area;
		
		while (keys.hasMoreElements()) {
			color = keys.nextElement();
			rectangle = rectangles.get(color);
			
			// con area
//			area =area(rectangle);
//			System.err.println("SCI::OCV:color"+color+". area: "+ area + ".");
//			if (area>MAX_RECT_AREA) area = MAX_RECT_AREA;
//			size = (area - MIN_RECT_AREA)/(MAX_RECT_AREA-MIN_RECT_AREA); // normalizo el tamaño
			// con alto
//			System.err.println("SCI::OCV:color"+color+". alto: "+ rectangle.height() + ".");
			if (rectangle.height()>MAX_RECT_HEIGHT) area = MAX_RECT_HEIGHT;
			size = (rectangle.height() - MIN_RECT_HEIGHT)/(MAX_RECT_HEIGHT-MIN_RECT_HEIGHT); // normalizo el tamaño
			position = rectangle.x()+rectangle.width()/2; // calculo la pocicion del punto medio del rectangulo en x
			position = position/panorama.getWidth(); // normalizo la posicion
			result.put(color, new Double(position, size));
//			System.out.println("SCI::OCV:color"+color+". position: "+ position + ". size: "+ size + ".");
		}
		return result;
	}

	/**
	 * @param rectangles
	 */
	private Hashtable <Color, CvRect> deleteRectangles(Hashtable<Color, CvRect> rectangles) {
		Enumeration<Color> keys = rectangles.keys();
		Color color;
		CvRect rectangle;
		Hashtable<Color, CvRect> result = new Hashtable<Color, CvRect> ();
		double relWH, area;
		
		while (keys.hasMoreElements()) {
			color = keys.nextElement();
			rectangle = rectangles.get(color);
			relWH = (double)rectangle.width()/(double)rectangle.height();
			area = area(rectangle);
//			System.err.println("SCI::OCV:color"+color+". altura: "+ rectangle.height() +". area: "+ area +". relWH: "+ relWH + ".");

//			if (area(rectangle)>MIN_RECT_AREA)
			if ((rectangle.height()>MIN_RECT_HEIGHT)&&(area>MIN_RECT_AREA)&&(relWH<MAX_REL_WIDTH_HEIGHT))
				result.put(color, rectangle);
		}
		return result;
	}

	/**
	 * @param rectangle
	 * @return
	 */
	private double area(CvRect rectangle) {
		return rectangle.width()*rectangle.height();
	}

	/*
	 * construye los affordances de movimiento desde el LRF del robot
	 */
	private boolean[] makeAffordancesFromLDR() {
		boolean[] result = new boolean[ACTIONS];
		Arrays.fill(result,  false);
		long lrf[]=null;
		
		try {
			lrf = robot.getLRF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// cada cada affordance que puede extraerse desde el LRF
		// -135 lrf[6] --> aff[1] ... 135 lrf[0] --> aff[7]
		for (int iteAffLRF=0; iteAffLRF<lrf.length; iteAffLRF++) {
			result[7-iteAffLRF]=lrf[iteAffLRF]>THRESHOLD_DISTANCE;
		}
		
		// TODO: por ahora en el robot real apago los giros a raiz de 2 :D
		result[AFF_N135]=false;result[AFF_P135]=false;
		result[AFF_N45]=false;result[AFF_P45]=false;
//		System.err.println("SCI::affordances: (-120) " + result[AFF_N135]+ " , " + result[AFF_N90]+ " , " + result[AFF_N45]+ ", o"+ result[AFF_0]+ "o, "+ result[AFF_P45]+ ", "+ result[AFF_P90]+", "+ result[AFF_P135]+ " (120).");
		return result;
	}
	
//	public boolean[] makeAffordancesFromPanoramic() {
//		boolean[] result = new boolean[ACTIONS];
//		Double infoBorde, pixelsRange; 
//		double whitePorcentage;
//		int [] hist = ImageProcessing.makeHistogram(panorama.getImage(), colorWhite);
//		Arrays.fill(result,  false);
//		// cada cada affordance que puede extraerse desde la vision, lo obtengo ...
//		for (int iteVisionAff=0; iteVisionAff<VISION_AFF.length; iteVisionAff++) {
//			pixelsRange = range(ANGLE_AFF[VISION_AFF[iteVisionAff]]);
//			infoBorde = ImageProcessing.averageMinWidth(hist,pixelsRange);
//			whitePorcentage = ImageProcessing.percentInRange(panorama.getImage(), colorWhite,pixelsRange);
//			System.out.println("SCI::relacion: " + (infoBorde.y/infoBorde.x) + "%blanco: " + whitePorcentage + ".");
//			result [VISION_AFF[iteVisionAff]] = infoBorde.y/infoBorde.x<1.5 && whitePorcentage>TH_PORCENTAGE; //ImageProcessing.percentInRange(panorama.getImage(), colorBlack,range(ANGLE_N45))>TH_PORCENTAGE;			
//		}
//		
//		System.err.println("SCI::affordances: " + result[AFF_N90]+ " (-90), " + result[AFF_N45]+ ", "+ result[AFF_0]+ ", "+ result[AFF_P45]+ ", "+ result[AFF_P90]+ " (90).");
//		return result;
//	}

	/**
	 * @param angleN45
	 * @return
	 */
	private Double range(int angle) {
		int ANGLE_RANGE = NUMBER_IMAGES_PAN * GRADOS_HFOV;
		double horizontalPos = (angle + ANGLE_RANGE/2) * panorama.getImage().getWidth()/(double)ANGLE_RANGE;
		
		Double result = new Double(horizontalPos-TH_ANGLE, horizontalPos+TH_ANGLE);
		return result;
	}

	public static void main(String[] args) {
		SpatialCognitionInterfaceOpenCV robot = new SpatialCognitionInterfaceOpenCV();
		boolean[] aff=null;
		Double[] landmarks;
		double iniTime = System.currentTimeMillis();
		int min, seg;
		
		while (true) {
			try {landmarks = robot.findLandmarks();} catch (Exception e) {e.printStackTrace();}	
			
			try {aff = robot.makeAffordancesFromLDR();} catch (Exception e) {e.printStackTrace();}	
			try {
				if (aff[AFF_0]) robot.doAction(ANGLE_0);
				else if (aff[AFF_P45]) robot.doAction(ANGLE_P45);
				else if (aff[AFF_N45]) robot.doAction(ANGLE_N45);
				else if (aff[AFF_P90]) robot.doAction(ANGLE_P90);
				else if (aff[AFF_N90]) robot.doAction(ANGLE_N90);
				else if (aff[AFF_P135]) robot.doAction(ANGLE_P135);
				else if (aff[AFF_N135]) robot.doAction(ANGLE_N135);
				else robot.doAction(ANGLE_P180);
			} catch (Exception e) {e.printStackTrace();}

			seg = (int) (System.currentTimeMillis() - iniTime) / 1000;
			min = (int) seg / 60;
			seg = seg - min * 60;
			System.err.println("Up time: " + min + ":" + seg + " s.");
			//try {Thread.sleep(3000);} catch (Exception e) {e.printStackTrace();}
		}
	}

	// esto hay que sacarlo de aca y pasar solo al modelo el delta angulo.
	double globalAngle = 0;
	double speed = 0;
	/**
	 * @param i
	 * @throws IOException 
	 */
	public void doAction(int grados) {
		// TODO Auto-generated method stub
		try {

			if (grados==0) {
				robot.avanzar(MM_FORWARD);
				speed = Utiles.speed(new Double(0,0), new Double(0, MM_FORWARD/1000));
				Arrays.fill(landmarks, null); // cuando avanzo borro toda la memoria de marcas
				Arrays.fill(images360, null); // cuando avanzo borro toda la memoria de imagenes
				cursosImagen360=0;
			} else {
				robot.girarAngulo(-grados);
				speed = 0;
				if (grados>0)
					cursosImagen360=(cursosImagen360+grados/45)%images360.length;
				else  {
					cursosImagen360=(cursosImagen360-grados/45);
					if (cursosImagen360<0) cursosImagen360=images360.length-cursosImagen360;
				}
			
		
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		globalAngle = globalAngle + grados;
		if (globalAngle>360) globalAngle=globalAngle%360;
		if (globalAngle<0) globalAngle = 360+globalAngle;
		robotHasMoved = true;		
	}

	/* (non-Javadoc)
	 * @see robot.IRobot#getGlobalCoodinate()
	 */
	@Override
	public Double getGlobalCoodinate() {
		//System.out.println("SCI::robot position:"+dohyoTracker.getPosition());
		return dohyoTracker.getPosition();
	}

	/* (non-Javadoc)
	 * @see robot.IRobot#getGlobalDirection()
	 */
	@Override
	public double getGlobalDirection() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see robot.IRobot#affordances()
	 */
	@Override
	public boolean[] affordances() {
		return makeAffordancesFromLDR();
	}

	/* (non-Javadoc)
	 * @see robot.IRobot#findFood()
	 */	@Override
	public boolean findFood() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see robot.IRobot#startRobot()
	 */
	@Override
	public void startRobot() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see khepera.IRobot#getPanoramica()
	 */
	@Override
	public BufferedImage getPanoramica() {
		try {
			makePanoramica();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return panorama.getImage();
	}
	
	/* (non-Javadoc)
	 * @see robot.IRobot#getColorsLandmarks()
	 */
	@Override
	public Color[] getColorsLandmarks() {
		// TODO Auto-generated method stub
		return landmarkColors;
	}

	/* (non-Javadoc)
	 * @see robot.IRobot#getSpeed()
	 */
	@Override
	public double getSpeed() {
		// TODO Auto-generated method stub
		return speed;
	}

	/* (non-Javadoc)
	 * @see robot.IRobot#getHeadDirection()
	 */
	@Override
	public double getHeadDirection() {
		// TODO Auto-generated method stub
		return Math.toRadians(globalAngle);
	}


}
