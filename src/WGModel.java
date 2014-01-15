/* Mdulo NSL que implementa el modelo de cognicin espacial.
   Alejandra Barrera
   Versin: 1
   Fecha: 10 de marzo de 2005.
 */


import nsl.modules.ActionSelectionSchema;
import nsl.modules.ActionPerformer;
import nslj.src.lang.NslModel;
import nslj.src.lang.NslModule;
import robot.IRobot;
import robot.RobotFactory;
import support.Configuration;

public class WGModel extends NslModel {
	private ActionPerformer world;
	private ActionSelectionSchema actionSel;

	private static final String DEFAULT_MODEL_NAME = "WGModel (nombre por defecto)";

    //SpatialCognitionInterfaceOpenCV pepe = new SpatialCognitionInterfaceOpenCV();
    
	public WGModel() {
		this(DEFAULT_MODEL_NAME, (NslModel) null);
	}

	public WGModel(String nslName, NslModule nslParent) {
		super(nslName, nslParent);
		
		IRobot robot = RobotFactory.getRobot();

		actionSel = new ActionSelectionSchema("actionSel", this, robot);
		System.out.println("WGModel::Action ... OK");
		world = new ActionPerformer("world", this, robot);
		System.out.println("WGModel::World ... OK");
		
		
//		System.out.println("WGModel::PIFD ... OK");
//		inputPCL = new InputToPlaceCellLayer("inputPCL", this);
//		System.out.println("WGModel::IPCL ... OK");
//		pcl = new PlaceCellLayer("pcl", this);
//		System.out.println("WGModel::PCL ... OK");
//		//gridViewer = new CellsViewer(this, "activationGrid.data", 1 , new Point2D.Double(50,50));
//		System.out.println("WGModel::Grid Viewer ... OK");
//
//		wgl = (WorldGraphLayer) ReflexionLoader.getReflexionModel(learningRule, "wgl");
//		System.out.println("WGModel::"+learningRule+" ... OK");
//		mot = new MotivationalSchema("mot", this);
//		System.out.println("WGModel::Motivational ... OK");
		
//		System.out.println("Creando el robot ... "+ Configuration.getString("Reflexion.Robot"));
//		gridViewer = new CellsViewer(this, "activationGrid.data", DEFAULT_GRIDS_NUMBER);
//		placeViewer = new CellsViewer(this, "activationPlace.data", DEFAULT_PLACES_NUMBER);
//		System.out.println("WGModel::Grid Viewer ... OK");

		initSys();	
	}
	
	
	public void initSys() {
		system.setRunEndTime(1);
		system.nslSetRunDelta(0.1);
		system.setNumRunEpochs(1);
	}

	public void makeConn() {
		nslConnect(world.actionTaken, actionSel.actionTaken);
	}

}
