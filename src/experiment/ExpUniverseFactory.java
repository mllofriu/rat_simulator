package experiment;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import robot.IRobot;
import support.Configuration;



import nslj.src.lang.NslModule;

public class ExpUniverseFactory {
	private static ExperimentUniverse universe=null;
	private static final String universeClassName = Configuration.getString("Reflexion.Universe");

	public static ExperimentUniverse getUniverse() {
		if(universe==null) {

			// Reflexion para levantar la clase aprendizaje desde archivo de configuracion
			Class[] types = new Class[] {};
			Constructor cons = null;
			try {
				cons = Class.forName(universeClassName).getConstructor(types);
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
			Object[] args = new Object[] {}; // constructor sin argumentos
			try {
				universe = (ExperimentUniverse) cons.newInstance(args);
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

		}

		return universe;
	}


}
