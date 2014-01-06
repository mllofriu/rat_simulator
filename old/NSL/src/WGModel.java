/* Mdulo NSL que implementa el modelo de cognicin espacial.
   Alejandra Barrera
   Versin: 1
   Fecha: 10 de marzo de 2005.
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import nslj.src.lang.NslModel;
import nslj.src.lang.NslModule;
import nslj.src.main.NslMain;

public class WGModel extends NslModel {
	private World world;
	private Rat rat;
	private Drive drive;
	private AffPerceptualSchema ps;
	private MotivationalSchema mot;
	private ActionSelectionSchema actionSel;
	private DynamicRemappingLayer drl;
	private PathIntegrationFeatureDetectorLayer piFDL;
	private LandmarksPerceptualSchema landPS;
	private LandmarksFeatureDetectorLayer landFDL;
	private LandmarksLayer landL;
	private InputToPlaceCellLayer inputPCL;
	private PlaceCellLayer pcl;
	private WorldGraphLayer wgl;
	
	private final int COMPACT_IMAGE_HEIGHT = 80;
	private final int COMPACT_IMAGE_WIDTH = 80;
	private final int INITIAL_RAT_HEAD_ANGLE =90;
	private final int AFF_PERCEPTUAL_SCHEMA_SIZE=80;
	private final int LAND_PERCEPTUAL_SCHEMA_SIZE=160;
	private static final String DEFAULT_MODEL_NAME = "WGModel (nombre por defecto)";
    public static final int EPOCHS = Configuration.getInt("WGModel.EPOCHS");
    public static final String learningRule = Configuration.getString("Reflexion.LearningRule");


	public WGModel() {
		this(DEFAULT_MODEL_NAME, (NslModel) null);
	}

	public WGModel(String nslName, NslModule nslParent) {
		super(nslName, nslParent);
		if (NslMain.TopLoaded) {
			System.err.println("ERROR: construction without (name,parent)");
			System.exit(1);
		}
		NslMain.TopLoaded = true;

		world = new World("world", this, COMPACT_IMAGE_HEIGHT, COMPACT_IMAGE_WIDTH, INITIAL_RAT_HEAD_ANGLE);
		System.out.println("WGModel::World ... OK");
		rat = new Rat("rat", this);
		System.out.println("WGModel::Rat ... OK");
		ps = new AffPerceptualSchema("ps", this,AFF_PERCEPTUAL_SCHEMA_SIZE);
		System.out.println("WGModel::APS ... OK");
		landPS = new LandmarksPerceptualSchema("landPS", this, LAND_PERCEPTUAL_SCHEMA_SIZE);
		System.out.println("WGModel::LPS ... OK");
		landFDL = new LandmarksFeatureDetectorLayer("landFDL", this);
		System.out.println("WGModel::LFDL ... OK");
		landL = new LandmarksLayer("landL", this);
		System.out.println("WGModel::LL ... OK");
		drive = new Drive("drive", this);
		System.out.println("WGModel::Drive ... OK");
		drl = new DynamicRemappingLayer("drl", this);
		System.out.println("WGModel::DLR ... OK");
		piFDL = new PathIntegrationFeatureDetectorLayer("piFDL", this);
		System.out.println("WGModel::PIFD ... OK");
		inputPCL = new InputToPlaceCellLayer("inputPCL", this);
		System.out.println("WGModel::IPCL ... OK");
		pcl = new PlaceCellLayer("pcl", this);
		System.out.println("WGModel::PCL ... OK");
		
		//wgl = new WorldGraphLayer("wgl", this);
		// Reflexion para levantar la clase aprendizaje desde archivo de configuracion
		Class[] types = new Class[] { String.class, NslModule.class };
		Constructor cons = null;
		try {
			cons = Class.forName(learningRule).getConstructor(types);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object[] args = new Object[] {"wgl", this};
		try {
			wgl = (WorldGraphLayer) cons.newInstance(args);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("WGModel::"+learningRule+" ... OK");
		mot = new MotivationalSchema("mot", this);
		System.out.println("WGModel::Motivational ... OK");
		actionSel = new ActionSelectionSchema("actionSel", this);
		System.out.println("WGModel::Action ... OK");
		System.out.println("Creando el robot ... "+ Configuration.getString("Reflexion.Robot"));

		initSys();
	}

	public void initSys() {
		system.setRunEndTime(10);
		system.nslSetRunDelta(0.1);
		system.setNumRunEpochs(EPOCHS);
	}

	public void makeConn() {
		nslConnect(world.currentHeadAngle, rat.headAngleRat);
		nslConnect(rat.currentHeadAngleRat, ps.headAngleRat);
		nslConnect(rat.distFood, drive.distFood);
		nslConnect(rat.turnToFood, mot.turnToFood);
		nslConnect(rat.distLandmarks, landPS.distLandmarks);
		nslConnect(rat.angleLandmarks, landPS.angleLandmarks);
		nslConnect(landPS.psLand1, landFDL.psLand1);
		nslConnect(landPS.psLand2, landFDL.psLand2);
		nslConnect(landPS.psLand3, landFDL.psLand3);
		nslConnect(landPS.psLand4, landFDL.psLand4);
		nslConnect(landFDL.fdl1, landL.fdl1);
		nslConnect(landFDL.fdl2, landL.fdl2);
		nslConnect(landFDL.fdl3, landL.fdl3);
		nslConnect(landFDL.fdl4, landL.fdl4);
		nslConnect(landFDL.activation4Land, landL.activation4Land);
		nslConnect(actionSel.newHeadAngleRat, world.newHeadAngleRat);
		nslConnect(ps.affPS, mot.affPS);
		nslConnect(ps.currentHeadAngleRat, actionSel.headAngleRat);
		nslConnect(mot.AngleToGo, actionSel.AngleToGo);
		nslConnect(actionSel.ActionTaken, world.ActionTaken);
		nslConnect(actionSel.currentDir, drl.currentDir);
		nslConnect(actionSel.nextDir, drl.nextDir);
		nslConnect(drl.PIps1dim, piFDL.PIps1dim);
		nslConnect(piFDL.piFDL1dim, inputPCL.piFDL1dim);
		nslConnect(landL.lFDL1dim, inputPCL.lFDL1dim);
		nslConnect(inputPCL.iPCL, pcl.iPCL);
		nslConnect(ps.currentHeadAngleRat, wgl.currentHeadAngleRat);
		nslConnect(pcl.pcl1dim, wgl.pcl1dim);
		nslConnect(drive.reward, wgl.reward);
		nslConnect(rat.distFood, wgl.distFood);
		nslConnect(actionSel.ActionTaken, wgl.ActionTaken);
		nslConnect(ps.currentHeadAngleRat, mot.currentHeadAngleRat);
		nslConnect(wgl.expCycling, mot.expCycling);
		nslConnect(wgl.curiosityCycling, mot.curiosityCycling);
	}

	public AffPerceptualSchema getAPS() {
		return ps;
	}
}
