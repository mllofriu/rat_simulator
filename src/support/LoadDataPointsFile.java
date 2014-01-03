package support;
import java.awt.geom.Point2D.Double;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import javax.vecmath.Point2d;

public class LoadDataPointsFile implements IPoints {
	private String fileName = "coordenadasPredefinidasRataHasselmo.csv";

	public LoadDataPointsFile() {
	}
	
	public LoadDataPointsFile(String fileName) {
		this.fileName = fileName;
	}

	public Vector<Double> getDataPoints() throws IOException {
		Vector<Double> result = new Vector<Double>();
		File file = new File(fileName);
		BufferedReader bufRdr = new BufferedReader(new FileReader(file));
		String line = null;
		String[] strArray;
		Double point;
		int iterPoints = 0, iterLines = 0;
		double x, y;

		System.out.print("Leyendo puntos del archivo " + fileName + "...");
		while ((line = bufRdr.readLine()) != null) {
			strArray = line.split("\t");
			try {
				//x = (java.lang.Double.parseDouble(strArray[0]) + 100) / 200;
				//y = (java.lang.Double.parseDouble(strArray[1]) + 100) / 200;
				x = java.lang.Double.parseDouble(strArray[0])/100; // lo paso a metros como el simulador
				y = java.lang.Double.parseDouble(strArray[1])/100;
				point = new Double(x, y);
				result.add(point);
				iterPoints++;
			} catch (NumberFormatException exception) {
			}
			iterLines++;
		}
		System.out.println(" puntos leidos " + iterPoints + "/" + iterLines);
		return result;
	}
}
