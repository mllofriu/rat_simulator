/**
 * Esta clase implementa un camino de prueba para una celda grilla de Hasselmo y genera
 * la salida para luego poder evaluar los resultados obtenidos
 */

/**
 * @author gtejera
 * Version: 1
 *  Fecha: 10 de agosto de 2012.
 */

import java.awt.geom.Point2D;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import Schemas.HasselmoGridCellLayer;
import Schemas.HebbianNetwork;
import support.Configuration;
import neural.GridCell;
import neural.HeadAndSpeed;
import nslj.src.system.*;
import nslj.src.cmd.*;
import nslj.src.lang.*;
import nslj.src.math.*;
import nslj.src.display.*;

public class TestHasselmoGridCell extends NslModel  {
	private static final String DEFAULT_MODEL_NAME = "WGModel (nombre por defecto)";
	private static final int DEFAULT_GRIDS_PER_PLACE = Configuration.getInt("GridCellLayer.GridsPerPlace");
	//private static final int DEFAULT_GRIDS_NUMBER = DEFAULT_GRIDS_PER_PLACE*DEFAULT_PLACES_NUMBER;
	private static final int DEFAULT_GRIDS_NUMBER = Configuration.getInt("GridCellLayer.DefaultGridsNumber");
	
	private static final int DEFAULT_PLACES_NUMBER = Configuration.getInt("PlaceCellLayer.DefaultPlacesNumber");
	private static double CONNECTIVITY_RATE = DEFAULT_GRIDS_PER_PLACE/(double)DEFAULT_GRIDS_NUMBER;

	private static final int EPOCHS = 65534;
	private static final double DELTA_STEP = Configuration.getDouble("Simulation.DeltaStep");

	private HeadAndSpeed headAndSpeed;
	private HasselmoGridCellLayer grids;
	private CellsViewer gridViewer;
	//private PlaceCellLayer places;
	private HebbianNetwork places;
	private CellsViewer placeViewer;
	private ActionRobot actionRobot;
	
	public TestHasselmoGridCell() {
		this(DEFAULT_MODEL_NAME, null);
	}

	public TestHasselmoGridCell(String nslName, NslModule nslParent) {
		super(nslName, nslParent);
		if (NslMain.TopLoaded) {
			System.err.println("ERROR: construction without (name,parent)");
			System.exit(1);
		}
		NslMain.TopLoaded = true;
		headAndSpeed = new HeadAndSpeed(this);
		grids = new HasselmoGridCellLayer(this,DEFAULT_GRIDS_NUMBER);
		gridViewer = new CellsViewer(this, "activationGridTest-Z1.0.data", DEFAULT_GRIDS_NUMBER);
		
		System.out.println("TestHasselmoGridCell::Creando grids modules ... ok");

		places = new HebbianNetwork("Hebbian Network Grid2Place", this, DEFAULT_GRIDS_NUMBER, DEFAULT_PLACES_NUMBER, CONNECTIVITY_RATE);
		placeViewer = new CellsViewer(this, "activationPlaceTest.data",  DEFAULT_PLACES_NUMBER);
		System.out.println("TestHasselmoGridCell::Creando place modules ... ok");
		actionRobot = new ActionRobot(this);
		
		system.setRunEndTime(50);
		system.nslSetRunDelta(DELTA_STEP);
		system.setNumRunEpochs(EPOCHS);
	}

	public void makeConn() {
		nslConnect(headAndSpeed.headDirection, grids.headDirection);
		nslConnect(headAndSpeed.speed, grids.speed);
		nslConnect(grids.outputPIL, gridViewer.cellsActivationNSL);
		nslConnect(grids.outputPIL, places.inputsNSL);
//		nslConnect(grids.gridCellsActivationNSL, places.gridCellsActivationNSL);
//		nslConnect(grids.gridCellsPositionNSL, places.gridCellsPositionNSL);
		nslConnect(places.activationNSL, placeViewer.cellsActivationNSL);
	}
	
}
